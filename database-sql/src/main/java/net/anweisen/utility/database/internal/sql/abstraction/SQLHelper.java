package net.anweisen.utility.database.internal.sql.abstraction;

import net.anweisen.utility.common.misc.GsonUtils;
import net.anweisen.utility.document.JsonConvertable;
import net.anweisen.utility.document.gson.GsonDocument;
import net.anweisen.utility.document.gson.GsonHelper;

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

	private SQLHelper() {
	}

	public static void fillParams(@Nonnull PreparedStatement statement, @Nonnull Object... params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			Object param = serializeObject(params[i]);
			statement.setObject(i + 1 /* in sql we count from 1 */, param);
		}
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public static Object serializeObject(@Nullable Object object) {
		if (object == null) return null;
		if (object instanceof Number) return object;
		if (object instanceof Boolean) return object;
		if (object instanceof Enum<?>) return ((Enum<?>) object).name();
		if (object instanceof JsonConvertable) return ((JsonConvertable) object).toJson();
		if (object instanceof Map) return new GsonDocument((Map<String, Object>) object).toJson();
		if (object instanceof Iterable) return GsonUtils.convertIterableToJsonArray(GsonHelper.DEFAULT_GSON, (Iterable<?>) object).toString();
		if (object.getClass().isArray()) return GsonUtils.convertArrayToJsonArray(GsonHelper.DEFAULT_GSON, object).toString();
		return object.toString();
	}

}
