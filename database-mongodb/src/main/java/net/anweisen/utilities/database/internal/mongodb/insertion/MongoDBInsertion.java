package net.anweisen.utilities.database.internal.mongodb.insertion;

import net.anweisen.utilities.commons.misc.MongoUtils;
import net.anweisen.utilities.database.action.DatabaseInsertion;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.internal.mongodb.MongoDBDatabase;
import org.bson.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

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

	@Override
	public boolean equals(@Nonnull DatabaseInsertion other) {
		return equals((Object) other);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MongoDBInsertion that = (MongoDBInsertion) o;
		return database.equals(that.database) && collection.equals(that.collection) && values.equals(that.values);
	}

	@Override
	public int hashCode() {
		return Objects.hash(database, collection, values);
	}

}
