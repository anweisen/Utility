package net.anweisen.utilities.common.config;

import net.anweisen.utilities.common.concurrent.task.Task;
import net.anweisen.utilities.common.config.document.GsonDocument;
import net.anweisen.utilities.common.config.document.PropertiesDocument;
import net.anweisen.utilities.common.config.document.wrapper.FileDocumentWrapper;
import net.anweisen.utilities.common.logging.ILogger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
public interface FileDocument extends Document {

	/**
	 * Logger used to log errors which are caught in the safe version of an exceptionally method
	 */
	ILogger LOGGER = ILogger.forThisClass();

	default void saveExceptionally() throws IOException {
		saveToFile(getFile());
	}

	/**
	 * Executes {@link #saveExceptionally()} and prints all caught errors to {@link #LOGGER}
	 *
	 * @see #saveExceptionally()
	 */
	default void save() {
		try {
			saveExceptionally();
		} catch (IOException ex) {
			LOGGER.error("Could not save config to file \"{}\"", getFile(), ex);
		}
	}

	@Nonnull
	default Task<Void> saveAsync() {
		return Task.asyncRun(this::save);
	}

	/**
	 * @param async whether this should be saved asynchronously or synchronously
	 *
	 * @see #save()
	 * @see #saveAsync()
	 */
	default void save(boolean async) {
		if (async)  saveAsync();
		else        save();
	}

	@Nonnull
	File getFile();

	@Nonnull
	@CheckReturnValue
	static FileDocument wrap(@Nonnull Document document, @Nonnull File file) {
		return new FileDocumentWrapper(file, document);
	}

	@Nonnull
	@CheckReturnValue
	static FileDocument readFile(@Nonnull Class<? extends Document> classOfDocument, @Nonnull File file) {
		return Document.readFile(classOfDocument, file).asFileDocument(file);
	}

	@Nonnull
	@CheckReturnValue
	static FileDocument readJsonFile(@Nonnull File file) {
		return readFile(GsonDocument.class, file);
	}

	@Nonnull
	@CheckReturnValue
	static FileDocument readPropertiesFile(@Nonnull File file) {
		return readFile(PropertiesDocument.class, file);
	}

}
