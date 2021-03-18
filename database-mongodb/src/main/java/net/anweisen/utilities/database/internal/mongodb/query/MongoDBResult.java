package net.anweisen.utilities.database.internal.mongodb.query;

import net.anweisen.utilities.commons.config.document.readonly.ReadOnlyBsonDocument;
import net.anweisen.utilities.database.Result;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class MongoDBResult extends ReadOnlyBsonDocument implements Result {

	public MongoDBResult(@Nonnull org.bson.Document bsonDocument) {
		super(bsonDocument);
	}

	@Nonnull
	@Override
	public Result set(@Nonnull String path, @Nullable Object value) {
		return (Result) super.set(path, value);
	}

	@Nonnull
	@Override
	public Result remove(@Nonnull String path) {
		return (Result) super.remove(path);
	}

	@Nonnull
	@Override
	public Result clear() {
		return (Result) super.clear();
	}

}
