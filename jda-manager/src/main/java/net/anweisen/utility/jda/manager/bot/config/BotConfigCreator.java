package net.anweisen.utility.jda.manager.bot.config;

import net.anweisen.utility.common.misc.FileUtils;
import net.anweisen.utility.document.Documents;
import net.anweisen.utility.document.wrapped.StorableDocument;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class BotConfigCreator implements ConfigProvider {

	public static final Object[][] OTHER_VALUES = {
			{ "default-language",               "en" },
			{ "auth-token",                     "unknown" },
			{ "language-files",                 new String[0] },
	};
	public static final Object[][] GENERAL_VALUES = {
			{ "token",                          "secret" },
			{ "shards",                         1 },
			{ "default-prefix",                 "!" },
			{ "online-status",                  "ONLINE" },
	};
	public static final Object[][] DATABASE_VALUES = {
			{ "database.type",                  "sqlite" },

			{ "database.mongodb.host",          "127.0.0.1" },
			{ "database.mongodb.port",          27017 },
			{ "database.mongodb.database",      "discordbot" },
			{ "database.mongodb.auth-database", "admin" },
			{ "database.mongodb.user",          "root" },
			{ "database.mongodb.password",      "secret" },

			{ "database.mysql.host",            "127.0.0.1" },
			{ "database.mysql.port",            3306 },
			{ "database.mysql.database",        "discordbot" },
			{ "database.mysql.user",            "root" },
			{ "database.mysql.password",        "secret" },

			{ "database.sqlite.file",           "database.db" },
	};
	public static final Object[][][] ALL_VALUES = { OTHER_VALUES, GENERAL_VALUES, DATABASE_VALUES };

	private final StorableDocument document;

	public BotConfigCreator() throws IOException {
		this(ALL_VALUES);
	}

	public BotConfigCreator(Object[][]... defaultValues) throws IOException {
		this(Paths.get("config.json"), defaultValues);
	}

	public BotConfigCreator(@Nonnull Path path) throws IOException {
		this(path, ALL_VALUES);
	}

	public BotConfigCreator(@Nonnull Path file, @Nonnull Object[][]... defaultValues) throws IOException {

		document = Documents.newStorableJsonDocument(file);

		if (!Files.exists(file)) {
			FileUtils.createFile(file);
			for (Object[][] values : defaultValues) {
				for (Object[] pair : values) {
					String key = (String) pair[0];
					Object value = pair[1];
					document.set(key, value);
				}
			}
			document.save();
			throw new FileNotFoundException("Config file " + file.getFileName() + " was not found, created a new one");
		}

	}

	@Nonnull
	public StorableDocument getDocument() {
		return document;
	}

	@Override
	public String toString() {
		return "BotConfig{" +
				"shards=" + getShards() +
				", languageFiles=" + getLanguageFiles() +
				", defaultPrefix='" + getDefaultPrefix() + "'" +
				", defaultLanguage='" + getDefaultLanguage() + "'" +
				", databaseType='" + getDatabaseType() + "'" +
				"}";
	}

}
