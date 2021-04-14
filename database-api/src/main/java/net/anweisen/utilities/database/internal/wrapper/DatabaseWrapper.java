package net.anweisen.utilities.database.internal.wrapper;

import net.anweisen.utilities.database.*;
import net.anweisen.utilities.database.DatabaseConfig;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public abstract class DatabaseWrapper implements Database {

	protected final Database database;

	public DatabaseWrapper(@Nonnull Database database) {
		this.database = database;
	}

	@Override
	public boolean isConnected() {
		return database.isConnected();
	}

	@Override
	public void connect() throws DatabaseException {
		database.connect();
	}

	@Override
	public boolean connectSafely() {
		return database.connectSafely();
	}

	@Override
	public void disconnect() throws DatabaseException {
		database.disconnect();
	}

	@Override
	public boolean disconnectSafely() {
		return database.disconnectSafely();
	}

	@Override
	public void createTableIfNotExists(@Nonnull String name, @Nonnull SQLColumn... columns) throws DatabaseException {
		database.createTableIfNotExists(name, columns);
	}

	@Override
	public void createTableIfNotExistsSafely(@Nonnull String name, @Nonnull SQLColumn... columns) {
		database.createTableIfNotExistsSafely(name, columns);
	}

	@Nonnull
	@Override
	public DatabaseQuery query(@Nonnull String table) {
		return database.query(table);
	}

	@Nonnull
	@Override
	public DatabaseUpdate update(@Nonnull String table) {
		return database.update(table);
	}

	@Nonnull
	@Override
	public DatabaseInsertion insert(@Nonnull String table) {
		return database.insert(table);
	}

	@Nonnull
	@Override
	public DatabaseInsertion insert(@Nonnull String table, @Nonnull Map<String, Object> values) {
		return database.insert(table, values);
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate insertOrUpdate(@Nonnull String table) {
		return database.insertOrUpdate(table);
	}

	@Nonnull
	@Override
	public DatabaseDeletion delete(@Nonnull String table) {
		return database.delete(table);
	}

	@Nonnull
	@Override
	public DatabaseConfig getConfig() {
		return database.getConfig();
	}

	@Override
	public boolean equals(Object obj) {
		return database.equals(obj);
	}

	@Override
	public int hashCode() {
		return database.hashCode();
	}

}
