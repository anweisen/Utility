package net.anweisen.utilities.jda.manager.hooks.registered;

import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.Command;
import net.anweisen.utilities.jda.manager.hooks.InterfacedCommand;
import net.anweisen.utilities.jda.manager.hooks.event.CommandArguments;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.hooks.option.CommandOptions;
import net.anweisen.utilities.jda.manager.hooks.option.CommandScope;
import net.anweisen.utilities.jda.manager.hooks.option.CoolDownScope;
import net.dv8tion.jda.api.Permission;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
		this(new CommandOptions(method.getAnnotation(Command.class)), CommandTask.of(method, holder), manager);
	}

	public RegisteredCommand(@Nonnull InterfacedCommand command, @Nonnull CommandManager manager) {
		this(command.options(), CommandTask.of(command), manager);
	}

	public RegisteredCommand(@Nonnull CommandOptions options, @Nonnull CommandTask task, @Nonnull CommandManager manager) {
		if (options.getScope() != CommandScope.GUILD && options.getCoolDownScope() == CoolDownScope.GUILD)
			throw new IllegalArgumentException("Cannot use CoolDownScope.GUILD without CommandScope.GUILD on command \"" + options.getFirstName() + '"');
		if (options.getScope() != CommandScope.GUILD && (options.getPermission() != Permission.UNKNOWN || options.getTeam()))
			throw new IllegalArgumentException("Cannot declare command permission or team command without CommandScope.GUILD on command \"" + options.getFirstName() + '"');

		this.options = options;
		this.task = task;

		List<RequiredArgument> arguments = new ArrayList<>();
		parseArguments(options.getUsage(), manager, arguments);

		this.arguments = arguments.toArray(new RequiredArgument[0]);
		this.syntax = createSyntax().toString();
		this.cooldown = new CommandCoolDown(options.getCoolDownScope(), options.getCoolDownSeconds());
	}

	@Nonnull
	private StringBuilder createSyntax() {
		StringBuilder builder = new StringBuilder();
		for (RequiredArgument argument : arguments) {
			if (builder.length() > 0) builder.append(" ");
			builder.append("<").append(argument.getName()).append(">");
		}
		return builder;
	}

	public void execute(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws Exception {
		task.execute(event, args);
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

	protected void parseArguments(@Nonnull String usage, @Nonnull CommandManager manager, @Nonnull Collection<RequiredArgument> arguments) {
		boolean inArgument = false;
		StringBuilder buffer = new StringBuilder();
		for (char c : usage.toCharArray()) {
			if (!inArgument) {
				if (c == ' ') continue; // Ignore spaces outside arguments
				if (c == '[') { // Start argument
					inArgument = true;
					continue;
				}
				throw new IllegalArgumentException("Unexpected '" + c + "' in " + usage + ", expected argument start '['");
			}
			if (c == ']') { // End argument
				inArgument = false;
				arguments.add(new RequiredArgument(manager.getParserContext(), this, buffer.toString()));
				buffer.setLength(0);
				continue;
			}

			buffer.append(c);
		}
	}

}
