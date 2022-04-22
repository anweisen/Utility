package net.anweisen.utility.database.internal.mongodb.query;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.anweisen.utility.database.Order;
import net.anweisen.utility.database.action.DatabaseQuery;
import net.anweisen.utility.database.action.result.ExecutedQuery;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.abstraction.DefaultExecutedQuery;
import net.anweisen.utility.database.internal.mongodb.MongoDBDatabase;
import net.anweisen.utility.database.internal.mongodb.MongoUtils;
import net.anweisen.utility.database.internal.mongodb.where.MongoDBWhere;
import net.anweisen.utility.database.internal.mongodb.where.ObjectWhere;
import net.anweisen.utility.database.internal.mongodb.where.StringIgnoreCaseWhere;
import org.bson.Document;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

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
	protected int limit;

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
	public String getTable() {
		return collection;
	}

	@Nonnull
	@Override
	public DatabaseQuery limit(int amount) {
		this.limit = amount;
		return this;
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable Object value) {
		where.put(field, new ObjectWhere(field, value, Filters::eq));
		return this;
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable Number value) {
		return where(field, (Object) value);
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable String value) {
		return where(field, (Object) value);
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable String value, boolean ignoreCase) {
		if (!ignoreCase) return where(field, value);
		if (value == null) throw new NullPointerException("Cannot use where ignore case with null value");
		where.put(field, new StringIgnoreCaseWhere(field, value));
		return this;
	}

	@Nonnull
	@Override
	public DatabaseQuery whereNot(@Nonnull String field, @Nullable Object value) {
		where.put(field, new ObjectWhere(field, value, Filters::ne));
		return this;
	}

	@Nonnull
	@Override
	public DatabaseQuery order(@Nonnull String column, @Nonnull Order order) {
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
			FindIterable<Document> iterable = database.getCollection(collection).find().limit(limit);
			MongoUtils.applyWhere(iterable, where);
			MongoUtils.applyOrder(iterable, orderBy, order);

			return createExecutedQuery(iterable.into(new ArrayList<>()));
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

	@Nonnull
	private ExecutedQuery createExecutedQuery(@Nonnull List<Document> documents) {
		return new DefaultExecutedQuery(documents.stream().map(MongoDBResult::new).collect(Collectors.toList()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MongoDBQuery that = (MongoDBQuery) o;
		return database.equals(that.database) && collection.equals(that.collection) && where.equals(that.where) && order == that.order && Objects.equals(orderBy, that.orderBy);
	}

	@Override
	public int hashCode() {
		return Objects.hash(database, collection, where, order, orderBy);
	}

}
