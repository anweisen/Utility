package net.anweisen.utilities.database.action;

import net.anweisen.utilities.common.concurrent.task.Task;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DatabaseInsertion {

	@Nonnull
	@CheckReturnValue
	DatabaseInsertion set(@Nonnull String field, @Nullable Object value);

	void execute() throws DatabaseException;

	@Nonnull
	default Task<Void> executeAsync() {
		return Task.asyncRunExceptionally(this::execute);
	}

	boolean equals(@Nonnull DatabaseInsertion other);

}
