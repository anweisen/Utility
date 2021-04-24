package net.anweisen.utilities.jda.commandmanager.utils;

import net.anweisen.utilities.commons.config.document.GsonDocument;
import net.anweisen.utilities.commons.config.document.wrapper.FileDocumentWrapper;
import net.anweisen.utilities.commons.misc.FileUtils;
import net.anweisen.utilities.commons.misc.WrappedException;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.DatabaseConfig;
import net.anweisen.utilities.database.SimpleDatabaseTypeResolver;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class BotConfigCreator {

	public static final Object[][] DEFAULT_VALUES = {

			{ "token",                          "secret" },
			{ "auth-token",                     "unknown" },
			{ "database.type",                  "mysql" },

			{ "database.mongodb.host",          "127.0.0.1" },
			{ "database.mongodb.port",          "27017" },
			{ "database.mongodb.database",      "discordbot" },
			{ "database.mongodb.auth-database", "admin" },
			{ "database.mongodb.user",          "root" },
			{ "database.mongodb.password",      "secret" },

			{ "database.mysql.host",            "127.0.0.1" },
			{ "database.mysql.port",            "3306" },
			{ "database.mysql.database",        "discordbot" },
			{ "database.mysql.user",            "root" },
			{ "database.mysql.password",        "secret" },

			{ "database.sqlite.file",           "database.db" },

	};

	private final FileDocumentWrapper document;

	public BotConfigCreator() throws IOException {
		this(new File("config.json"), DEFAULT_VALUES);
	}

	public BotConfigCreator(@Nonnull File file) throws IOException {
		this(file, DEFAULT_VALUES);
	}

	public BotConfigCreator(@Nonnull File file, @Nonnull Object[][] defaultValues) throws IOException {

		document = new FileDocumentWrapper(file, file.exists() ? new GsonDocument(file) : new GsonDocument());

		if (!file.exists()) {
			FileUtils.createFilesIfNecessary(file);
			for (Object[] pair : defaultValues) {
				String key = (String) pair[0];
				String value = (String) pair[1];
				document.set(key, value);
			}
			document.save();
			throw new FileNotFoundException("Config file " + file.getName() + " was not found, created a new one");
		}

	}

	@Nonnull
	public FileDocumentWrapper getDocument() {
		return document;
	}

	public String getToken() {
		return document.getString("token");
	}

	public String getAuthToken() {
		return document.getString("auth-token");
	}

	public String getDatabaseType() {
		return document.getString("database-type");
	}

	public DatabaseConfig getTargetDatabaseConfig() {
		return new DatabaseConfig(document.getDocument(getDatabaseType()));
	}

	public Database createDatabase() {
		Class<? extends Database> databaseClass = SimpleDatabaseTypeResolver.findDatabaseType(getDatabaseType());
		if (databaseClass == null) throw new IllegalArgumentException("No such database is known '" + getDatabaseType() + "'");
		try {
			Constructor<? extends Database> constructor = databaseClass.getDeclaredConstructor(DatabaseConfig.class);
			return constructor.newInstance(getTargetDatabaseConfig());
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
			throw new IllegalArgumentException("Database type '" + getDatabaseType() + "' does not support config instantiation");
		} catch (InvocationTargetException ex) {
			throw new WrappedException(ex);
		}
	}

}
