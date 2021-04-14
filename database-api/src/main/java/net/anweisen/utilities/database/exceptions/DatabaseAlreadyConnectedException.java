package net.anweisen.utilities.database.exceptions;

/**
 * This exception in thrown, when a database tries to connect but is already connected.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class DatabaseAlreadyConnectedException extends DatabaseException {

	public DatabaseAlreadyConnectedException() {
		super("Database already connected");
	}

}
