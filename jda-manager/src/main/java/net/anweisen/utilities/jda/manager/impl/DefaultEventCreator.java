package net.anweisen.utilities.jda.manager.impl;

import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.EventCreator;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utilities.jda.manager.impl.entities.event.MessageCommandEvent;
import net.anweisen.utilities.jda.manager.impl.entities.event.SlashCommandEvent;
import net.anweisen.utilities.jda.manager.process.CommandPreProcessInfo;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultEventCreator implements EventCreator {

	@Nonnull
	@Override
	public CommandEvent createEvent(@Nonnull CommandManager manager, @Nonnull CommandPreProcessInfo info, @Nonnull RegisteredCommand command, boolean useEmbeds) {
		boolean disableMentions = command.getOptions().getDisableMentions();
		if (info.getMessage() != null) {            // normal execution
			return new MessageCommandEvent(manager, info.getMessage(), info.getMember(), disableMentions, useEmbeds);
		} else if (info.getInteraction() != null) { // slash command
			return new SlashCommandEvent(manager, info.getInteraction(), info.getMember(), disableMentions, useEmbeds);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
