package net.anweisen.utilities.database.action;

import net.anweisen.utilities.database.action.hierarchy.SetAction;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DatabaseInsertion extends DatabaseAction<Void>, SetAction {

	@Nonnull
	@CheckReturnValue
	DatabaseInsertion set(@Nonnull String field, @Nullable Object value);

	@Nullable
	@Override
	Void execute() throws DatabaseException;

	boolean equals(@Nonnull DatabaseInsertion other);

}
