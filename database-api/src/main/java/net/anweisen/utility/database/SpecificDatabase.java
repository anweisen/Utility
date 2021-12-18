package net.anweisen.utility.database;

import net.anweisen.utility.database.action.*;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * Represents a table/collection of a database
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Database
 * @see Database#getSpecificDatabase(String)
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

	@Nonnull
	Database getParent();

}
