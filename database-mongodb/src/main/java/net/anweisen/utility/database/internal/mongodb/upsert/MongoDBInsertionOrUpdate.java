package net.anweisen.utility.database.internal.mongodb.upsert;

import net.anweisen.utility.database.action.DatabaseUpsert;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.mongodb.MongoDBDatabase;
import net.anweisen.utility.database.internal.mongodb.update.MongoDBUpdate;
import net.anweisen.utility.database.internal.mongodb.where.MongoDBWhere;
import net.anweisen.utility.document.bson.BsonHelper;
import org.bson.BsonDocument;
import org.bson.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map.Entry;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MongoDBInsertionOrUpdate extends MongoDBUpdate implements DatabaseUpsert {

	public MongoDBInsertionOrUpdate(@Nonnull MongoDBDatabase database, @Nonnull String collection) {
		super(database, collection);
	}

	@Nonnull
	@Override
	public DatabaseUpsert where(@Nonnull String field, @Nullable String value, boolean ignoreCase) {
		super.where(field, value, ignoreCase);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseUpsert where(@Nonnull String field, @Nullable Object value) {
		super.where(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseUpsert where(@Nonnull String field, @Nullable String value) {
		super.where(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseUpsert where(@Nonnull String field, @Nullable Number value) {
		super.where(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseUpsert whereNot(@Nonnull String field, @Nullable Object value) {
		super.whereNot(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseUpsert set(@Nonnull String field, @Nullable Object value) {
		super.set(field, value);
		return this;
	}

	@Override
	public Void execute() throws DatabaseException {
		if (database.query(collection, where).execute().isSet()) {
			return super.execute();
		} else {
			Document document = new Document(values);
			for (Entry<String, MongoDBWhere> entry : where.entrySet()) {
				BsonDocument bson = BsonHelper.toBsonDocument(entry.getValue().toBson());
				document.putAll(bson);
			}

			database.insert(collection, document).execute();
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

}
