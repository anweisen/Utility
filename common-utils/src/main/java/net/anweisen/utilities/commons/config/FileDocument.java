package net.anweisen.utilities.commons.config;

import net.anweisen.utilities.commons.config.document.wrapper.FileDocumentWrapper;
import net.anweisen.utilities.commons.logging.ILogger;

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

	void saveExceptionally() throws IOException;

	/**
	 * Executes {@link #saveExceptionally()} and prints all caught errors to {@link #LOGGER}
	 *
	 * @see #saveExceptionally()
	 */
	void save();

	/**
	 * {@link #save() saves} this in a new thread or submits it so a assigned {@link java.util.concurrent.ExecutorService}
	 *
	 * @see #save()
	 */
	void saveAsync();

	/**
	 * @param async if this should be saved asynchronously or synchronously
	 *
	 * @see #save()
	 * @see #saveAsync()
	 */
	default void save(boolean async) {
		if (async)  saveAsync();
		else        save();
	}

	@Nonnull
	@CheckReturnValue
	static FileDocument wrap(@Nonnull Document document, @Nonnull File file) {
		return new FileDocumentWrapper(file, document);
	}

	@Nonnull
	@CheckReturnValue
	static FileDocument create(@Nonnull Class<? extends Document> classOfDocument, @Nonnull File file) {
		return wrap(Document.create(classOfDocument, file), file);
	}

}
