package net.anweisen.utility.database;

import net.anweisen.utility.common.concurrent.task.Task;
import net.anweisen.utility.database.action.*;
import net.anweisen.utility.database.action.result.ExecutedQuery;
import net.anweisen.utility.database.action.result.ExistingSqlColumn;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.abstraction.DefaultExecutedQuery;
import net.anweisen.utility.database.internal.abstraction.DefaultSpecificDatabase;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class EmptyDatabase implements Database {

	private final boolean silent;

	public EmptyDatabase(boolean silent) {
		this.silent = silent;
	}

	protected static void throwException(@Nonnull String message) {
		throw new UnsupportedOperationException(message);
	}

	public boolean isSilent() {
		return silent;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public void connect() throws DatabaseException {
		if (!silent)
			throwException("Cannot connect with a NOP Database");
	}

	@Override
	public boolean connectSafely() {
		return false;
	}

	@Override
	public void disconnect() throws DatabaseException {
		if (!silent)
			throwException("Cannot disconnect from a NOP Database");
	}

	@Override
	public boolean disconnectSafely() {
		return false;
	}

	@Override
	public void createTable(@Nonnull String name, boolean update, @Nonnull SqlColumn... columns) throws DatabaseException {
		if (!silent)
			throwException("Cannot create tables from a NOP Database");
	}

	@Override
	public void editTable(@Nonnull String name, @Nonnull SqlColumn... columns) throws DatabaseException {
		if (!silent)
			throwException("Cannot edit tables from a NOP Database");
	}

	@Nonnull
	@Override
	public DatabaseListTables listTables() {
		if (!silent)
			throwException("Cannot list tables of a NOP Database");

		return new EmptyListTables();
	}

	@Nonnull
	@Override
	public DatabaseListColumns listColumns(@Nonnull String table) {
		if (!silent)
			throwException("Cannot list columns of a NOP Database");

		return new EmptyListColumns();
	}

	@Nonnull
	@Override
	public DatabaseCountEntries countEntries(@Nonnull String table) {
		if (!silent)
			throwException("Cannot count entries of a NOP Database");

		return new EmptyCountEntries();
	}

	@Nonnull
	@Override
	public DatabaseQuery query(@Nonnull String table) {
		if (!silent)
			throwException("Cannot query in a NOP Database");

		return new EmptyDatabaseQuery();
	}

	@Nonnull
	@Override
	public DatabaseUpdate update(@Nonnull String table) {
		if (!silent)
			throwException("Cannot update in a NOP Database");

		return new EmptyVoidAction();
	}

	@Nonnull
	@Override
	public DatabaseInsertion insert(@Nonnull String table) {
		if (!silent)
			throwException("Cannot insert into a NOP Database");

		return new EmptyVoidAction();
	}

	@Nonnull
	@Override
	public DatabaseUpsert upsert(@Nonnull String table) {
		if (!silent)
			throwException("Cannot inset or update into a NOP Database");

		return new EmptyVoidAction();
	}

	@Nonnull
	@Override
	public DatabaseDeletion delete(@Nonnull String table) {
		if (!silent)
			throwException("Cannot delete from a NOP Database");

		return new EmptyVoidAction();
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
		public String getTable() {
			throw new UnsupportedOperationException();
		}

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
		public DatabaseQuery order(@Nonnull String field, @Nonnull Order order) {
			return this;
		}

		@Nonnull
		@Override
		public DatabaseQuery limit(int amount) {
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

	}

	public static class EmptyVoidAction implements DatabaseDeletion, DatabaseInsertion, DatabaseUpdate, DatabaseUpsert {

		@Nonnull
		@Override
		public String getTable() {
			throw new UnsupportedOperationException();
		}

		@Nonnull
		@Override
		public EmptyVoidAction where(@Nonnull String field, @Nullable Object value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyVoidAction where(@Nonnull String field, @Nullable Number value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyVoidAction where(@Nonnull String field, @Nullable String value, boolean ignoreCase) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyVoidAction where(@Nonnull String field, @Nullable String value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyVoidAction whereNot(@Nonnull String field, @Nullable Object value) {
			return this;
		}

		@Nonnull
		@Override
		public EmptyVoidAction set(@Nonnull String field, @Nullable Object value) {
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

	}

	public static class EmptyCountEntries implements DatabaseCountEntries {

		@Nonnull
		@Override
		public Long execute() throws DatabaseException {
			return 0L;
		}

		@Nonnull
		@Override
		public Task<Long> executeAsync() {
			return Task.completed(0L);
		}

		@Nonnull
		@Override
		public String getTable() {
			throw new UnsupportedOperationException();
		}
	}

	public static class EmptyListTables implements DatabaseListTables {

		@Nonnull
		@Override
		public List<String> execute() throws DatabaseException {
			return Collections.emptyList();
		}

		@Nonnull
		@Override
		public Task<List<String>> executeAsync() {
			return Task.completed(Collections.emptyList());
		}

	}

	public static class EmptyListColumns implements DatabaseListColumns {

		@Nonnull
		@Override
		public List<ExistingSqlColumn> execute() throws DatabaseException {
			return Collections.emptyList();
		}

		@Nonnull
		@Override
		public Task<List<ExistingSqlColumn>> executeAsync() {
			return Task.completed(Collections.emptyList());
		}

		@Nonnull
		@Override
		public String getTable() {
			throw new UnsupportedOperationException();
		}

	}

}
