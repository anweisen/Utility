package net.anweisen.utility.database.exception;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DatabaseUnsupportedFeatureException extends DatabaseException {

	public DatabaseUnsupportedFeatureException() {
		super();
	}

	public DatabaseUnsupportedFeatureException(@Nonnull String message) {
		super(message);
	}

}
