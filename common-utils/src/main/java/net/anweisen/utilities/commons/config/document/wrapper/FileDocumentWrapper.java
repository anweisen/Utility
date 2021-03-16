package net.anweisen.utilities.commons.config.document.wrapper;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.GsonDocument;
import net.anweisen.utilities.commons.config.document.PropertiesDocument;
import net.anweisen.utilities.commons.misc.FileUtils;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class FileDocumentWrapper extends DocumentWrapper {

	@Nonnull
	@CheckReturnValue
	public static FileDocumentWrapper createGson(@Nonnull File file) throws IOException {
		return new FileDocumentWrapper(file, new GsonDocument(file));
	}

	@Nonnull
	@CheckReturnValue
	public static FileDocumentWrapper createProperties(@Nonnull File file) throws IOException {
		return new FileDocumentWrapper(file, new PropertiesDocument(file));
	}

	@Nonnull
	@CheckReturnValue
	public static FileDocumentWrapper create(@Nonnull File file) throws IOException {
		String extension = FileUtils.getFileExtension(file);
		switch (extension) {
			case "json":
				return createGson(file);
			case "properties":
				return createProperties(file);
			default:
				throw new IllegalArgumentException("Cannot create config for file type '" + extension + "'");
		}
	}

	protected final File file;

	public FileDocumentWrapper(@Nonnull File file, @Nonnull Document document) {
		super(document);
		this.file = file;
	}

	public void save(boolean async) {
		if (async)  saveAsync();
		else        save();
	}

	public void save() {
		try {
			document.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void saveAsync() {
		Thread thread = new Thread(this::save);
		thread.setName("DocumentWriter");
		thread.start();
	}

}
