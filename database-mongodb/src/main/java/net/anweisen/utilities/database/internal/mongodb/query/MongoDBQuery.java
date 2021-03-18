package net.anweisen.utilities.database.internal.mongodb.query;

import com.mongodb.client.FindIterable;
import net.anweisen.utilities.commons.misc.MongoUtils;
import net.anweisen.utilities.database.DatabaseQuery;
import net.anweisen.utilities.database.ExecutedQuery;
import net.anweisen.utilities.database.Order;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.internal.mongodb.MongoDBDatabase;
import net.anweisen.utilities.database.internal.mongodb.where.MongoDBWhere;
import net.anweisen.utilities.database.internal.mongodb.where.ObjectWhere;
import net.anweisen.utilities.database.internal.mongodb.where.StringIgnoreCaseWhere;
import org.bson.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MongoDBQuery implements DatabaseQuery {

	protected final MongoDBDatabase database;
	protected final String collection;
	protected final Map<String, MongoDBWhere> where;
	protected Order order;
	protected String orderBy;

	public MongoDBQuery(@Nonnull MongoDBDatabase database, @Nonnull String collection) {
		this.database = database;
		this.collection = collection;
		this.where = new HashMap<>();
	}

	public MongoDBQuery(@Nonnull MongoDBDatabase database, @Nonnull String collection, @Nonnull Map<String, MongoDBWhere> where) {
		this.database = database;
		this.collection = collection;
		this.where = where;
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String column, @Nullable Object object) {
		where.put(column, new ObjectWhere(column, object));
		return this;
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String column, @Nullable Number value) {
		return where(column, (Object) value);
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String column, @Nullable String value) {
		return where(column, (Object) value);
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String column, @Nullable String value, boolean ignoreCase) {
		if (!ignoreCase) return where(column, value);
		if (value == null) throw new NullPointerException("Cannot use where ignore case with null value");
		where.put(column, new StringIgnoreCaseWhere(column, value));
		return this;
	}

	@Nonnull
	@Override
	public DatabaseQuery orderBy(@Nonnull String column, @Nonnull Order order) {
		this.orderBy = column;
		this.order = order;
		return this;
	}

	@Nonnull
	@Override
	public DatabaseQuery select(@Nonnull String... selection) {
		return this;
	}

	@Nonnull
	@Override
	public ExecutedQuery execute() throws DatabaseException {
		try {
			FindIterable<Document> iterable = database.getCollection(collection).find();
			MongoUtils.applyWhere(iterable, where);
			MongoUtils.applyOrder(iterable, orderBy, order);

			List<Document> documents = iterable.into(new ArrayList<>());
			return new ExecutedMongoDBQuery(documents);
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

}
