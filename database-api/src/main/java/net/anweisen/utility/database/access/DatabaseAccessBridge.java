package net.anweisen.utility.database.access;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.SpecificDatabase;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.document.Document;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public interface DatabaseAccessBridge<V> {

	@Nullable
	V get(@Nonnull String key) throws DatabaseException;

	@Nonnull
	V get(@Nonnull String key, @Nonnull V def) throws DatabaseException;

	@Nonnull
	Optional<V> getOptional(@Nonnull String key) throws DatabaseException;

	void set(@Nonnull String key, @Nullable V value) throws DatabaseException;

	default boolean has(@Nonnull String key) throws DatabaseException {
		return getOptional(key).isPresent();
	}

	@Nonnull
	Database getDatabase();

}
