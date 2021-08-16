package net.anweisen.utilities.database.action;

import net.anweisen.utilities.common.concurrent.task.Task;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.exceptions.UnsignedDatabaseException;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DatabaseAction<R> {

	R execute() throws DatabaseException;

	default R executeUnsigned() {
		try {
			return execute();
		} catch (DatabaseException ex) {
			throw new UnsignedDatabaseException(ex);
		}
	}

	@Nonnull
	default Task<R> executeAsync() {
		return Task.asyncCall(this::execute);
	}

}
