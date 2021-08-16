package net.anweisen.utilities.database.exceptions;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class UnsupportedDatabaseFeature extends DatabaseException {

	public UnsupportedDatabaseFeature() {
	}

	public UnsupportedDatabaseFeature(@Nonnull String message) {
		super(message);
	}

}
