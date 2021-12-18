package net.anweisen.utility.database.action;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.SpecificDatabase;
import net.anweisen.utility.database.exception.DatabaseException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Database#countEntries(String)
 * @see SpecificDatabase#countEntries()
 */
public interface DatabaseCountEntries extends DatabaseAction<Long> {

	@Nonnull
	@Override
	@Nonnegative
	Long execute() throws DatabaseException;

}
