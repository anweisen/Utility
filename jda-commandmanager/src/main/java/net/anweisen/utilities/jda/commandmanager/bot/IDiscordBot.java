package net.anweisen.utilities.jda.commandmanager.bot;

import net.anweisen.utilities.commons.config.FileDocument;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.bot.config.ConfigProvider;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface IDiscordBot {

	@Nonnull
	static IDiscordBot getInstance() {
		return DiscordBot.getInstance();
	}

	@Nonnull
	ConfigProvider getConfig();

	@Nonnull
	default FileDocument getConfigDocument() {
		return getConfig().getDocument();
	}

	@Nonnull
	Database getDatabase();

	/**
	 * @return the {@link ApplicationInfo} of this bot
	 *
	 * @throws IllegalStateException
	 *         If no info was received yet
	 */
	@Nonnull
	ApplicationInfo getApplicationInfo();

	/**
	 * @return the {@link CommandManager} of this bot
	 *
	 * @throws IllegalStateException
	 *         If the bot has not been initialized yet
	 */
	@Nonnull
	CommandManager getCommandManager();

	/**
	 * @return the {@link ShardManager} of this bot
	 *
	 * @throws IllegalStateException
	 *         If the bot has not been initialized yet
	 */
	@Nonnull
	ShardManager getShardManager();

	/**
	 * Returns the first shard of this shardmanager.
	 * If you are using multiple shard you should NOT use this but {@link #getShardManager()}.
	 *
	 * @return The first shard of this shardmanager
	 *
	 * @throws IllegalStateException
	 *         If no shard is built
	 */
	@Nonnull
	JDA getJDA();

	boolean isInitialized();

	boolean isReady();

}
