package net.anweisen.utilities.database.access;

import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class CachedDatabaseAccess extends DirectDatabaseAccess {

	protected final Map<String, String> cache = new ConcurrentHashMap<>();

	public CachedDatabaseAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig setup) {
		super(database, setup);
	}

	@Nullable
	@Override
	public String getValue(@Nonnull String key) throws DatabaseException {
		String value = cache.get(key);
		if (value != null) return value;

		value = super.getValue(key);
		cache.put(key, value);
		return value;
	}

	@Nonnull
	@Override
	public String getValue(@Nonnull String key, @Nonnull String def) throws DatabaseException {
		String value = cache.get(key);
		if (value != null) return value;

		value = super.getValue(key, def);
		cache.put(key, value);
		return value;
	}

	@Override
	public void setValue(@Nonnull String key, @Nonnull String value) throws DatabaseException {
		cache.put(key, value);
		super.setValue(key, value);
	}

}
