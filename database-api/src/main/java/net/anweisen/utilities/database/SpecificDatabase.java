package net.anweisen.utilities.database;

import net.anweisen.utilities.database.action.*;

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

	@Nonnull
	@CheckReturnValue
	DatabaseCountEntries countEntries();

	@Nonnull
	@CheckReturnValue
	DatabaseQuery query();

	@Nonnull
	@CheckReturnValue
	DatabaseUpdate update();

	@Nonnull
	@CheckReturnValue
	DatabaseInsertion insert();

	@Nonnull
	@CheckReturnValue
	DatabaseInsertionOrUpdate insertOrUpdate();

	@Nonnull
	@CheckReturnValue
	DatabaseDeletion delete();

	@Nonnull
	Database getParent();

}
