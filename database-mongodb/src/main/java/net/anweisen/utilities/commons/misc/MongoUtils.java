package net.anweisen.utilities.commons.misc;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Sorts;
import net.anweisen.utilities.commons.config.Json;
import net.anweisen.utilities.commons.config.document.gson.SerializableTypeAdapter;
import net.anweisen.utilities.database.Order;
import net.anweisen.utilities.database.internal.mongodb.where.MongoDBWhere;
import org.bson.BsonDocument;
import org.bson.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class MongoUtils {

	private MongoUtils() {
	}

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
			case HIGHEST:
				iterable.sort(Sorts.descending(orderBy));
				break;
			case LOWEST:
				iterable.sort(Sorts.ascending(orderBy));
				break;
		}
	}

	@Nullable
	public static Object packObject(@Nullable Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Json) {
			String json = ((Json) value).toJson();
			return Document.parse(json);
		} else if (SerializationUtils.isSerializable(value.getClass())) {
			Map<String, Object> values = SerializationUtils.serializeObject(value);
			if (values == null) return null;
			BsonDocument bson = new BsonDocument();
			BsonUtils.setDocumentProperties(bson, values);
			return bson;
		} else if (value instanceof UUID) {
			return ((UUID) value).toString();
		} else {
			return value;
		}
	}

}
