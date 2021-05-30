package net.anweisen.utilities.jda.commandmanager.impl;

import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.EventCreator;
import net.anweisen.utilities.jda.commandmanager.impl.entities.CommandEventImpl;
import net.anweisen.utilities.jda.commandmanager.process.CommandPreProcessInfo;
import net.anweisen.utilities.jda.commandmanager.hooks.registered.RegisteredCommand;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultEventCreator implements EventCreator {

	@Nonnull
	@Override
	public CommandEvent createEvent(@Nonnull CommandManager manager, @Nonnull CommandPreProcessInfo info, @Nonnull RegisteredCommand command) {
		return new CommandEventImpl(manager, info.getMessage(), info.getMember(), command.getOptions().getDisableMentions());
	}

	@Nonnull
	@Override
	public Class<? extends CommandEvent> getEventClass() {
		return CommandEvent.class;
	}

}
