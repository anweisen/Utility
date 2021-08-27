package net.anweisen.utilities.database.exceptions;

import net.anweisen.utilities.common.collection.WrappedException;
import net.anweisen.utilities.database.action.DatabaseAction;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see DatabaseException
 *
 * @see DatabaseAction#executeUnsigned()
 */
public class UnsignedDatabaseException extends WrappedException {

	public UnsignedDatabaseException(@Nonnull DatabaseException cause) {
		super(cause);
	}

	@Nonnull
	@Override
	public DatabaseException getCause() {
		return (DatabaseException) super.getCause();
	}
}
