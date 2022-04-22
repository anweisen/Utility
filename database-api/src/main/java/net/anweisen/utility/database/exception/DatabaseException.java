package net.anweisen.utility.database.exception;

import net.anweisen.utility.database.action.DatabaseAction;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @see DatabaseAlreadyConnectedException
 * @see DatabaseConnectionClosedException
 * @see DatabaseUnsupportedFeatureException
 * @see DatabaseAction#execute()
 * @since 1.0
 */
public class DatabaseException extends Exception {

	protected DatabaseException() {
		super();
	}

	public DatabaseException(@Nonnull String message) {
		super(message);
	}

	public DatabaseException(@Nonnull Throwable cause) {
		super(cause);
	}

	public DatabaseException(@Nonnull String message, @Nonnull Throwable cause) {
		super(message, cause);
	}
}
