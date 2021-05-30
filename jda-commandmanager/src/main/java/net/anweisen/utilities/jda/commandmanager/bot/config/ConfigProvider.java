package net.anweisen.utilities.jda.commandmanager.bot.config;

import net.anweisen.utilities.commons.common.WrappedException;
import net.anweisen.utilities.commons.config.FileDocument;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.DatabaseConfig;
import net.anweisen.utilities.database.SimpleDatabaseTypeResolver;
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
	FileDocument getDocument();

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
		return getDocument().getStringList("language-files");
	}

	@Nonnull
	default DatabaseConfig getTargetDatabaseConfig() {
		return new DatabaseConfig(getDocument().getDocument("database." + getDatabaseType()));
	}

	default int getShards() {
		return getDocument().getInt("shards");
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
