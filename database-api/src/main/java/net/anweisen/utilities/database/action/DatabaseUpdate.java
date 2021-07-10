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
public interface DatabaseUpdate {

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate where(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate where(@Nonnull String field, @Nullable Number value);

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate where(@Nonnull String field, @Nullable String value, boolean ignoreCase);

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate where(@Nonnull String field, @Nullable String value);

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate whereNot(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate set(@Nonnull String field, @Nullable Object value);

	void execute() throws DatabaseException;

	@Nonnull
	default Task<Void> executeAsync() {
		return Task.asyncRunExceptionally(this::execute);
	}

	boolean equals(@Nonnull DatabaseUpdate other);

}
