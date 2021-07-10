package net.anweisen.utilities.database.action;

import net.anweisen.utilities.common.concurrent.task.Task;
import net.anweisen.utilities.database.Order;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DatabaseQuery {

	@Nonnull
	@CheckReturnValue
	DatabaseQuery where(@Nonnull String field, @Nullable Object object);

	@Nonnull
	@CheckReturnValue
	DatabaseQuery where(@Nonnull String field, @Nullable Number value);

	@Nonnull
	@CheckReturnValue
	DatabaseQuery where(@Nonnull String field, @Nullable String value, boolean ignoreCase);

	@Nonnull
	@CheckReturnValue
	DatabaseQuery where(@Nonnull String field, @Nullable String value);

	@Nonnull
	@CheckReturnValue
	DatabaseQuery whereNot(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@CheckReturnValue
	DatabaseQuery select(@Nonnull String... selection);

	@Nonnull
	@CheckReturnValue
	DatabaseQuery orderBy(@Nonnull String field, @Nonnull Order order);

	@Nonnull
	@CheckReturnValue
	ExecutedQuery execute() throws DatabaseException;

	@Nonnull
	@CheckReturnValue
	default Task<ExecutedQuery> executeAsync() {
		return Task.asyncCall(this::execute);
	}

	boolean equals(@Nonnull ExecutedQuery other);

}
