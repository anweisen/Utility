package net.anweisen.utilities.database.exceptions;

/**
 * This exception is thrown, when a database operation is tried which requires a active connection, but the database is not connected.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DatabaseConnectionClosedException extends DatabaseException {

	public DatabaseConnectionClosedException() {
		super("Database connection closed");
	}

}
