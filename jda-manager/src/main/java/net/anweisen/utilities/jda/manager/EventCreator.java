package net.anweisen.utilities.jda.manager;

import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utilities.jda.manager.process.CommandPreProcessInfo;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface EventCreator {

	@Nonnull
	CommandEvent createEvent(@Nonnull CommandManager manager, @Nonnull CommandPreProcessInfo info, @Nonnull RegisteredCommand command, boolean useEmbeds);

}