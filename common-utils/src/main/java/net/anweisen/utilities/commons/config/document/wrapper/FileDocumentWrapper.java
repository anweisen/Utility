package net.anweisen.utilities.commons.config.document.wrapper;

import net.anweisen.utilities.commons.config.Document;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class FileDocumentWrapper extends DocumentWrapper {

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
