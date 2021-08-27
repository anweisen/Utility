package net.anweisen.utilities.jda.manager.process;

import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface CommandResultHandler {

	void handle(@Nonnull CommandManager manager, @Nonnull CommandEvent event, @Nonnull CommandResultInfo result);

}
