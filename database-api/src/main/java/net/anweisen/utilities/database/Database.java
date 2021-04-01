package net.anweisen.utilities.database;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.database.exceptions.DatabaseException;

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

	void connect() throws DatabaseException;
	boolean connectSafely();

	void disconnect() throws DatabaseException;
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
