package net.anweisen.utilities.jda.commandmanager.utils;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.GsonDocument;
import net.anweisen.utilities.commons.config.document.wrapper.FileDocumentWrapper;
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

	private final FileDocumentWrapper document;

	public BotConfigLoader() throws IOException {
		this(new File("config.json"), DEFAULT_VALUES);
	}

	public BotConfigLoader(@Nonnull File file) throws IOException {
		this(file, DEFAULT_VALUES);
	}

	public BotConfigLoader(@Nonnull File file, @Nonnull Object[][] defaultValues) throws IOException {

		document = new FileDocumentWrapper(file, file.exists() ? new GsonDocument(file) : new GsonDocument());

		if (!file.exists()) {
			FileUtils.createFilesIfNecessary(file);
			for (Object[] pair : DEFAULT_VALUES) {
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

}
