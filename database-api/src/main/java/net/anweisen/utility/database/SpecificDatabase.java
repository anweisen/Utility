package net.anweisen.utility.database;

import net.anweisen.utility.database.action.*;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * Represents a table/collection of a database.
 * Allows use of table actions
 *
 * @author anweisen | https://github.com/anweisen
 * @see Database
 * @see Database#getSpecificDatabase(String)
 * @since 1.0
 */
public interface SpecificDatabase {

	boolean isConnected();

	@Nonnull
	String getName();

	/**
	 * @see Database#countEntries(String)
	 */
	@Nonnull
	@CheckReturnValue
	DatabaseCountEntries countEntries();

	/**
	 * @see Database#query(String)
	 */
	@Nonnull
	@CheckReturnValue
	DatabaseQuery query();

	/**
	 * @see Database#update(String)
	 */
	@Nonnull
	@CheckReturnValue
	DatabaseUpdate update();

	/**
	 * @see Database#insert(String)
	 */
	@Nonnull
	@CheckReturnValue
	DatabaseInsertion insert();

	/**
	 * @see Database#insertOrUpdate(String)
	 */
	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate insertOrUpdate();

	/**
	 * @see Database#delete(String)
	 */
	@Nonnull
	@CheckReturnValue
	DatabaseDeletion delete();

	/**
	 * @see Database#listColumns(String)
	 */
	@Nonnull
	@CheckReturnValue
	DatabaseListColumns listColumns();

	@Nonnull
	Database getParent();

}
