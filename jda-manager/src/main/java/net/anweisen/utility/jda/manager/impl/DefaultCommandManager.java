package net.anweisen.utility.jda.manager.impl;

import net.anweisen.utility.common.collection.NamedThreadFactory;
import net.anweisen.utility.common.collection.WrappedException;
import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.jda.manager.*;
import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.arguments.IllegalArgumentParserValueException;
import net.anweisen.utility.jda.manager.arguments.ParserOptions;
import net.anweisen.utility.jda.manager.hooks.event.CommandArguments;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utility.jda.manager.hooks.option.CommandOptions;
import net.anweisen.utility.jda.manager.hooks.option.CommandScope;
import net.anweisen.utility.jda.manager.hooks.registered.CommandResolver;
import net.anweisen.utility.jda.manager.hooks.registered.CommandTask;
import net.anweisen.utility.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utility.jda.manager.hooks.registered.RequiredArgument;
import net.anweisen.utility.jda.manager.impl.entities.CommandArgumentsImpl;
import net.anweisen.utility.jda.manager.impl.language.ConstantLanguageManager;
import net.anweisen.utility.jda.manager.impl.language.FallbackLanguage;
import net.anweisen.utility.jda.manager.impl.prefix.ConstantPrefixProvider;
import net.anweisen.utility.jda.manager.impl.resolver.AnnotatedCommandResolver;
import net.anweisen.utility.jda.manager.impl.resolver.InterfacedCommandResolver;
import net.anweisen.utility.jda.manager.impl.slashcommands.SlashCommandHelper;
import net.anweisen.utility.jda.manager.language.LanguageManager;
import net.anweisen.utility.jda.manager.process.CommandPreProcessInfo;
import net.anweisen.utility.jda.manager.process.CommandProcessResult;
import net.anweisen.utility.jda.manager.process.CommandResultHandler;
import net.anweisen.utility.jda.manager.process.CommandResultInfo;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultCommandManager implements CommandManager {

	private static final Object CALLBACK_RESULT = new Object();

	protected class Callback {

		private final Consumer<? super CommandResultInfo> action;
		private boolean called;

		public Callback(@Nonnull CommandPreProcessInfo info) {
			CommandEvent event = eventCreator.createEvent(DefaultCommandManager.this, info, new RegisteredCommand(new CommandOptions(), (e, a) -> {
			}, DefaultCommandManager.this), useEmbeds);
			this.action = resultHandler == null ? result -> {
			} : result -> resultHandler.handle(DefaultCommandManager.this, event, result);
		}

		public Object call(@Nonnull CommandResultInfo info) {
			if (called) return null;
			called = true;
			try {
				action.accept(info);
			} catch (Throwable ex) {
				LOGGER.error("An error occurred while handling callback of command handling", ex);
			}
			return CALLBACK_RESULT;
		}

		public Object call(@Nonnull CommandProcessResult result, @Nullable String prefix, @Nullable String commandName) {
			return call(new CommandResultInfo(result, commandName, prefix));
		}

		public Object call(@Nonnull CommandProcessResult result, @Nonnull RegisteredCommand command, @Nonnull String prefix, @Nonnull String commandName) {
			return call(new CommandResultInfo(result, command, commandName, prefix));
		}

		public Object call(@Nonnull CommandProcessResult result) {
			return call(result, null, null);
		}

	}

	protected static class ArgumentParseResult {

		private final Tuple<Class<?>[], Object[]> success;
		private final Tuple<String, Object[]> failure;

		public ArgumentParseResult(@Nullable Tuple<Class<?>[], Object[]> success, @Nullable Tuple<String, Object[]> failure) {
			this.success = success;
			this.failure = failure;
		}
	}

	protected final Collection<RegisteredCommand> commands = new ArrayList<>();
	protected final ExecutorService executor = Executors.newCachedThreadPool(new NamedThreadFactory(threadId -> String.format("Command-Thread-%s", threadId)));
	protected final Collection<CommandResolver> resolvers = new ArrayList<>(Arrays.asList(new AnnotatedCommandResolver(), new InterfacedCommandResolver()));
	protected LanguageManager languageManager = new ConstantLanguageManager(new FallbackLanguage());
	protected CommandResultHandler resultHandler = new DefaultResultHandler();
	protected ParserContext parserContext = new DefaultParserContext();
	protected EventCreator eventCreator = new DefaultEventCreator();
	protected PrefixProvider prefixProvider;
	protected TeamRoleManager teamRoleManager;
	protected boolean reactToMentionPrefix = true;
	protected boolean useEmbeds = false;

	public DefaultCommandManager(@Nonnull PrefixProvider prefixProvider) {
		this.prefixProvider = prefixProvider;
	}

	public DefaultCommandManager(@Nonnull String prefix) {
		this(new ConstantPrefixProvider(prefix));
	}

	@Nonnull
	@Override
	public Collection<RegisteredCommand> getRegisteredCommands() {
		return Collections.unmodifiableCollection(commands);
	}

	@Nonnull
	@Override
	public CommandManager register(@Nonnull Object command) {
		for (CommandResolver resolver : resolvers) {
			Collection<RegisteredCommand> commands = resolver.resolve(command, this);
			if (!commands.isEmpty()) {
				commands.forEach(this::register0);
				return this;
			}
		}

		LOGGER.warn("Unrecognized command {} - {}", command.getClass(), command);
		return this;
	}

	@Nonnull
	@Override
	public CommandManager register(@Nonnull CommandTask task, @Nonnull CommandOptions options) {
		register0(new RegisteredCommand(options, task, this));
		return this;
	}

	private void register0(@Nonnull RegisteredCommand command) {
		commands.add(command);
	}

	@Nonnull
	@Override
	public CommandManager setupSlashCommands(@Nonnull JDA jda) {
		new SlashCommandHelper(jda, commands).execute();
		return this;
	}

	@Nonnull
	public Optional<Tuple<String, RegisteredCommand>> findCommand(@Nonnull String input, @Nonnull Collection<RegisteredCommand> matchingName) {
		for (RegisteredCommand command : commands) {
			for (String name : command.getOptions().getName()) {
				boolean beginsWithName = input.startsWith(name);
				if (!(beginsWithName || name.startsWith(input))) continue;
				matchingName.add(command);
				if (!beginsWithName) continue;
				if (input.length() > name.length() && !input.substring(name.length()).startsWith(" ")) continue;
				RequiredArgument[] arguments = command.getArguments();
				String argumentInput = input.substring(name.length()).trim();
				if (!isArgumentLengthAssignable(argumentInput, arguments)) continue;
				return Optional.of(Tuple.of(name, command));
			}
		}
		return Optional.empty();
	}

	private boolean isArgumentLengthAssignable(@Nonnull String input, @Nonnull RequiredArgument[] arguments) {
		if (input.isEmpty()) return arguments.length == 0;
		if (arguments.length == 0) return false;
		String[] split = input.split(" ");
		int index = 0;
		for (RequiredArgument argument : arguments) {
			if (argument.getLength() == 0 && split.length > (index == 0 ? 1 : index)) return true; // We want all following arguments
			index += argument.getLength();
			if (index > split.length) return false;
		}
		return true;
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
		String prefix = info.getPrefix(prefixProvider);

		if (info.getMessage() == null) // slash command
			return handleCommand00(info, callback, prefix, info.getMessageContent());

		if (info.getMessage().getType().isSystem())
			return callback.call(CommandProcessResult.INVALID_MESSAGE);
		if (info.getMessage().getAuthor().equals(info.getMessage().getJDA().getSelfUser()))
			return callback.call(CommandProcessResult.SELF_REACTION);

		String raw = info.getMessage().getContentRaw();
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

		if (raw.equals(usedPrefix))
			return callback.call(CommandProcessResult.EMPTY_COMMAND);

		String content = raw.substring(usedPrefix.length());
		return handleCommand00(info, callback, prefix, content);
	}

	protected Object handleCommand00(@Nonnull CommandPreProcessInfo info, @Nonnull Callback callback, @Nonnull String prefix, @Nonnull String content) {

		String lower = content.toLowerCase();
		List<RegisteredCommand> matchingName = new ArrayList<>();
		Optional<Tuple<String, RegisteredCommand>> optional = findCommand(lower, matchingName);
		if (!optional.isPresent()) {
			if (matchingName.isEmpty()) {
				return callback.call(CommandProcessResult.UNKNOWN_COMMAND, prefix, content);
			} else if (matchingName.size() == 1) {
				RegisteredCommand matchingCommand = matchingName.get(0);
				doCommonChecks(matchingCommand, callback, prefix, matchingCommand.getOptions().getFirstName(), info);
				return callback.call(new CommandResultInfo(CommandProcessResult.INCORRECT_ARGUMENTS, matchingName, matchingCommand.getOptions().getFirstName(), prefix));
			} else {
				return callback.call(new CommandResultInfo(CommandProcessResult.INCORRECT_ARGUMENTS, matchingName, null, prefix));
			}
		}

		Tuple<String, RegisteredCommand> pair = optional.get();
		String commandName = pair.getFirst();
		RegisteredCommand command = pair.getSecond();

		if (doCommonChecks(command, callback, prefix, commandName, info) == CALLBACK_RESULT)
			return CALLBACK_RESULT;
		if (command.getCoolDown().isOnCoolDown(info.getUser(), info.isFromGuild() ? info.getMember().getGuild() : null))
			return callback.call(new CommandResultInfo(CommandProcessResult.COOLDOWN, command, commandName, prefix, command.getCoolDown().getCoolDown(info.getUser(), info.isFromGuild() ? info.getMember().getGuild() : null)));

		command.getCoolDown().renewCoolDown(info.getUser(), info.isFromGuild() ? info.getMember().getGuild() : null);

		String stripped = content.substring(commandName.length()).trim();

		CommandEvent event = eventCreator.createEvent(this, info, command, useEmbeds);
		if (command.getOptions().getAutoSendTyping() && info.getMessage() != null)
			info.getChannel().sendTyping().queue();

		ArgumentParseResult parsed = parseArguments(command, stripped, event);
		if (parsed.success == null)
			return callback.call(new CommandResultInfo(CommandProcessResult.INCORRECT_ARGUMENTS, command, commandName, prefix, parsed.failure));

		CommandArguments args = new CommandArgumentsImpl(parsed.success.getFirst(), parsed.success.getSecond());
		if (command.getOptions().isAsync()) {
			executor.submit(() -> execute0(command, callback, event, args, prefix, commandName));
		} else {
			execute0(command, callback, event, args, prefix, commandName);
		}

		return null;
	}

	protected Object execute0(@Nonnull RegisteredCommand command, @Nonnull Callback callback, @Nonnull CommandEvent event, @Nonnull CommandArguments args, @Nonnull String prefix, @Nonnull String commandName) {
		try {
			command.execute(event, args);
			return callback.call(CommandProcessResult.SUCCESS, command, prefix, commandName);
		} catch (Throwable ex) {
			LOGGER.error("An error occurred while executing the command '{}'", commandName, ex);
			return callback.call(CommandProcessResult.ERROR, command, prefix, commandName);
		}
	}

	protected Object doCommonChecks(@Nonnull RegisteredCommand command, @Nonnull Callback callback, @Nonnull String prefix, @Nonnull String commandName, @Nonnull CommandPreProcessInfo info) {
		if (info.getMessage() != null) {
			if (!command.getOptions().getAllowEdits() && info.getMessage().isEdited())
				return callback.call(CommandProcessResult.EDITS_UNSUPPORTED, command, prefix, commandName);
			if (!command.getOptions().getAllowWebHooks() && info.getMessage().isWebhookMessage())
				return callback.call(CommandProcessResult.INVALID_AUTHOR, command, prefix, commandName);
		}
		if (info.getInteraction() != null) {
			if (!command.getOptions().getAllowSlashCommands())
				return callback.call(CommandProcessResult.SLASH_COMMANDS_UNSUPPORTED);
		}

		if (!command.getOptions().getAllowBots() && info.getUser().isBot())
			return callback.call(CommandProcessResult.INVALID_AUTHOR, command, prefix, commandName);
		if (command.getOptions().getScope() == CommandScope.GUILD && !info.isFromGuild())
			return callback.call(CommandProcessResult.INVALID_SCOPE, command, prefix, commandName);
		if (command.getOptions().getScope() == CommandScope.PRIVATE && info.isFromGuild())
			return callback.call(CommandProcessResult.INVALID_SCOPE, command, prefix, commandName);
		if (command.getOptions().getTeam() && teamRoleManager != null) {
			if ((!teamRoleManager.isTeamRoleSet(info.getMember().getGuild()) || !teamRoleManager.hasTeamRole(info.getMember())) && !info.getMember().hasPermission(command.getOptions().getPermissionOrAdmin()))
				return callback.call(CommandProcessResult.MISSING_TEAM_ROLE, command, prefix, commandName);
		} else if (command.getOptions().getPermission() != Permission.UNKNOWN && !info.getMember().hasPermission(command.getOptions().getPermission()))
			return callback.call(CommandProcessResult.MISSING_PERMISSION, command, prefix, commandName);

		return null;
	}

	@Nonnull
	private ArgumentParseResult parseArguments(@Nonnull RegisteredCommand command, @Nonnull String message, @Nonnull CommandEvent event) {
		if (message.isEmpty()) return new ArgumentParseResult(new Tuple<>(new Class<?>[0], new Object[0]), null);
		List<Class<?>> types = new ArrayList<>(command.getArguments().length);
		List<Object> values = new ArrayList<>(command.getArguments().length);
		String[] args = message.split(" ");
		int index = 0;
		for (RequiredArgument argument : command.getArguments()) {
			int collectArgs = argument.getLength() == 0 ? args.length - index : argument.getLength();
			ArgumentParser<?, ?> parser = argument.getParser();
			ParserOptions options = parser.options();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < collectArgs; i++, index++) {
				if (index >= args.length) throw new IllegalArgumentException("Tried to access argument at index " + index + " for length " + args.length);
				if (i > 0) builder.append(options.getMultiWordSeparator());
				builder.append(args[index]);
			}
			Object parsed = null;
			try {
				parsed = parser.parseCasted(event, argument.getInfoContainer(), builder.toString());
				if (!parser.options().isNullable() && parsed == null) throw new NullPointerException("No value for input '" + builder + "'");

				if (parsed != null) {
					if (!argument.getClassOfArgument().isInstance(parsed))
						throw new IllegalArgumentParserValueException("Parser for class " + argument.getClassOfArgument().getName() + " returned " + parsed.getClass().getName());
					if (argument.getInfoContainer() != null && !parser.validateInfoContainerCasted(argument.getInfoContainer(), parsed))
						throw new IllegalArgumentException("Info container is not valid for the given input");
				}

				types.add(argument.getClassOfArgument());
				values.add(parsed);
			} catch (IllegalArgumentParserValueException ex) {
				throw new WrappedException(ex); // Internal error
			} catch (Exception ex) {
				Tuple<String, Object[]> errorMessage = parser.getErrorMessageCasted(argument.getInfoContainer(), parsed);
				return new ArgumentParseResult(null, errorMessage); // Invalid usage
			}
		}
		return new ArgumentParseResult(new Tuple<>(types.toArray(new Class[0]), values.toArray()), null);
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

	@Nonnull
	@Override
	public EventCreator getEventCreator() {
		return eventCreator;
	}

	@Nonnull
	@Override
	public CommandManager setEventCreator(@Nonnull EventCreator eventCreator) {
		this.eventCreator = eventCreator;
		return this;
	}

	@Nonnull
	@Override
	public Collection<CommandResolver> getResolvers() {
		return Collections.unmodifiableCollection(resolvers);
	}

	@Nonnull
	@Override
	public CommandManager addResolver(@Nonnull CommandResolver resolver) {
		if (!commands.isEmpty())
			LOGGER.warn("CommandResolver was changed after commands were registered, some commands may not be registered as expected");
		resolvers.add(resolver);
		return this;
	}

	@Override
	public boolean getReactToMentionPrefix() {
		return reactToMentionPrefix;
	}

	@Nonnull
	@Override
	public CommandManager setReactToMentionPrefix(boolean react) {
		this.reactToMentionPrefix = react;
		return this;
	}

	@Override
	public boolean getUseEmbeds() {
		return useEmbeds;
	}

	@Nonnull
	@Override
	public CommandManager setUseEmbeds(boolean useEmbeds) {
		this.useEmbeds = useEmbeds;
		return this;
	}

}
