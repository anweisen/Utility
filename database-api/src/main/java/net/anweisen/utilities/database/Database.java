package net.anweisen.utilities.database;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.database.action.*;
import net.anweisen.utilities.database.exceptions.*;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Database {

	ILogger LOGGER = ILogger.forThisClass();

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

	void createTableIfNotExists(@Nonnull String name, @Nonnull SQLColumn... columns) throws DatabaseException;
	void createTableIfNotExistsSafely(@Nonnull String name, @Nonnull SQLColumn... columns);

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
	DatabaseInsertion insert(@Nonnull String table, @Nonnull Map<String, Object> values);

	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate insertOrUpdate(@Nonnull String table);

	@Nonnull
	@CheckReturnValue
	DatabaseDeletion delete(@Nonnull String table);

	@Nonnull
	DatabaseConfig getConfig();

}
