package net.anweisen.utilities.common.config.document.wrapper;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.config.FileDocument;
import net.anweisen.utilities.common.config.Propertyable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

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

	@Nonnull
	@Override
	public FileDocument set(@Nonnull String path, @Nullable Object value) {
		return (FileDocument) super.set(path, value);
	}

	@Nonnull
	@Override
	public FileDocument clear() {
		return (FileDocument) super.clear();
	}

	@Nonnull
	@Override
	public FileDocument remove(@Nonnull String path) {
		return (FileDocument) super.remove(path);
	}

}
