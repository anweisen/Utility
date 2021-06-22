package net.anweisen.utilities.jda.manager.bot;

import net.anweisen.utilities.common.collection.Tuple;
import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.common.misc.StringUtils;
import net.anweisen.utilities.database.SQLColumn;
import net.anweisen.utilities.database.SQLColumn.Type;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.manager.hooks.option.CommandOptions;
import net.anweisen.utilities.jda.manager.hooks.registered.CommandTask;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class DiscordBotBuilder {

	protected static final ILogger logger = ILogger.forThisClass();

	protected final EnumSet<CacheFlag> cacheFlags = EnumSet.allOf(CacheFlag.class);
	protected final Collection<Tuple<CommandTask, CommandOptions>> taskCommands = new ArrayList<>();
	protected final Collection<Object> commands = new ArrayList<>();
	protected final Collection<Object> listener = new ArrayList<>();
	protected final Collection<String> resourceLanguages = new ArrayList<>();
	protected final Collection<String> fileLanguages = new ArrayList<>();
	protected final List<Supplier<Activity>> activities = new ArrayList<>();
	protected final Map<String, Tuple<Class<?>, ArgumentParser<?, ?>>> parsers = new HashMap<>();
	protected final Map<String, SQLColumn[]> tables = new HashMap<>();
	protected MemberCachePolicy memberCachePolicy = MemberCachePolicy.DEFAULT;
	protected GatewayIntent[] intents;
	protected BotDatabaseConfig databaseConfig;
	protected boolean requireDatabase = false;
	protected boolean useEmbeds = false;
	protected boolean disableSlashCommands = false;
	protected int activityUpdateRate = -1;
	protected String activityPrefix = "";

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder intents(@Nonnull GatewayIntent... intents) {
		this.intents = intents;
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder allIntents() {
		logger.warn("Using all intents, this can cause lots of unnecessary traffic, please consider configuring your gateway intents");
		return intents(GatewayIntent.values());
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder enableCache(@Nonnull CacheFlag... flags) {
		cacheFlags.clear();
		cacheFlags.addAll(Arrays.asList(flags));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder disableCache(@Nonnull CacheFlag... flags) {
		cacheFlags.removeAll(Arrays.asList(flags));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder withCache(@Nonnull CacheFlag... flags) {
		cacheFlags.clear();
		return enableCache(flags);
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder memberCachePolicy(@Nonnull MemberCachePolicy policy) {
		this.memberCachePolicy = policy;
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder commands(@Nonnull Object... commands) {
		this.commands.addAll(Arrays.asList(commands));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder commands(@Nonnull CommandTask task, @Nonnull CommandOptions options) {
		this.taskCommands.add(Tuple.of(task, options));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder listeners(@Nonnull Object... listeners) {
		this.listener.addAll(Arrays.asList(listeners));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder resourceLanguages(@Nonnull String... filenames) {
		resourceLanguages.addAll(Arrays.asList(filenames));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder fileLanguages(@Nonnull String... filenames) {
		fileLanguages.addAll(Arrays.asList(filenames));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder argumentParser(@Nonnull String key, @Nonnull Class<?> clazz, ArgumentParser<?, ?> parser) {
		parsers.put(key, new Tuple<>(clazz, parser));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder databaseConfig(@Nonnull String guildTable, @Nonnull String guildKeyColumn,
	                                        @Nullable String teamRoleColumn, @Nullable String languageColumn, @Nullable String prefixColumn) {
		this.databaseConfig = new BotDatabaseConfig(guildTable, guildKeyColumn, teamRoleColumn, languageColumn, prefixColumn);
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder defaultDatabaseConfig() {
		return databaseConfig("guilds", "guildId", "teamRoleId", "language", "prefix");
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder createTable(@Nonnull String name, @Nonnull SQLColumn... columns) {
		tables.put(name, columns);
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder requireDatabase() {
		requireDatabase = true;
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder activityPrefix(@Nullable String activityPrefix) {
		this.activityPrefix = activityPrefix == null ? "" : activityPrefix;
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder disableSlashCommands() {
		this.disableSlashCommands = true;
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder activity(@Nonnull String activity, @Nonnull Object... args) {
		return activity(Activity::playing, activity, args);
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder activity(@Nonnull Function<String, Activity> creator, @Nonnull String activity, @Nonnull Object... args) {
		activities.add(() -> creator.apply(StringUtils.format(activityPrefix + activity, args)));
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder activity(@Nonnull Supplier<Activity> activity) {
		activities.add(activity);
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder updateActivity(@Nonnegative int seconds) {
		if (seconds < 1) throw new IllegalArgumentException("Activity update rate cannot be smaller than 1 second");
		this.activityUpdateRate = seconds;
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBotBuilder useEmbedsInCommands(boolean useEmbeds) {
		this.useEmbeds = useEmbeds;
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public DiscordBot buildNewBot() throws Exception {
		class AnonymousBot extends DiscordBot {
			public AnonymousBot() throws Exception {
				init();
			}

			@Nonnull
			@Override
			protected DiscordBotBuilder builder() {
				return DiscordBotBuilder.this;
			}
		}

		return new AnonymousBot();
	}

	@Nonnull
	@CheckReturnValue
	DiscordBotBuilder validate() {
		if (intents == null || intents.length == 0) {
			logger.warn("No intents were given, defaulting to all intents");
			allIntents();
		}
		if (activityUpdateRate < 0 && !activities.isEmpty()) {
			logger.warn("No activity update rate was given, defaulting to 15 seconds");
			updateActivity(15);
		}
		if (databaseConfig != null) {
			if (!tables.containsKey(databaseConfig.getGuildTable())) {
				List<SQLColumn> columns = new ArrayList<>(1);
				columns.add(new SQLColumn(databaseConfig.getGuildKeyColumn(), Type.VARCHAR, 18));

				if (databaseConfig.getPrefixColumn() != null)
					columns.add(new SQLColumn(databaseConfig.getPrefixColumn(), Type.VARCHAR, 100));
				if (databaseConfig.getLanguageColumn() != null)
					columns.add(new SQLColumn(databaseConfig.getLanguageColumn(), Type.VARCHAR, 32));
				if (databaseConfig.getTeamRoleColumn() != null)
					columns.add(new SQLColumn(databaseConfig.getTeamRoleColumn(), Type.VARCHAR, 18));

				if (columns.size() > 0)
					createTable(databaseConfig.getGuildTable(), columns.toArray(new SQLColumn[0]));
			}
		}

		return this;
	}

	@Override
	@CheckReturnValue
	public String toString() {
		return "BotBuilder{" +
				"cacheFlags=" + cacheFlags +
				", resourceLanguages=" + resourceLanguages +
				", fileLanguages=" + fileLanguages +
				", intents=" + Arrays.toString(intents) +
				'}';
	}
}
