package net.anweisen.utility.database.internal.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Sorts;
import net.anweisen.utility.common.misc.BukkitReflectionSerializationUtils;
import net.anweisen.utility.database.Order;
import net.anweisen.utility.database.internal.mongodb.where.MongoDBWhere;
import net.anweisen.utility.document.JsonConvertable;
import net.anweisen.utility.document.bson.BsonHelper;
import org.bson.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class MongoUtils {


	public static void applyWhere(@Nonnull FindIterable<Document> iterable, @Nonnull Map<String, MongoDBWhere> where) {
		for (Entry<String, MongoDBWhere> entry : where.entrySet()) {
			MongoDBWhere value = entry.getValue();
			iterable.filter(value.toBson());

			Collation collation = value.getCollation();
			if (collation != null)
				iterable.collation(collation);
		}
	}

	public static void applyOrder(@Nonnull FindIterable<Document> iterable, @Nullable String orderBy, @Nullable Order order) {
		if (order == null || orderBy == null) return;
		switch (order) {
			case HIGHEST -> iterable.sort(Sorts.descending(orderBy));
			case LOWEST -> iterable.sort(Sorts.ascending(orderBy));
		}
	}

	@Nullable
	public static Object packObject(@Nullable Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof JsonConvertable) {
			String json = ((JsonConvertable) value).toJson();
			return Document.parse(json);
		} else if (BukkitReflectionSerializationUtils.isSerializable(value.getClass())) {
			Map<String, Object> values = BukkitReflectionSerializationUtils.serializeObject(value);
			if (values == null) return null;
			return BsonHelper.toBsonDocument(values);
		} else if (value instanceof UUID) {
			return value.toString();
		} else {
			return value;
		}
	}

	@Nullable
	public static Object unpackBsonElement(@Nullable Object value) {
		if (value == null || value instanceof BsonNull) {
			return null;
		} else if (value instanceof BsonString) {
			return ((BsonString) value).getValue();
		} else if (value instanceof BsonDouble) {
			return ((BsonDouble) value).getValue();
		} else if (value instanceof BsonInt32) {
			return ((BsonInt32) value).getValue();
		} else if (value instanceof BsonInt64) {
			return ((BsonInt64) value).getValue();
		} else if (value instanceof BsonBinary) {
			return ((BsonBinary) value).getData();
		} else if (value instanceof BsonObjectId) {
			return ((BsonObjectId) value).getValue();
		} else if (value instanceof BsonArray) {
			return convertBsonArrayToList((BsonArray) value);
		} else if (value instanceof BsonDocument) {
			return convertBsonDocumentToMap((BsonDocument) value);
		} else if (value instanceof Document) {
			return value;
		} else {
			return value;
		}
	}

	@Nonnull
	public static List<Object> convertBsonArrayToList(@Nonnull BsonArray array) {
		List<Object> list = new ArrayList<>(array.size());
		for (BsonValue value : array) {
			list.add(unpackBsonElement(value));
		}
		return list;
	}

	@Nonnull
	public static Map<String, Object> convertBsonDocumentToMap(@Nonnull BsonDocument document) {
		Map<String, Object> map = new HashMap<>();
		for (Entry<String, BsonValue> entry : document.entrySet()) {
			map.put(entry.getKey(), unpackBsonElement(entry.getValue()));
		}
		return map;
	}

	private MongoUtils() {
	}
}
