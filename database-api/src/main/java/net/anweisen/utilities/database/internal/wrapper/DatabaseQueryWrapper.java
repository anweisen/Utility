package net.anweisen.utilities.database.internal.wrapper;

import net.anweisen.utilities.database.DatabaseQuery;
import net.anweisen.utilities.database.ExecutedQuery;
import net.anweisen.utilities.database.Order;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public abstract class DatabaseQueryWrapper implements DatabaseQuery {

	protected final DatabaseQuery query;

	public DatabaseQueryWrapper(@Nonnull DatabaseQuery query) {
		this.query = query;
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable Object object) {
		return query.where(field, object);
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable Number value) {
		return query.where(field, value);
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable String value, boolean ignoreCase) {
		return query.where(field, value, ignoreCase);
	}

	@Nonnull
	@Override
	public DatabaseQuery where(@Nonnull String field, @Nullable String value) {
		return query.where(field, value);
	}

	@Nonnull
	@Override
	public DatabaseQuery whereNot(@Nonnull String field, @Nullable Object value) {
		return query.whereNot(field, value);
	}

	@Nonnull
	@Override
	public DatabaseQuery select(@Nonnull String... selection) {
		return query.select(selection);
	}

	@Nonnull
	@Override
	public DatabaseQuery orderBy(@Nonnull String field, @Nonnull Order order) {
		return query.orderBy(field, order);
	}

	@Nonnull
	@Override
	public ExecutedQuery execute() throws DatabaseException {
		return query.execute();
	}

	@Override
	public boolean equals(@Nonnull ExecutedQuery other) {
		return query.equals(other);
	}

	@Override
	public boolean equals(Object obj) {
		return query.equals(obj);
	}

	@Override
	public int hashCode() {
		return query.hashCode();
	}

}
