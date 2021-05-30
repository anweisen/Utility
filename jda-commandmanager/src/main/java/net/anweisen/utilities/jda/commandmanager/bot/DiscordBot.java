package net.anweisen.utilities.jda.commandmanager.bot;

import net.anweisen.utilities.commons.common.NamedThreadFactory;
import net.anweisen.utilities.commons.function.ExceptionallyBiConsumer;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.SQLColumn;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.bot.config.BotConfigCreator;
import net.anweisen.utilities.jda.commandmanager.impl.DatabaseTeamRoleManager;
import net.anweisen.utilities.jda.commandmanager.impl.DefaultCommandManager;
import net.anweisen.utilities.jda.commandmanager.impl.language.DatabaseLanguageManager;
import net.anweisen.utilities.jda.commandmanager.impl.prefix.ConstantPrefixProvider;
import net.anweisen.utilities.jda.commandmanager.impl.prefix.DatabasePrefixProvider;
import net.anweisen.utilities.jda.commandmanager.language.LanguageManager;
import net.anweisen.utilities.jda.commandmanager.listener.CommandListener;
import net.anweisen.utilities.jda.commandmanager.listener.manager.CombinedEventManager;
import net.anweisen.utilities.jda.commandmanager.bot.config.ConfigProvider;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.sharding.ThreadPoolProvider;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class DiscordBot implements IDiscordBot {

	private static DiscordBot instance;

	@Nonnull
	public static DiscordBot getInstance() {
		return instance;
	}

	private final ConfigProvider config;

	private boolean initialized = false;

	private CommandManager commandManager;
	private ShardManager shardManager;
	private Database database;
	private ApplicationInfo applicationInfo;

	public DiscordBot() throws Exception {
		this(new BotConfigCreator());
	}

	public DiscordBot(@Nonnull ConfigProvider configProvider) throws Exception {
		instance = this;

		config = configProvider;
		database = config.createDatabase();
	}

	protected final void init() throws Exception {
		if (initialized) throw new IllegalStateException("init() was already called.");
		initialized = true;

		DiscordBotBuilder builder = builder().validate();
		DiscordBotBuilder.logger.debug("Building bot with following configuration:" +
				"\n\t" + config +
				"\n\t" + builder +
				"\n\t" + (builder.databaseConfig == null ? "BotDatabaseConfig{null}" : builder.databaseConfig));

		boolean requireDatabase = builder.databaseConfig != null || !builder.tables.isEmpty() || builder.requireDatabase;
		if (builder.databaseConfig == null) {
			builder.databaseConfig = new BotDatabaseConfig("guilds", "guildId", null, null, null);
		}
		if (requireDatabase) {
			database.connect();
			builder.tables.forEach((ExceptionallyBiConsumer<String, SQLColumn[]>) (name, columns) -> database.createTableIfNotExists(name, columns));
		}

		builder.fileLanguages.addAll(config.getLanguageFiles());
		commandManager = new DefaultCommandManager(builder.databaseConfig.getPrefixColumn() == null
				? new ConstantPrefixProvider(config.getDefaultPrefix())
				: new DatabasePrefixProvider(config.getDefaultPrefix(), database, builder.databaseConfig.getGuildTable(), builder.databaseConfig.getGuildKeyColumn(), builder.databaseConfig.getPrefixColumn()));

		if (builder.databaseConfig.getTeamRoleColumn() != null) {
			commandManager.setTeamRoleManager(new DatabaseTeamRoleManager(database, builder.databaseConfig.getGuildTable(), builder.databaseConfig.getGuildKeyColumn(), builder.databaseConfig.getTeamRoleColumn()));
		}

		if (builder.databaseConfig.getLanguageColumn() != null) {
			LanguageManager languageManager = new DatabaseLanguageManager(database, builder.databaseConfig.getGuildTable(), builder.databaseConfig.getGuildKeyColumn(), builder.databaseConfig.getLanguageColumn());
			commandManager.setLanguageManager(languageManager);

			for (String filename : builder.fileLanguages) languageManager.readFile(filename);
			for (String filename : builder.resourceLanguages) languageManager.readResource(filename);

			languageManager.setDefaultLanguage(config.getDefaultLanguage());
		} else if (!builder.fileLanguages.isEmpty() || !builder.resourceLanguages.isEmpty()) {
			DiscordBotBuilder.logger.warn("Languages were registered but no database for language management is setup!");
		}

		builder.parsers.forEach((key, parser) -> commandManager.getParserContext().registerParser(key, parser.getFirst(), parser.getSecond()));

		commandManager.register(builder.commands);

		List<GatewayIntent> intents = Arrays.asList(builder.intents);
		if (!intents.contains(GatewayIntent.DIRECT_MESSAGES))
			DiscordBotBuilder.logger.warn("Missing GatewayIntent.DIRECT_MESSAGES, no commands will be available in private chats");
		if (!intents.contains(GatewayIntent.GUILD_MESSAGES))
			DiscordBotBuilder.logger.warn("Missing GatewayIntent.GUILD_MESSAGES, no commands will be available in guild chats");

		DefaultShardManagerBuilder shardManagerBuilder = DefaultShardManagerBuilder.create(config.getToken(), intents)
				.setCallbackPoolProvider(newThreadPoolProvider("Callback"))
				.setEventPoolProvider(newThreadPoolProvider("Events"))
				.setShardsTotal(config.getShards())
				.setMemberCachePolicy(builder.memberCachePolicy)
				.setEventManagerProvider(shardId -> new CombinedEventManager())
				.setStatusProvider(shardId -> config.getOnlineStatus())
				.addEventListeners(new CommandListener(commandManager), new BotSetupListener(this));

		builder.listener.forEach(shardManagerBuilder::addEventListeners);

		for (CacheFlag cache : CacheFlag.values()) {
			if (builder.cacheFlags.contains(cache)) {
				shardManagerBuilder.enableCache(cache);
			} else {
				shardManagerBuilder.disableCache(cache);
			}
		}

		shardManager = shardManagerBuilder.build();

		startup();

		if (!builder.activities.isEmpty()) {
			new Timer("BotActivityChanger").schedule(new TimerTask() {

				private int index = 0;

				@Override
				public void run() {
					if (index >= builder.activities.size()) {
						index = 0;
					}

					Supplier<Activity> activitySupplier = builder.activities.get(index);
					Activity activity = activitySupplier.get();
					shardManager.setActivity(activity);

					index++;
				}
			}, 3000, builder.activityUpdateRate * 1000L);
		}

	}

	protected void startup() throws Exception {
	}

	@Nonnull
	private ThreadPoolProvider<?> newThreadPoolProvider(@Nonnull String scope) {
		return new ThreadPoolProvider<ExecutorService>() {

			@Nonnull
			@Override
			public ExecutorService provide(int shardId) {
				return Executors.newCachedThreadPool(new NamedThreadFactory(String.format("Shard-%s-%s", shardId + 1, scope)));
			}

			@Override
			public boolean shouldShutdownAutomatically(int shardId) {
				return true;
			}

		};
	}

	void handleShardReady(@Nonnull JDA jda) {
		if (applicationInfo == null) {
			jda.retrieveApplicationInfo().queue(applicationInfo -> this.applicationInfo = applicationInfo);
		}
	}

	@Nonnull
	@CheckReturnValue
	protected abstract DiscordBotBuilder builder() throws Exception;

	@Nonnull
	@CheckReturnValue
	protected static DiscordBotBuilder newBuilder() {
		return new DiscordBotBuilder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	public ShardManager getShardManager() {
		if (shardManager == null) throw new IllegalStateException("Bot is not built yet! Call init() first");
		return shardManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	public JDA getJDA() {
		if (shardManager == null) throw new IllegalStateException("Bot is not built yet! Call init() first");
		return shardManager.getShardCache().stream().findFirst().orElseThrow(() -> new IllegalStateException("No JDA is built yet"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	public Database getDatabase() {
		return database;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	public CommandManager getCommandManager() {
		if (commandManager == null) throw new IllegalStateException("Bot is not built yet! Call init() first");
		return commandManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	public ConfigProvider getConfig() {
		return config;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	public ApplicationInfo getApplicationInfo() {
		if (!initialized) throw new IllegalStateException("Bot is not built yet! Call init() first");
		if (applicationInfo == null) throw new IllegalStateException("ApplicationInfo not received yet");
		return applicationInfo;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public boolean isReady() {
		return isInitialized();
	}

}
