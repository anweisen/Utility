package net.anweisen.utilities.jda.commandmanager.registered;

import net.anweisen.utilities.jda.commandmanager.*;

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
public class RegisteredCommand {

	private final Command annotation;
	private final Method method;
	private final Object holder;
	private final RequiredArgument[] arguments;
	private final String syntax;
	private final CommandCoolDown cooldown;

	public RegisteredCommand(@Nonnull Object holder, @Nonnull Method method, @Nonnull CommandManager manager) {
		this.annotation = method.getAnnotation(Command.class);
		this.method = method;
		this.holder = holder;

		if (annotation.field() != CommandField.GUILD && annotation.cooldownScope() == CoolDownScope.GUILD) throw new IllegalArgumentException("Cannot use CoolDownScope.GUILD without CommandField.GUILD");

		List<RequiredArgument> arguments = new ArrayList<>();
		parseArguments(manager, arguments);

		this.arguments = arguments.toArray(new RequiredArgument[0]);
		this.syntax = createSyntax().toString();
		this.cooldown = new CommandCoolDown(annotation.cooldownScope(), annotation.cooldownSeconds());
	}

	private void parseArguments(@Nonnull CommandManager manager, @Nonnull Collection<RequiredArgument> arguments) {
		boolean inArgument = false;
		StringBuilder buffer = new StringBuilder();
		for (char c : annotation.usage().toCharArray()) {
			if (!inArgument) {
				if (c == ' ') continue; // Ignore spaces
				if (c == '[') { // Start argument
					inArgument = true;
					continue;
				}
				throw new IllegalArgumentException("Unexpected '" + c + "' in " + annotation.usage() + "; Expected argument start ]");
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
			method.setAccessible(true);
			method.invoke(holder, event, args);
		} catch (Throwable ex) {
			CommandManager.LOGGER.error("An error occurred while processing a command", ex);
		}
	}

	@Nonnull
	public String getSyntax() {
		return syntax;
	}

	@Nonnull
	public Object getHolder() {
		return holder;
	}

	@Nonnull
	public RequiredArgument[] getArguments() {
		return arguments;
	}

	@Nonnull
	public Command getAnnotation() {
		return annotation;
	}

	@Nonnull
	public Method getMethod() {
		return method;
	}

	@Nonnull
	public CommandCoolDown getCoolDown() {
		return cooldown;
	}

	@Override
	public String toString() {
		return "RegisteredCommand{" +
				"name=" + Arrays.toString(annotation.name()) +
				", usage='" + annotation.usage() + "'" +
				", field=" + annotation.field() +
				", permission=" + annotation.permission() +
				", async=" + annotation.async() +
				'}';
	}
}
