package net.anweisen.utilities.database;

import net.anweisen.utilities.common.concurrent.task.Task;
import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.database.action.*;
import net.anweisen.utilities.database.exceptions.DatabaseAlreadyConnectedException;
import net.anweisen.utilities.database.exceptions.DatabaseConnectionClosedException;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collection;

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
	 * @throws DatabaseException
	 *         If the connection could not be established
	 * @throws DatabaseAlreadyConnectedException
	 *         If this database is already {@link #isConnected() connected}
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
	 * @throws DatabaseException
	 *         If something went wrong while closing the connection to the database
	 * @throws DatabaseConnectionClosedException
	 *         If this database is not {@link #isConnected() connected}
	 */
	void disconnect() throws DatabaseException;

	/**
	 * Closes the connection to the database synchronously.
	 * No exceptions will be thrown if the process fails.
	 *
	 * @return {@code true} if the connection was closed without errors
	 */
	boolean disconnectSafely();

	void createTable(@Nonnull String name, @Nonnull SQLColumn... columns) throws DatabaseException;
	void createTableSafely(@Nonnull String name, @Nonnull SQLColumn... columns);

	@Nonnull
	default Task<Void> createTableAsync(@Nonnull String name, @Nonnull SQLColumn... columns) {
		return Task.asyncRunExceptionally(() -> createTable(name, columns));
	}

	@Nonnull
	Collection<String> listTables() throws DatabaseException;

	@Nonnull
	default Task<Collection<String>> listTablesAsync() {
		return Task.asyncCall(this::listTables);
	}

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
