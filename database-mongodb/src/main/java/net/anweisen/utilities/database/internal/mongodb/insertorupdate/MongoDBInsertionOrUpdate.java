package net.anweisen.utilities.database.internal.mongodb.insertorupdate;

import net.anweisen.utilities.common.misc.BsonUtils;
import net.anweisen.utilities.database.action.DatabaseInsertion;
import net.anweisen.utilities.database.action.DatabaseInsertionOrUpdate;
import net.anweisen.utilities.database.action.DatabaseUpdate;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.internal.mongodb.MongoDBDatabase;
import net.anweisen.utilities.database.internal.mongodb.update.MongoDBUpdate;
import net.anweisen.utilities.database.internal.mongodb.where.MongoDBWhere;
import org.bson.BsonDocument;
import org.bson.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map.Entry;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MongoDBInsertionOrUpdate extends MongoDBUpdate implements DatabaseInsertionOrUpdate {

	public MongoDBInsertionOrUpdate(@Nonnull MongoDBDatabase database, @Nonnull String collection) {
		super(database, collection);
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable String value, boolean ignoreCase) {
		super.where(field, value, ignoreCase);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable Object value) {
		super.where(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable String value) {
		super.where(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable Number value) {
		super.where(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate whereNot(@Nonnull String field, @Nullable Object value) {
		super.whereNot(field, value);
		return this;
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate set(@Nonnull String field, @Nullable Object value) {
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
				BsonDocument bson = BsonUtils.convertBsonToBsonDocument(entry.getValue().toBson());
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
