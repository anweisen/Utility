package net.anweisen.utility.database.action;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.SpecificDatabase;
import net.anweisen.utility.database.action.hierarchy.TableAction;
import net.anweisen.utility.database.exception.DatabaseException;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @see Database#insertOrUpdate(String)
 * @see SpecificDatabase#insertOrUpdate()
 * @since 1.0
 */
public interface DatabaseInsertionOrUpdate extends DatabaseUpdate, DatabaseInsertion, TableAction {

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
