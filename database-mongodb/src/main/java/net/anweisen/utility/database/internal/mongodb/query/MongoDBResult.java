package net.anweisen.utility.database.internal.mongodb.query;

import net.anweisen.utility.document.bson.BsonDocument;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class MongoDBResult extends BsonDocument {

	public MongoDBResult(@Nonnull org.bson.Document bsonDocument) {
		super(bsonDocument, new AtomicBoolean(false));
	}

}
