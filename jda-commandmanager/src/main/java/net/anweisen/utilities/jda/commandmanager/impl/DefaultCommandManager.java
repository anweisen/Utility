package net.anweisen.utilities.jda.commandmanager.impl;

import net.anweisen.utilities.commons.common.Tuple;
import net.anweisen.utilities.commons.common.WrappedException;
import net.anweisen.utilities.commons.misc.ReflectionUtils;
import net.anweisen.utilities.jda.commandmanager.*;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.commandmanager.arguments.InvalidArgumentParseValueException;
import net.anweisen.utilities.jda.commandmanager.arguments.ParserOptions;
import net.anweisen.utilities.jda.commandmanager.impl.entities.CommandArgumentsImpl;
import net.anweisen.utilities.jda.commandmanager.impl.entities.CommandEventImpl;
import net.anweisen.utilities.jda.commandmanager.impl.prefix.ConstantPrefixProvider;
import net.anweisen.utilities.jda.commandmanager.language.LanguageManager;
import net.anweisen.utilities.jda.commandmanager.process.CommandPreProcessInfo;
import net.anweisen.utilities.jda.commandmanager.process.CommandProcessResult;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultHandler;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultInfo;
import net.anweisen.utilities.jda.commandmanager.registered.CommandOptions;
import net.anweisen.utilities.jda.commandmanager.registered.CommandTask;
import net.anweisen.utilities.jda.commandmanager.registered.RegisteredCommand;
import net.anweisen.utilities.jda.commandmanager.registered.RequiredArgument;
import net.dv8tion.jda.api.Permission;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultCommandManager implements CommandManager {

	protected class Callback {

		private final Consumer<? super CommandResultInfo> action;
		private boolean called;

		public Callback(@Nonnull CommandPreProcessInfo info) {
			CommandEventImpl container = new CommandEventImpl(DefaultCommandManager.this, info.getMessage(), info.getMember(), true);
			this.action = resultHandler == null ? result -> {} : result -> resultHandler.handle(DefaultCommandManager.this, container, container, result);
		}

		public Object call(@Nonnull CommandResultInfo info) {
			if (called) return null;
			called = true;
			action.accept(info);
			return null;
		}

		public Object call(@Nonnull CommandProcessResult result, @Nullable String prefix, @Nullable String commandName) {
			return call(new CommandResultInfo(result, commandName, prefix));
		}

		public Object call(@Nonnull CommandProcessResult result) {
			return call(result, null, null);
		}

	}

	protected final Map<String, RegisteredCommand> commandsByName = new HashMap<>();
	protected final ExecutorService executorService = Executors.newCachedThreadPool();
	protected CommandResultHandler resultHandler = new HardcodedResultHandler(false);
	protected ParserContext parserContext = new DefaultParserContext();
	protected PrefixProvider prefixProvider;
	protected LanguageManager languageManager;
	protected TeamRoleManager teamRoleManager;
	protected boolean reactToMentionPrefix = true;

	public DefaultCommandManager(@Nonnull PrefixProvider prefixProvider) {
		this.prefixProvider = prefixProvider;
	}

	public DefaultCommandManager(@Nonnull String prefix) {
		this(new ConstantPrefixProvider(prefix));
	}

	@Nonnull
	@Override
	public Collection<RegisteredCommand> getRegisteredCommands() {
		return Collections.unmodifiableCollection(commandsByName.values());
	}

	@Nonnull
	@Override
	public CommandManager register(@Nonnull Object... commands) {
		for (Object command : commands) {
			register(command);
		}
		return this;
	}

	@Nonnull
	@Override
	public CommandManager register(@Nonnull Object command) {
		if (command instanceof InterfacedCommand) {
			RegisteredCommand registeredCommand = new RegisteredCommand((InterfacedCommand) command, this);
			register0(registeredCommand);
			return this;
		}

		for (Method method : ReflectionUtils.getMethodsAnnotatedWith(command.getClass(), Command.class)) {
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length != 2) throw new IllegalArgumentException("Cannot register " + method + ", parameter count is not 2");
			if (parameters[0] != CommandEvent.class || parameters[1] != CommandArguments.class) throw new IllegalArgumentException("Cannot register " + method + ", parameters are not (CommandEvent, CommandArguments)");

			RegisteredCommand registeredCommand = new RegisteredCommand(command, method, this);
			register0(registeredCommand);
		}
		return this;
	}

	@Nonnull
	@Override
	public CommandManager register(@Nonnull CommandTask task, @Nonnull CommandOptions options) {
		register0(new RegisteredCommand(options, task, this));
		return this;
	}

	private void register0(@Nonnull RegisteredCommand command) {
		for (String name : command.getOptions().getName()) {
			commandsByName.put(name, command);
		}
	}

	@Nonnull
	public Optional<RegisteredCommand> findCommand(@Nonnull String input, @Nonnull Collection<RegisteredCommand> matchingName) {
		for (Entry<String, RegisteredCommand> entry : commandsByName.entrySet()) {
			String name = entry.getKey();
			if (!input.startsWith(name)) continue;
			RegisteredCommand command = entry.getValue();
			matchingName.add(command);
			RequiredArgument[] arguments = command.getArguments();
			String argumentInput = input.substring(name.length()).trim();
			if (!isArgumentLengthAssignable(argumentInput, arguments)) continue;
			return Optional.of(command);
		}
		return Optional.empty();
	}

	private boolean isArgumentLengthAssignable(@Nonnull String input, @Nonnull RequiredArgument[] arguments) {
		if (input.isEmpty() && arguments.length > 0) return false;
		String[] splitted = input.split(" ");
		int index = 0;
		for (RequiredArgument argument : arguments) {
			if (argument.getLength() == 0 && splitted.length > (index == 0 ? 1 : index)) return true; // We want all following arguments
			index += argument.getLength();
			if (index > splitted.length) return false;
		}
		return index > 0;
	}

	@Override
	public void handleCommand(@Nonnull CommandPreProcessInfo info) {
		Callback callback = new Callback(info);
		try {
			handleCommand0(info, callback);
		} catch (Exception ex) {
			LOGGER.error("An error occurred while handling a command", ex);
			callback.call(CommandProcessResult.ERROR);
		}
	}

	protected Object handleCommand0(@Nonnull CommandPreProcessInfo info, @Nonnull Callback callback) {
		if (info.getMessage().getType().isSystem())
			return callback.call(CommandProcessResult.INVALID_MESSAGE);
		if (info.getMessage().getAuthor().equals(info.getMessage().getJDA().getSelfUser()))
			return callback.call(CommandProcessResult.SELF_REACTION);

		String raw = info.getMessage().getContentRaw();
		String prefix = info.getPrefix(prefixProvider);
		String usedPrefix = prefix;
		if (!raw.startsWith(prefix)) {
			if (reactToMentionPrefix) {
				String mentionPrefix = "<@!" + info.getMessage().getJDA().getSelfUser().getId() + ">";
				if (raw.startsWith(mentionPrefix)) {
					usedPrefix = mentionPrefix;
					while (raw.substring(usedPrefix.length()).startsWith(" "))
						usedPrefix += " ";
				} else {
					return callback.call(CommandProcessResult.PREFIX_NOT_USED, prefix, null);
				}
			} else {
				return callback.call(CommandProcessResult.PREFIX_NOT_USED, prefix, null);
			}
		}

		String content = raw.substring(usedPrefix.length());
		String lower = content.toLowerCase();
		List<RegisteredCommand> matchingName = new ArrayList<>();
		Optional<RegisteredCommand> optional = findCommand(lower, matchingName);
		if (!optional.isPresent()) {
			if (matchingName.isEmpty()) {
				return callback.call(CommandProcessResult.UNKNOWN_COMMAND, prefix, content);
			} else {
				return callback.call(new CommandResultInfo(CommandProcessResult.INCORRECT_ARGUMENTS, matchingName, matchingName.get(0).getOptions().getFirstName(), prefix));
			}
		}

		RegisteredCommand command = optional.get();
		String commandName = command.getOptions().getFirstName();

		if (!command.getOptions().getAllowEdits() && info.getMessage().isEdited())
			return callback.call(CommandProcessResult.EDITS_UNSUPPORTED, prefix, commandName);
		if (!command.getOptions().getAllowBots() && info.getMessage().getAuthor().isBot())
			return callback.call(CommandProcessResult.INVALID_AUTHOR, prefix, commandName);
		if (!command.getOptions().getAllowWebHooks() && info.getMessage().isWebhookMessage())
			return callback.call(CommandProcessResult.INVALID_AUTHOR, prefix, commandName);
		if (command.getOptions().getField() == CommandField.GUILD && !info.getMessage().isFromGuild())
			return callback.call(CommandProcessResult.INVALID_FIELD, prefix, commandName);
		if (command.getOptions().getField() == CommandField.PRIVATE && info.getMessage().isFromGuild())
			return callback.call(CommandProcessResult.INVALID_FIELD, prefix, commandName);
		if (command.getOptions().getPermission() != Permission.UNKNOWN && !info.getMember().hasPermission(command.getOptions().getPermission()))
			return callback.call(CommandProcessResult.MISSING_PERMISSION);
		if (command.getOptions().getTeam() && (!teamRoleManager.isTeamRoleSet(info.getMember().getGuild()) || !teamRoleManager.hasTeamRole(info.getMember())) && !info.getMember().hasPermission(command.getOptions().getPermissionOrAdmin()))
			return callback.call(CommandProcessResult.MISSING_TEAM_ROLE);
		if (command.getCoolDown().isOnCoolDown(info.getMessage().getAuthor(), info.getMessage().isFromGuild() ? info.getMessage().getGuild() : null))
			return callback.call(new CommandResultInfo(CommandProcessResult.COOLDOWN, command, commandName, prefix, command.getCoolDown().getCoolDown(info.getMessage().getAuthor(), info.getMessage().isFromGuild() ? info.getMessage().getGuild() : null)));

		command.getCoolDown().renewCoolDown(info.getMessage().getAuthor(), info.getMessage().isFromGuild() ? info.getMessage().getGuild() : null);

		String stripped = content.substring(commandName.length()).trim();

		CommandEvent event = new CommandEventImpl(this, info.getMessage(), info.getMember(), command.getOptions().isDisableMentions());
		Tuple<Class<?>[], Object[]> parsed = parseArguments(command, stripped, event);
		if (parsed == null)
			return callback.call(new CommandResultInfo(CommandProcessResult.INCORRECT_ARGUMENTS, command, commandName, prefix));

		CommandArguments args = new CommandArgumentsImpl(parsed.getFirst(), parsed.getSecond());

		if (command.getOptions().isAsync()) {
			executorService.submit(() -> execute0(command, callback, event, args, prefix, commandName));
		} else {
			execute0(command, callback, event, args, prefix, commandName);
		}

		return null;
	}

	protected Object execute0(@Nonnull RegisteredCommand command, @Nonnull Callback callback, @Nonnull CommandEvent event, @Nonnull CommandArguments args, @Nonnull String prefix, @Nonnull String commandName) {
		try {
			command.execute(event, args);
			return callback.call(CommandProcessResult.SUCCESS, prefix, commandName);
		} catch (Throwable ex) {
			LOGGER.error("An error occurred while executing a command", ex);
			return callback.call(CommandProcessResult.ERROR, prefix, commandName);
		}
	}

	@Nullable
	private Tuple<Class<?>[], Object[]> parseArguments(@Nonnull RegisteredCommand command, @Nonnull String message, @Nonnull CommandEvent event) {
		if (message.isEmpty()) return new Tuple<Class<?>[], Object[]>(new Class[0], new Object[0]);
		List<Class<?>> types = new ArrayList<>(command.getArguments().length);
		List<Object> values = new ArrayList<>(command.getArguments().length);
		String[] args = message.split(" ");
		int index = 0;
		for (RequiredArgument argument : command.getArguments()) {
			int collectArgs = argument.getLength() == 0 ? args.length - index : argument.getLength();
			ArgumentParser<?> parser = argument.getParser();
			ParserOptions options = parser.options();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < collectArgs; i++, index++) {
				if (index >= args.length) throw new IllegalArgumentException("Tried to access argument at index " + index + " for length " + args.length);
				if (i > 0) builder.append(options.getMultiWordSeparator());
				builder.append(args[index]);
			}
			try {
				Object parsed = parser.parse(event, builder.toString());
				if (parsed == null) throw new NullPointerException("No value for input '" + builder + "'");
				if (!argument.getClassOfArgument().isInstance(parsed))
					throw new InvalidArgumentParseValueException("Parser for class " + argument.getClassOfArgument().getName() + " returned " + parsed.getClass().getName());
				types.add(argument.getClassOfArgument());
				values.add(parsed);
			} catch (InvalidArgumentParseValueException ex) {
				throw new WrappedException(ex); // Internal error
			} catch (Exception ex) {
				return null; // Invalid usage
			}
		}
		return new Tuple<>(types.toArray(new Class[0]), values.toArray());
	}

	@Nonnull
	@Override
	public ParserContext getParserContext() {
		return parserContext;
	}

	@Nonnull
	@Override
	public CommandManager setParserContext(@Nonnull ParserContext parserContext) {
		this.parserContext = parserContext;
		return this;
	}

	@Nonnull
	@Override
	public PrefixProvider getPrefixProvider() {
		return prefixProvider;
	}

	@Nonnull
	@Override
	public CommandManager setPrefixProvider(@Nonnull PrefixProvider prefixProvider) {
		this.prefixProvider = prefixProvider;
		return this;
	}

	@Nullable
	@Override
	public CommandResultHandler getResultHandler() {
		return resultHandler;
	}

	@Nonnull
	@Override
	public CommandManager setResultHandler(@Nullable CommandResultHandler handler) {
		this.resultHandler = handler;
		return this;
	}

	@Nonnull
	@Override
	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	@Nonnull
	@Override
	public CommandManager setLanguageManager(@Nonnull LanguageManager manager) {
		this.languageManager = manager;
		return this;
	}

	@Nullable
	@Override
	public TeamRoleManager getTeamRoleManager() {
		return teamRoleManager;
	}

	@Nonnull
	@Override
	public CommandManager setTeamRoleManager(@Nullable TeamRoleManager teamRoleManager) {
		this.teamRoleManager = teamRoleManager;
		return this;
	}

	@Override
	public boolean getReactToMentionPrefix() {
		return false;
	}

	@Nonnull
	@Override
	public CommandManager setReactToMentionPrefix(boolean react) {
		this.reactToMentionPrefix = react;
		return this;
	}

}
