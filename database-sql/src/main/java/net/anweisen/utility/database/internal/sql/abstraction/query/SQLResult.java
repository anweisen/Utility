package net.anweisen.utility.database.internal.sql.abstraction.query;

import net.anweisen.utility.document.map.MapDocument;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public final class SQLResult extends MapDocument {

	@SuppressWarnings("unchecked")
	public SQLResult(@Nonnull Map<String, Object> values) {
		super((Map<Object, Object>) (Map<?, ?>) values, new AtomicBoolean(false));
	}


}
