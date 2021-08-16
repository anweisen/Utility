package net.anweisen.utilities.common.config.document.wrapper;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.config.FileDocument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class FileDocumentWrapper implements WrappedDocument, FileDocument {

	protected final Document document;
	protected final File file;

	public FileDocumentWrapper(@Nonnull File file, @Nonnull Document document) {
		this.file = file;
		this.document = document;
	}

	@Override
	public Document getWrappedDocument() {
		return document;
	}

	@Nonnull
	@Override
	public File getFile() {
		return file;
	}

	@Nonnull
	@Override
	public Path getPath() {
		return file.toPath();
	}

	@Nonnull
	@Override
	public FileDocument set(@Nonnull String path, @Nullable Object value) {
		return (FileDocument) WrappedDocument.super.set(path, value);
	}

	@Nonnull
	@Override
	public FileDocument clear() {
		return (FileDocument) WrappedDocument.super.clear();
	}

	@Nonnull
	@Override
	public FileDocument remove(@Nonnull String path) {
		return (FileDocument) WrappedDocument.super.remove(path);
	}

}
