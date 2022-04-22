package net.anweisen.utility.database.access;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.exception.DatabaseException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public interface DatabaseAccess<V> {

	@Nullable
	V getValue(@Nonnull String key) throws DatabaseException;

	@Nonnull
	V getValue(@Nonnull String key, @Nonnull V def) throws DatabaseException;

	@Nonnull
	Optional<V> getValueOptional(@Nonnull String key) throws DatabaseException;

	void setValue(@Nonnull String key, @Nullable V value) throws DatabaseException;

	default boolean hasValue(@Nonnull String key) throws DatabaseException {
		return getValueOptional(key).isPresent();
	}

	@Nonnull
	Database getDatabase();

	@Nonnull
	DatabaseAccessConfig getConfig();

}
