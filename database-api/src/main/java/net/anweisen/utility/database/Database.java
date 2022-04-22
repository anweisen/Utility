package net.anweisen.utility.database;

import net.anweisen.utility.common.concurrent.task.Task;
import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.database.action.*;
import net.anweisen.utility.database.exception.DatabaseAlreadyConnectedException;
import net.anweisen.utility.database.exception.DatabaseConnectionClosedException;
import net.anweisen.utility.database.exception.DatabaseException;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Database {

	ILogger LOGGER = ILogger.forThisClass();

	@Nonnull
	@CheckReturnValue
	static Database empty() {
		return new EmptyDatabase(true);
	}

	@Nonnull
	@CheckReturnValue
	static Database unsupported() {
		return new EmptyDatabase(false);
	}

	boolean isConnected();

	/**
	 * Creates the connection to the database synchronously.
	 *
	 * @throws DatabaseException                 If the connection could not be established
	 * @throws DatabaseAlreadyConnectedException If this database is already {@link #isConnected() connected}
	 */
	void connect() throws DatabaseException;

	/**
	 * Creates the connection to the database synchronously.
	 * No exceptions will be thrown if the process fails.
	 *
	 * @return {@code true} if the connection was established successfully
	 */
	boolean connectSafely();

	/**
	 * Closes the connection to the database synchronously.
	 *
	 * @throws DatabaseException                 If something went wrong while closing the connection to the database
	 * @throws DatabaseConnectionClosedException If this database is not {@link #isConnected() connected}
	 */
	void disconnect() throws DatabaseException;

	/**
	 * Closes the connection to the database synchronously.
	 * No exceptions will be thrown if the process fails.
	 *
	 * @return {@code true} if the connection was closed without errors
	 */
	boolean disconnectSafely();

	default void createTable(@Nonnull String name, @Nonnull SqlColumn... columns) throws DatabaseException {
		createTable(name, false, columns);
	}

	void createTable(@Nonnull String name, boolean update, @Nonnull SqlColumn... columns) throws DatabaseException;

	void editTable(@Nonnull String name, @Nonnull SqlColumn... columns) throws DatabaseException;

	@Nonnull
	default Task<Void> createTableAsync(@Nonnull String name, @Nonnull SqlColumn... columns) {
		return Task.asyncRunExceptionally(() -> createTable(name, columns));
	}

	@Nonnull
	default Task<Void> createTableAsync(@Nonnull String name, boolean replace, @Nonnull SqlColumn... columns) {
		return Task.asyncRunExceptionally(() -> createTable(name, replace, columns));
	}

	@Nonnull
	@CheckReturnValue
	DatabaseListTables listTables();

	@Nonnull
	@CheckReturnValue
	DatabaseListColumns listColumns(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	DatabaseCountEntries countEntries(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	DatabaseQuery query(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate update(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	DatabaseInsertion insert(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate insertOrUpdate(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	DatabaseDeletion delete(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	SpecificDatabase getSpecificDatabase(@Nonnull String name);

	@Nonnull
	DatabaseConfig getConfig();

}
