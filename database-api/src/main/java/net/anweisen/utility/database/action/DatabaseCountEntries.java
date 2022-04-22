package net.anweisen.utility.database.action;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.SpecificDatabase;
import net.anweisen.utility.database.action.hierarchy.TableAction;
import net.anweisen.utility.database.exception.DatabaseException;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @see Database#countEntries(String)
 * @see SpecificDatabase#countEntries()
 * @since 1.0
 */
public interface DatabaseCountEntries extends DatabaseAction<Long>, TableAction {

	@Nonnull
	@Override
	@Nonnegative
	Long execute() throws DatabaseException;

}
