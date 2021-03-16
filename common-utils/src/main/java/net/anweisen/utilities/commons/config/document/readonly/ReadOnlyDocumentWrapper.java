package net.anweisen.utilities.commons.config.document.readonly;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.wrapper.DocumentWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ReadOnlyDocumentWrapper extends DocumentWrapper {

	public ReadOnlyDocumentWrapper(@Nonnull Document document) {
		super(document);
	}

	@Override
	public boolean isReadonly() {
		return true;
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		return new ReadOnlyDocumentWrapper(document.getDocument(path));
	}

	@Nonnull
	@Override
	@Deprecated
	public Document set(@Nonnull String path, @Nullable Object value) {
		throw new UnsupportedOperationException("ReadonlyDocumentWrapper.set(String, Object)");
	}

	@Nonnull
	@Override
	@Deprecated
	public Document clear() {
		throw new UnsupportedOperationException("ReadonlyDocumentWrapper.clear()");
	}

	@Nonnull
	@Override
	@Deprecated
	public Document remove(@Nonnull String path) {
		throw new UnsupportedOperationException("ReadonlyDocumentWrapper.remove(String)");
	}

}
