package net.anweisen.utilities.database.exceptions;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DatabaseUnsupportedFeatureException extends DatabaseException {

	public DatabaseUnsupportedFeatureException() {
	}

	public DatabaseUnsupportedFeatureException(@Nonnull String message) {
		super(message);
	}

}
