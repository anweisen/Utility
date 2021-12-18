package net.anweisen.utility.jda.manager.impl;

import net.anweisen.utility.jda.manager.CommandManager;
import net.anweisen.utility.jda.manager.EventCreator;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utility.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utility.jda.manager.impl.entities.event.MessageCommandEvent;
import net.anweisen.utility.jda.manager.impl.entities.event.SlashCommandEvent;
import net.anweisen.utility.jda.manager.process.CommandPreProcessInfo;

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
