package net.anweisen.utilities.jda.commandmanager.bot.config;

import net.anweisen.utilities.commons.config.FileDocument;
import net.anweisen.utilities.commons.config.document.GsonDocument;
import net.anweisen.utilities.commons.misc.FileUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class BotConfigCreator implements ConfigProvider {

	public static final Object[][] DEFAULT_VALUES = {

			{ "token",                          "secret" },
			{ "shards",                         1 },
			{ "auth-token",                     "unknown" },
			{ "default-language",               "en" },
			{ "default-prefix",                 "!" },
			{ "online-status",                  "ONLINE" },
			{ "language-files",                 new String[0] },
			{ "database.type",                  "sqlite" },

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

	private final FileDocument document;

	public BotConfigCreator() throws IOException {
		this(new File("config.json"), DEFAULT_VALUES);
	}

	public BotConfigCreator(@Nonnull File file) throws IOException {
		this(file, DEFAULT_VALUES);
	}

	public BotConfigCreator(@Nonnull File file, @Nonnull Object[][] defaultValues) throws IOException {

		document = FileDocument.read(GsonDocument.class, file);

		if (!file.exists()) {
			FileUtils.createFilesIfNecessary(file);
			for (Object[] pair : defaultValues) {
				String key = (String) pair[0];
				Object value = pair[1];
				document.set(key, value);
			}
			document.save();
			throw new FileNotFoundException("Config file " + file.getName() + " was not found, created a new one");
		}

	}

	@Nonnull
	public FileDocument getDocument() {
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
