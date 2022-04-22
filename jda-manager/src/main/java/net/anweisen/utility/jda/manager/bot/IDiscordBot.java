package net.anweisen.utility.jda.manager.bot;

import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.database.Database;
import net.anweisen.utility.jda.manager.CommandManager;
import net.anweisen.utility.jda.manager.bot.config.ConfigProvider;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

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

	/**
	 * @return the {@link Database} of this bot
	 * @throws IllegalStateException If the bot has not been initialized yet
	 */
	@Nonnull
	Database getDatabase();

	/**
	 * @return the {@link ApplicationInfo} of this bot
	 * @throws IllegalStateException If no info was received yet
	 */
	@Nonnull
	ApplicationInfo getApplicationInfo();

	/**
	 * @return the {@link CommandManager} of this bot
	 * @throws IllegalStateException If the bot has not been initialized yet
	 */
	@Nonnull
	CommandManager getCommandManager();

	/**
	 * @return the {@link ShardManager} of this bot
	 * @throws IllegalStateException If the bot has not been initialized yet
	 */
	@Nonnull
	ShardManager getShardManager();

	/**
	 * Returns the first shard of this shardmanager.
	 * If you are using multiple shards you should NOT use this but {@link #getShardManager()}.
	 *
	 * @return The first shard of this shardmanager
	 * @throws IllegalStateException If no shard is built yet
	 */
	@Nonnull
	JDA getJDA();

	@Nonnull
	ILogger getLogger();

	@Nonnull
	ScheduledExecutorService getExecutor();

	/**
	 * @return whether the bot has been initialized
	 */
	boolean isInitialized();

	/**
	 * @return whether all shards are ready
	 */
	boolean isReady();

	int getReadyShardCount();

	@Nonnull
	List<JDA> getReadyShards();

}
