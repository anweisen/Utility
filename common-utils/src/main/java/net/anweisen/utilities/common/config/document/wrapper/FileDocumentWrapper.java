package net.anweisen.utilities.common.config.document.wrapper;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.config.FileDocument;

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

	@Nonnull
	@Override
	public File getFile() {
		return file;
	}

}
