package net.anweisen.utilities.jda.manager.impl.resolver;

import net.anweisen.utilities.common.misc.ReflectionUtils;
import net.anweisen.utilities.jda.manager.hooks.Command;
import net.anweisen.utilities.jda.manager.hooks.event.CommandArguments;
import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utilities.jda.manager.hooks.registered.CommandResolver;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class AnnotatedCommandResolver implements CommandResolver {

	@Nonnull
	@Override
	public Collection<RegisteredCommand> resolve(@Nonnull Object command, @Nonnull CommandManager manager) {
		List<RegisteredCommand> commands = new ArrayList<>();
		boolean isClass = command instanceof Class;
		Class<?> clazz = isClass ? (Class<?>) command : command.getClass();
		for (Method method : ReflectionUtils.getMethodsAnnotatedWith(clazz, Command.class)) {
			Class<?>[] parameters = method.getParameterTypes();
			if (!Modifier.isStatic(method.getModifiers()) && isClass) continue; // Ignore instance commands when registering via class
			if (parameters.length != 2) throw new IllegalArgumentException("Cannot register " + method + ", parameter count is not 2");
			if (!parameters[0].isAssignableFrom(CommandEvent.class) || !parameters[1].isAssignableFrom(CommandArguments.class))
				throw new IllegalArgumentException("Cannot register " + method + ", parameters are not (" + CommandEvent.class.getName() + ", " + CommandArguments.class.getName() + ")");

			RegisteredCommand registeredCommand = new RegisteredCommand(command, method, manager);
			commands.add(registeredCommand);
		}
		return commands;
	}

}
