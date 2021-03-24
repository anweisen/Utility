package net.anweisen.utilities.database.internal.mongodb.query;

import net.anweisen.utilities.commons.config.document.BsonDocument;
import net.anweisen.utilities.database.Result;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class MongoDBResult extends BsonDocument implements Result {

	public MongoDBResult(@Nonnull org.bson.Document bsonDocument) {
		super(bsonDocument);
	}

	@Override
	public boolean isReadonly() {
		return true;
	}

}
