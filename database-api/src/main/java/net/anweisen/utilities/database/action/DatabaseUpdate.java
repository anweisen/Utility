package net.anweisen.utilities.database.action;

import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.SpecificDatabase;
import net.anweisen.utilities.database.action.hierarchy.SetAction;
import net.anweisen.utilities.database.action.hierarchy.WhereAction;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Database#update(String)
 * @see SpecificDatabase#update()
 */
public interface DatabaseUpdate extends DatabaseAction<Void>, WhereAction, SetAction {

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

	@Nullable
	@Override
	Void execute() throws DatabaseException;

}
