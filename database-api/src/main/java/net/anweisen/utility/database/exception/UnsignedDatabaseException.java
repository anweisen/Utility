package net.anweisen.utility.database.exception;

import net.anweisen.utility.common.collection.WrappedException;
import net.anweisen.utility.database.action.DatabaseAction;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @see DatabaseException
 * @see DatabaseAction#executeUnsigned()
 * @since 1.0
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
