package net.anweisen.utilities.jda.commandmanager.impl.resolver;

import net.anweisen.utilities.commons.misc.ReflectionUtils;
import net.anweisen.utilities.jda.commandmanager.Command;
import net.anweisen.utilities.jda.commandmanager.CommandArguments;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.registered.RegisteredCommand;
import net.anweisen.utilities.jda.commandmanager.registered.resolver.CommandResolver;

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
		for (Method method : ReflectionUtils.getMethodsAnnotatedWith(command instanceof Class ? (Class<?>) command : command.getClass(), Command.class)) {
			Class<?>[] parameters = method.getParameterTypes();
			if (!Modifier.isStatic(method.getModifiers()) && command instanceof Class) continue; // Ignore instance commands when registering via class
			if (parameters.length != 2) throw new IllegalArgumentException("Cannot register " + method + ", parameter count is not 2");
			if (!parameters[0].isAssignableFrom(manager.getEventCreator().getEventClass()) || !parameters[1].isAssignableFrom(CommandArguments.class))
				throw new IllegalArgumentException("Cannot register " + method + ", parameters are not (" + manager.getEventCreator().getEventClass().getName() + ", " + CommandArguments.class.getName() + ")");

			RegisteredCommand registeredCommand = new RegisteredCommand(command, method, manager);
			commands.add(registeredCommand);
		}
		return commands;
	}

}
