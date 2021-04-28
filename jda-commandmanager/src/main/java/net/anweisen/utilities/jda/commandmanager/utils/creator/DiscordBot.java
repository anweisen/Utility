package net.anweisen.utilities.jda.commandmanager.utils.creator;

import net.anweisen.utilities.commons.common.function.ExceptionallyBiConsumer;
import net.anweisen.utilities.commons.config.FileDocument;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.SQLColumn;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.impl.DatabaseTeamRoleManager;
import net.anweisen.utilities.jda.commandmanager.impl.DefaultCommandManager;
import net.anweisen.utilities.jda.commandmanager.impl.language.DatabaseLanguageManager;
import net.anweisen.utilities.jda.commandmanager.impl.prefix.ConstantPrefixProvider;
import net.anweisen.utilities.jda.commandmanager.impl.prefix.DatabasePrefixProvider;
import net.anweisen.utilities.jda.commandmanager.language.LanguageManager;
import net.anweisen.utilities.jda.commandmanager.listener.CommandListener;
import net.anweisen.utilities.jda.commandmanager.listener.manager.CombinedEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.anweisen.utilities.jda.commandmanager.utils.creator.DiscordBotBuilder.logger;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class DiscordBot {

	private static DiscordBot instance;

	@Nonnull
	public static DiscordBot getInstance() {
		return instance;
	}

	private final BotConfigCreator config;

	private CommandManager commandManager;
	private ShardManager shardManager;
	private Database database;
	private ExecutorService executor;

	public DiscordBot() throws Exception {
		instance = this;

		config = new BotConfigCreator();
		executor = Executors.newCachedThreadPool();

		database = config.createDatabase();
	}

	protected final void init() throws Exception {
		DiscordBotBuilder builder = builder().validate();
		logger.info("Building bot with following configuration:" +
				"\n\t" + config +
				"\n\t" + builder +
				"\n\t" + builder.databaseConfig);

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
			LanguageManager languageManager = new DatabaseLanguageManager(database, builder.databaseConfig.getGuildTable(), builder.databaseConfig.getGuildKeyColumn(), builder.databaseConfig.getTeamRoleColumn());
			commandManager.setLanguageManager(languageManager);

			for (String filename : builder.fileLanguages) languageManager.readFile(filename);
			for (String filename : builder.resourceLanguages) languageManager.readResource(filename);

			languageManager.setDefaultLanguage(config.getDefaultLanguage());
		} else if (!builder.fileLanguages.isEmpty() || !builder.resourceLanguages.isEmpty()) {
			logger.warn("Languages were registered but no database for language management is setup!");
		}

		builder.parsers.forEach(parser -> commandManager.getParserContext().registerParser(parser.getFirst(), parser.getSecond(), parser.getThird()));

		commandManager.register(builder.commands);

		List<GatewayIntent> intents = Arrays.asList(builder.intents);
		if (!intents.contains(GatewayIntent.DIRECT_MESSAGES))
			logger.warn("Missing GatewayIntent.DIRECT_MESSAGES, no commands will be available in private chats");
		if (!intents.contains(GatewayIntent.GUILD_MESSAGES))
			logger.warn("Missing GatewayIntent.GUILD_MESSAGES, no commands will be available in guild chats");

		DefaultShardManagerBuilder shardManagerBuilder = DefaultShardManagerBuilder.create(config.getToken(), intents)
				.setCallbackPool(executor).setEventPool(executor)
				.setShardsTotal(config.getShards())
				.setMemberCachePolicy(builder.memberCachePolicy)
				.setEventManagerProvider(id -> new CombinedEventManager())
				.addEventListeners(new CommandListener(commandManager));

		builder.listener.forEach(shardManagerBuilder::addEventListeners);

		for (CacheFlag cache : CacheFlag.values()) {
			if (builder.cacheFlags.contains(cache)) {
				shardManagerBuilder.enableCache(cache);
			} else {
				shardManagerBuilder.disableCache(cache);
			}
		}

		shardManager = shardManagerBuilder.build();

	}

	@Nonnull
	@CheckReturnValue
	protected abstract DiscordBotBuilder builder();

	@Nonnull
	@CheckReturnValue
	protected static DiscordBotBuilder newBuilder() {
		return new DiscordBotBuilder();
	}

	@Nonnull
	public ShardManager getShardManager() {
		if (shardManager == null) throw new IllegalStateException("Bot is not built yet! Call init() first");
		return shardManager;
	}

	@Nonnull
	public Database getDatabase() {
		return database;
	}

	@Nonnull
	public ExecutorService getExecutor() {
		return executor;
	}

	@Nonnull
	public CommandManager getCommandManager() {
		return commandManager;
	}

	@Nonnull
	public BotConfigCreator getConfig() {
		return config;
	}

	@Nonnull
	public FileDocument getConfigDocument() {
		return config.getDocument();
	}

}
