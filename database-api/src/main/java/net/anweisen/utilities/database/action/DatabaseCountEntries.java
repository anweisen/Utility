package net.anweisen.utilities.database.action;

import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DatabaseCountEntries extends DatabaseAction<Long> {

	@Nonnull
	@Override
	Long execute() throws DatabaseException;

}
