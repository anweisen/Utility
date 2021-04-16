package net.anweisen.utilities.database.access;

import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public interface DatabaseAccess {

	@Nullable
	String getValue(@Nonnull String key) throws DatabaseException;

	@Nonnull
	String getValue(@Nonnull String key, @Nonnull String def) throws DatabaseException;

	@Nonnull
	Optional<String> getValueOptional(@Nonnull String key) throws DatabaseException;

	void setValue(@Nonnull String key, @Nonnull String value) throws DatabaseException;

	@Nonnull
	Database getDatabase();

	@Nonnull
	DatabaseAccessConfig getConfig();

}
