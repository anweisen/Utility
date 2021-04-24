package net.anweisen.utilities.jda.commandmanager;

import net.anweisen.utilities.jda.commandmanager.process.CommandPreProcessInfo;
import net.anweisen.utilities.jda.commandmanager.registered.RegisteredCommand;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface EventCreator {

	@Nonnull
	CommandEvent createEvent(@Nonnull CommandManager manager, @Nonnull CommandPreProcessInfo info, @Nonnull RegisteredCommand command);

	@Nonnull
	Class<? extends CommandEvent> getEventClass();

}