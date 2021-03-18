package net.anweisen.utilities.database.exceptions;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DatabaseConnectionClosedException extends DatabaseException {

	public DatabaseConnectionClosedException() {
		super("Database connection closed");
	}

}
