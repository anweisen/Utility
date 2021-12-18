package net.anweisen.utility.jda.manager.bot.config;

import net.anweisen.utility.common.collection.WrappedException;
import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.DatabaseConfig;
import net.anweisen.utility.database.SimpleDatabaseTypeResolver;
import net.anweisen.utility.document.wrapped.StorableDocument;
import net.dv8tion.jda.api.OnlineStatus;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ConfigProvider {

	@Nonnull
	StorableDocument getDocument();

	default String getToken() {
		return getDocument().getString("token");
	}

	default String getAuthToken() {
		return getDocument().getString("auth-token");
	}

	default String getDatabaseType() {
		return getDocument().getString("database.type");
	}

	default String getDefaultLanguage() {
		return getDocument().getString("default-language");
	}

	default String getDefaultPrefix() {
		return getDocument().getString("default-prefix", "!");
	}

	@Nonnull
	default List<String> getLanguageFiles() {
		return getDocument().getBundle("language-files").toStrings();
	}

	@Nonnull
	default DatabaseConfig getTargetDatabaseConfig() {
		return new DatabaseConfig(getDocument().getDocument("database." + getDatabaseType()));
	}

	default int getShards() {
		return getDocument().getInt("shards", -1);
	}

	@Nonnull
	default OnlineStatus getOnlineStatus() {
		return getDocument().getEnum("online-status", OnlineStatus.ONLINE);
	}

	@Nonnull
	default Database createDatabase() {
		Class<? extends Database> databaseClass = SimpleDatabaseTypeResolver.findDatabaseType(getDatabaseType());
		if (databaseClass == null) throw new IllegalArgumentException("No such database is known '" + getDatabaseType() + "'");
		try {
			Constructor<? extends Database> constructor = databaseClass.getDeclaredConstructor(DatabaseConfig.class);
			return constructor.newInstance(getTargetDatabaseConfig());
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
			throw new IllegalArgumentException("Database type '" + getDatabaseType() + "' (" + databaseClass.getSimpleName() + ") does not support config instantiation");
		} catch (InvocationTargetException ex) {
			throw new WrappedException(ex);
		}
	}

}
