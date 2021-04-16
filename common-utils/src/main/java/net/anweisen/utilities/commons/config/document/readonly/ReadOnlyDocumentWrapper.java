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

}
