package net.anweisen.utilities.database.internal.sql.abstraction;

import net.anweisen.utilities.commons.config.Json;
import net.anweisen.utilities.commons.config.document.GsonDocument;
import net.anweisen.utilities.commons.misc.GsonUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public final class SQLHelper {

	private SQLHelper() {}

	public static void fillParams(@Nonnull PreparedStatement statement, @Nonnull Object... params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			Object param = packObject(params[i]);
			statement.setObject(i + 1 /* in sql we count from 1 */, param);
		}
	}

	@Nullable
	private static Object packObject(@Nullable Object object) {
		if (object == null)                 return null;
		if (object instanceof Number)       return object;
		if (object instanceof Boolean)      return object;
		if (object instanceof Enum<?>)      return ((Enum<?>)object).name();
		if (object instanceof Json)         return ((Json)object).toJson();
		if (object instanceof Map)          return new GsonDocument((Map<String, Object>) object).toJson();
		if (object instanceof Iterable)     return GsonUtils.convertIterableToJsonArray(GsonDocument.GSON, (Iterable<?>) object).toString();
		if (object.getClass().isArray())    return GsonUtils.convertArrayToJsonArray(GsonDocument.GSON, object).toString();
		return object.toString();
	}

}
