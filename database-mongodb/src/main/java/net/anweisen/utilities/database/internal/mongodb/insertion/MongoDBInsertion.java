package net.anweisen.utilities.database.internal.mongodb.insertion;

import net.anweisen.utilities.commons.misc.MongoUtils;
import net.anweisen.utilities.database.DatabaseInsertion;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.internal.mongodb.MongoDBDatabase;
import org.bson.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MongoDBInsertion implements DatabaseInsertion {

	protected final MongoDBDatabase database;
	protected final String collection;
	protected final Document values;

	public MongoDBInsertion(@Nonnull MongoDBDatabase database, @Nonnull String collection) {
		this.database = database;
		this.collection = collection;
		this.values = new Document();
	}

	public MongoDBInsertion(@Nonnull MongoDBDatabase database, @Nonnull String collection, @Nonnull Document values) {
		this.database = database;
		this.collection = collection;
		this.values = values;
	}

	@Nonnull
	@Override
	public DatabaseInsertion set(@Nonnull String field, @Nullable Object value) {
		values.put(field, MongoUtils.packObject(value));
		return this;
	}

	@Override
	public void execute() throws DatabaseException {
		try {
			database.getCollection(collection).insertOne(values);
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

}
