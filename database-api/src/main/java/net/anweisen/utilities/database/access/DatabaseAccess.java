package net.anweisen.utilities.database.access;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiFunction;

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

	void setValue(@Nonnull String key, @Nonnull V value) throws DatabaseException;

	@Nonnull
	BiFunction<? super Document, ? super String, ? extends V> getMapper();

	@Nonnull
	Database getDatabase();

	@Nonnull
	DatabaseAccessConfig getConfig();

}
