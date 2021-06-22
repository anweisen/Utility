package net.anweisen.utilities.jda.manager.bot;

import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.bot.config.ConfigProvider;
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

	@Nonnull
	Database getDatabase();

	/**
	 * @return the {@link ApplicationInfo} of this bot
	 *
	 * @throws IllegalStateException
	 *         If no info was not received yet
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
	 * If you are using multiple shards you should NOT use this but {@link #getShardManager()}.
	 *
	 * @return The first shard of this shardmanager
	 *
	 * @throws IllegalStateException
	 *         If no shard is built yet
	 */
	@Nonnull
	JDA getJDA();

	@Nonnull
	ILogger getLogger();

	@Nonnull
	ScheduledExecutorService getExecutor();

	boolean isInitialized();

	boolean isReady();

	int getReadyShardCount();

	@Nonnull
	List<JDA> getReadyShards();

}
