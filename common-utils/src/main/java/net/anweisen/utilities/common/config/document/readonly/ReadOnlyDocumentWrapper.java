package net.anweisen.utilities.common.config.document.readonly;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.config.document.wrapper.WrappedDocument;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ReadOnlyDocumentWrapper implements WrappedDocument<Document> {

	private final Document document;

	public ReadOnlyDocumentWrapper(@Nonnull Document document) {
		this.document = document;
	}

	@Override
	public Document getWrappedDocument() {
		return document;
	}

	@Override
	public boolean isReadonly() {
		return true;
	}

}
