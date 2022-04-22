package net.anweisen.utility.database.action.hierarchy;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Database actions which implement this interface are capable of specifying where the action should be performed.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface WhereAction {

	@Nonnull
	@CheckReturnValue
	WhereAction where(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@CheckReturnValue
	WhereAction where(@Nonnull String field, @Nullable Number value);

	@Nonnull
	@CheckReturnValue
	WhereAction where(@Nonnull String field, @Nullable String value, boolean ignoreCase);

	@Nonnull
	@CheckReturnValue
	WhereAction where(@Nonnull String field, @Nullable String value);

	@Nonnull
	@CheckReturnValue
	WhereAction whereNot(@Nonnull String field, @Nullable Object value);

}
