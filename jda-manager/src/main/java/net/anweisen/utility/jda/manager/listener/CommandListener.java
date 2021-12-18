package net.anweisen.utility.jda.manager.listener;

import net.anweisen.utility.jda.manager.CommandManager;
import net.anweisen.utility.jda.manager.process.CommandPreProcessInfo;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CommandListener extends ListenerAdapter {

	private final CommandManager manager;

	public CommandListener(@Nonnull CommandManager manager) {
		this.manager = manager;
	}

	@Override
	@SubscribeEvent
	public void onMessageUpdate(@Nonnull MessageUpdateEvent event) {
		manager.handleCommand(new CommandPreProcessInfo(event));
	}

	@Override
	@SubscribeEvent
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		manager.handleCommand(new CommandPreProcessInfo(event));
	}

	@Override
	@SubscribeEvent
	public void onSlashCommand(@Nonnull SlashCommandEvent event) {
		event.deferReply().queue(); // Acknowledge event
		manager.handleCommand(new CommandPreProcessInfo(event));
	}

}
