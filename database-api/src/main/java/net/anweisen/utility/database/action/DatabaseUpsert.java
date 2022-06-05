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
 * @see Database#upsert(String)
 * @see SpecificDatabase#upsert()
 * @since 1.0
 */
public interface DatabaseUpsert extends DatabaseUpdate, DatabaseInsertion, TableAction {

	@Nonnull
	@CheckReturnValue
	DatabaseUpsert where(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@CheckReturnValue
	DatabaseUpsert where(@Nonnull String field, @Nullable Number value);

	@Nonnull
	@CheckReturnValue
	DatabaseUpsert where(@Nonnull String field, @Nullable String value, boolean ignoreCase);

	@Nonnull
	@CheckReturnValue
	DatabaseUpsert where(@Nonnull String field, @Nullable String value);

	@Nonnull
	@Override
	DatabaseUpsert whereNot(@Nonnull String field, @Nullable Object value);

	@Nonnull
	@Override
	DatabaseUpsert set(@Nonnull String field, @Nullable Object value);

	@Nullable
	@Override
	Void execute() throws DatabaseException;

}
