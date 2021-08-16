package net.anweisen.utilities.database;

import net.anweisen.utilities.common.concurrent.task.Task;
import net.anweisen.utilities.database.action.*;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.internal.abstraction.DefaultExecutedQuery;
import net.anweisen.utilities.database.internal.abstraction.DefaultSpecificDatabase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class EmptyDatabase implements Database {

	private final boolean silent;

	public EmptyDatabase(boolean silent) {
		this.silent = silent;
	}

	public boolean isSilent() {
		return silent;
	}

	protected void exception(@Nonnull String message) {
		throw new UnsupportedOperationException(message);
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public void connect() throws DatabaseException {
		if (!silent)
			exception("Cannot connect with a NOP Database");
	}

	@Override
	public boolean connectSafely() {
		return false;
	}

	@Override
	public void disconnect() throws DatabaseException {
		if (!silent)
			exception("Cannot disconnect from a NOP Database");
	}

	@Override
	public boolean disconnectSafely() {
		return false;
	}

	@Override
	public void createTable(@Nonnull String name, @Nonnull SQLColumn... columns) throws DatabaseException {
		if (!silent)
			exception("Cannot create tables from a NOP Database");
	}

	@Override
	public void createTableSafely(@Nonnull String name, @Nonnull SQLColumn... columns) {
	}

	@Nonnull
	@Override
	public Collection<String> listTables() throws DatabaseException {
		if (!silent)
			exception("Cannot list tables of a NOP Database");

		return Collections.emptyList();
	}

	@Nonnull
	@Override
	public DatabaseQuery query(@Nonnull String table) {
		if (!silent)
			exception("Cannot query in a NOP Database");

		return new EmptyDatabaseQuery();
	}

	@Nonnull
	@Override
	public DatabaseUpdate update(@Nonnull String table) {
		if (!silent)
			exception("Cannot update in a NOP Database");

		return new EmptyDatabaseAction();
	}

	@Nonnull
	@Override
	public DatabaseInsertion insert(@Nonnull String table) {
		if (!silent)
			exception("Cannot insert into a NOP Database");

		return new EmptyDatabaseAction();
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate insertOrUpdate(@Nonnull String table) {
		if (!silent)
			exception("Cannot inset or update into a NOP Database");

		return new EmptyDatabaseAction();
	}

	@Nonnull
	@Override
	public DatabaseDeletion delete(@Nonnull String table) {
		if (!silent)
			exception("Cannot delete from a NOP Database");

		return new EmptyDatabaseAction();
	}

	@Nonnull
	@Override
	public SpecificDatabase getSpecificDatabase(@Nonnull String name) {
		return new DefaultSpecificDatabase(this, name);
	}

	@Nonnull
	@Override
	public DatabaseConfig getConfig() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "EmptyDatabase[silent=" + silent + "]";
	}

	public static class EmptyDatabaseQuery implements DatabaseQuery {

		@Nonnull
		@Override
		public DatabaseQuery where(@Nonnull String field, @Nullable Object object) {
			return this;
		}

		@Nonnull
		@Override
		public DatabaseQuery where(@Nonnull String field, @Nullable Number value) {
			return this;
		}

		@Nonnull
		@Override
		public DatabaseQuery where(@Nonnull String field, @Nullable String value, boolean ignoreCase) {
			return this;
		}

		@Nonnull
		@Override
		public DatabaseQuery where(@Nonnull String field, @Nullable String value) {
			return this;
		}

		@Nonnull
		@Override
		public DatabaseQuery whereNot(@Nonnull String field, @Nullable Object value) {
			return this;
		}

		@Nonnull
		@Override
		public DatabaseQuery select(@Nonnull String... selection) {
			return this;
		}

		@Nonnull
		@Override
		public DatabaseQuery orderBy(@Nonnull String field, @Nonnull Order order) {
			return this;
		}

		@Nonnull
		@Override
		public ExecutedQuery execute() throws DatabaseException {
			return new DefaultExecutedQuery(Collections.emptyList());
		}

		@Nonnull
		@Override
		public Task<ExecutedQuery> executeAsync() {
			return Task.syncCall(this::execute);
		}

		@Override
		public boolean equals(@Nonnull ExecutedQuery other) {
			return equals((Object) other);
		}

	}

	public static class EmptyDatabaseAction implements DatabaseDeletion, DatabaseInsertion, DatabaseUpdate, DatabaseInsertionOrUpdate {

		@Nonnull
		@Override
		public EmptyDatabaseAction where(@Nonnull String field, @Nullable Object value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyDatabaseAction where(@Nonnull String field, @Nullable Number value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyDatabaseAction where(@Nonnull String field, @Nullable String value, boolean ignoreCase) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyDatabaseAction where(@Nonnull String field, @Nullable String value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyDatabaseAction whereNot(@Nonnull String field, @Nullable Object value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyDatabaseAction set(@Nonnull String field, @Nullable Object value) {
			return this;
		}

		@Override
		public Void execute() throws DatabaseException {
			return null;
		}

		@Nonnull
		@Override
		public Task<Void> executeAsync() {
			return Task.completedVoid();
		}

		@Override
		public boolean equals(@Nonnull DatabaseUpdate other) {
			return false;
		}

		@Override
		public boolean equals(@Nonnull DatabaseInsertionOrUpdate other) {
			return equals((Object) other);
		}

		@Override
		public boolean equals(@Nonnull DatabaseInsertion other) {
			return equals((Object) other);
		}

		@Override
		public boolean equals(@Nonnull DatabaseDeletion other) {
			return equals((Object) other);
		}
	}

}
