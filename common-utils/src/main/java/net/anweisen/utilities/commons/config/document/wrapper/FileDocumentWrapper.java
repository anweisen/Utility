package net.anweisen.utilities.commons.config.document.wrapper;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.FileDocument;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class FileDocumentWrapper extends DocumentWrapper implements FileDocument {

	protected final File file;

	public FileDocumentWrapper(@Nonnull File file, @Nonnull Document document) {
		super(document);
		this.file = file;
	}

	@Override
	public void save() {
		try {
			saveExceptionally();
		} catch (IOException ex) {
			LOGGER.error("Could not save config to file \"{}\"", file, ex);
		}
	}

	@Override
	public void saveExceptionally() throws IOException {
		document.saveToFile(file);
	}

	public void saveAsync() {
		Thread thread = new Thread(this::save);
		thread.setName("DocumentWriter");
		thread.start();
	}

}
