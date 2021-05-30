package net.anweisen.utilities.jda.commandmanager.bot;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
final class BotSetupListener {

	private final DiscordBot bot;

	public BotSetupListener(@Nonnull DiscordBot bot) {
		this.bot = bot;
	}

	@SubscribeEvent
	public void onReady(@Nonnull ReadyEvent event) {
		event.getJDA().removeEventListener(this);
		bot.handleShardReady(event.getJDA());
	}

}
