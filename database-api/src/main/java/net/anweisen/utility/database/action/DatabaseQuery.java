package net.anweisen.utility.database.action;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.Order;
import net.anweisen.utility.database.SpecificDatabase;
import net.anweisen.utility.database.action.hierarchy.OrderedAction;
import net.anweisen.utility.database.action.hierarchy.WhereAction;
import net.anweisen.utility.database.exception.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Database#query(String)
 * @see SpecificDatabase#query()
 */
public interface DatabaseQuery extends DatabaseAction<ExecutedQuery>, WhereAction, OrderedAction {

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
	@Override
	@CheckReturnValue
	ExecutedQuery execute() throws DatabaseException;

}
