package net.anweisen.utilities.jda.commandmanager.registered;

import net.anweisen.utilities.jda.commandmanager.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class RegisteredCommand {

	private final CommandTask task;
	private final CommandOptions options;

	private final String syntax; // syntax parsed based on the arguments
	private final RequiredArgument[] arguments; // arguments parsed by the usage
	private final CommandCoolDown cooldown;

	public RegisteredCommand(@Nonnull Object holder, @Nonnull Method method, @Nonnull CommandManager manager) {
		this(new CommandOptions(method.getAnnotation(Command.class)), (event, args) -> {
			method.setAccessible(true);
			method.invoke(holder instanceof Class ? null : holder, event, args);
		}, manager);
	}

	public RegisteredCommand(@Nonnull InterfacedCommand command, @Nonnull CommandManager manager) {
		this(command.options(), command::onCommand, manager);
	}

	public RegisteredCommand(@Nonnull CommandOptions options, @Nonnull CommandTask task, @Nonnull CommandManager manager) {
		if (options.getField() != CommandField.GUILD && options.getCoolDownScope() == CoolDownScope.GUILD) throw new IllegalArgumentException("Cannot use CoolDownScope.GUILD without CommandField.GUILD");

		List<RequiredArgument> arguments = new ArrayList<>();
		parseArguments(options.getUsage(), manager, arguments);

		this.arguments = arguments.toArray(new RequiredArgument[0]);
		this.syntax = createSyntax().toString();
		this.cooldown = new CommandCoolDown(options.getCoolDownScope(), options.getCoolDownSeconds());
		this.options = options;
		this.task = task;
	}

	private StringBuilder createSyntax() {
		StringBuilder builder = new StringBuilder();
		for (RequiredArgument argument : arguments) {
			if (builder.length() > 0) builder.append(" ");
			builder.append("<").append(argument.getName()).append(">");
		}
		return builder;
	}

	public void execute(@Nonnull CommandEvent event, @Nonnull CommandArguments args) {
		try {
			task.execute(event, args);
		} catch (Throwable ex) {
			CommandManager.LOGGER.error("An error occurred while processing a command", ex);
		}
	}

	@Nonnull
	public String getSyntax() {
		return syntax;
	}

	@Nonnull
	public RequiredArgument[] getArguments() {
		return arguments;
	}

	@Nonnull
	public CommandCoolDown getCoolDown() {
		return cooldown;
	}

	@Nonnull
	public CommandTask getTask() {
		return task;
	}

	@Nonnull
	public CommandOptions getOptions() {
		return options;
	}

	@Override
	public String toString() {
		return "RegisteredCommand{" +
				"options=" + options +
				", arguments=" + Arrays.toString(arguments) +
				'}';
	}

	public static void parseArguments(@Nonnull String usage, @Nonnull CommandManager manager, @Nonnull Collection<RequiredArgument> arguments) {
		boolean inArgument = false;
		StringBuilder buffer = new StringBuilder();
		for (char c : usage.toCharArray()) {
			if (!inArgument) {
				if (c == ' ') continue; // Ignore spaces
				if (c == '[') { // Start argument
					inArgument = true;
					continue;
				}
				throw new IllegalArgumentException("Unexpected '" + c + "' in " +usage + "; Expected argument start ]");
			}
			if (c == ']') { // End argument
				inArgument = false;
				arguments.add(new RequiredArgument(manager.getParserContext(), buffer.toString()));
				buffer = new StringBuilder();
				continue;
			}

			buffer.append(c);
		}
	}

}
