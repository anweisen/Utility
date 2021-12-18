package net.anweisen.utility.database.action;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.exception.DatabaseException;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Database#listTables()
 */
public interface DatabaseListTables extends DatabaseAction<List<String>> {

	@Nonnull
	@Override
	List<String> execute() throws DatabaseException;

}
