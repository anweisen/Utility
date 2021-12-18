package net.anweisen.utility.database.action;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.SpecificDatabase;
import net.anweisen.utility.database.action.hierarchy.SetAction;
import net.anweisen.utility.database.exception.DatabaseException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Database#insert(String)
 * @see SpecificDatabase#insert()
 */
public interface DatabaseInsertion extends DatabaseAction<Void>, SetAction {

	@Nonnull
	@CheckReturnValue
	DatabaseInsertion set(@Nonnull String field, @Nullable Object value);

	@Nullable
	@Override
	Void execute() throws DatabaseException;

}
