package net.anweisen.utilities.jda.commandmanager.utils;

import net.anweisen.utilities.commons.config.Document;
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
public final class BotConfigLoader {

	public static final Object[][] DEFAULT_VALUES = {
			{ "token", "secret" },
			{ "database.host", "127.0.0.1" },
			{ "database.password", "secret" },
			{ "database.user", "root" },
			{ "database.database", "discordbot" },
	};

	private final Document document;

	public BotConfigLoader() throws IOException {
		this(new File("config.json"));
	}

	public BotConfigLoader(@Nonnull File file) throws IOException {

		document = file.exists() ? new GsonDocument(file) : new GsonDocument();

		if (!file.exists()) {
			FileUtils.createFilesIfNecessary(file);
			for (Object[] pair : DEFAULT_VALUES) {
				String key = (String) pair[0];
				String value = (String) pair[1];
				document.set(key, value);
			}
			document.save(file);
			throw new FileNotFoundException("Config file " + file.getName() + " was not found, created a new one");
		}

	}

	@Nonnull
	public Document getDocument() {
		return document;
	}

}
