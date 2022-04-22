package net.anweisen.utility.database.action.hierarchy;

import javax.annotation.Nonnull;

/**
 * Database actions which implement this interface have to specify a table on which the action is performed.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface TableAction {

	@Nonnull
	String getTable();

}
