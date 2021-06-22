package net.anweisen.utilities.database.internal.sql.abstraction.query;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.config.document.EmptyDocument;
import net.anweisen.utilities.common.config.document.GsonDocument;
import net.anweisen.utilities.common.config.document.MapDocument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public final class SQLResult extends MapDocument {

	public SQLResult(@Nonnull Map<String, Object> values) {
		super(values);
	}

	@Nonnull
	@Override
	public Document getDocument0(@Nonnull String path, @Nonnull Document root, @Nullable Document parent) {
		try {
			return new GsonDocument(getString(path), this, null).readonly();
		} catch (Exception ex) {
			return new EmptyDocument(this, null);
		}
	}

	@Override
	public boolean isReadonly() {
		return true;
	}

}
