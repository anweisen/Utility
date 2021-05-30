package net.anweisen.utilities.jda.commandmanager.hooks.registered;

import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandArguments;
import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.hooks.InterfacedCommand;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface CommandTask {

	void execute(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws Exception;

	@Nonnull
	@CheckReturnValue
	static CommandTask of(@Nonnull InterfacedCommand command) {
		return command::onCommand;
	}

	@Nonnull
	@CheckReturnValue
	static CommandTask of(@Nonnull Method method, @Nonnull Object holder) {
		return (event, args) -> {
			boolean staticMethod = Modifier.isStatic(method.getModifiers());
			method.setAccessible(true);
			method.invoke(staticMethod ? null : holder, event, args);
		};
	}

}
