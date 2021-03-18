package net.anweisen.utilities.commons.config.document.readonly;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.PropertiesDocument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ReadOnlyPropertiesDocument extends PropertiesDocument {

	public ReadOnlyPropertiesDocument(@Nonnull Properties properties) {
		super(properties);
	}

	public ReadOnlyPropertiesDocument(@Nonnull File file) throws IOException {
		super(file);
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		return new ReadOnlyDocumentWrapper(super.getDocument(path));
	}

	@Nonnull
	@Override
	public Document set(@Nonnull String path, @Nullable Object value) {
		throw new UnsupportedOperationException("ReadOnlyPropertiesDocument.set(String, Object)");
	}

	@Nonnull
	@Override
	public Document clear() {
		throw new UnsupportedOperationException("ReadOnlyPropertiesDocument.clear()");
	}

	@Nonnull
	@Override
	public Document remove(@Nonnull String path) {
		throw new UnsupportedOperationException("ReadOnlyPropertiesDocument.remove(String)");
	}

	@Override
	public boolean isReadonly() {
		return true;
	}

}
