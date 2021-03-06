package net.anweisen.utilities.database.action;

import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.SpecificDatabase;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Database#insertOrUpdate(String)
 * @see SpecificDatabase#insertOrUpdate()
 */
public interface DatabaseInsertionOrUpdate extends DatabaseUpdate, DatabaseInsertion {

	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable Number value);

	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable String value, boolean ignoreCase);

	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate where(@Nonnull String field, @Nullable String value);

	@Nonnull
	@Override
	DatabaseInsertionOrUpdate whereNot(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@Override
	DatabaseInsertionOrUpdate set(@Nonnull String field, @Nullable Object value);

	@Nullable
	@Override
	Void execute() throws DatabaseException;

}
