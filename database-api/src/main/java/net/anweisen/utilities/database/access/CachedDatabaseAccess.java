package net.anweisen.utilities.database.access;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.config.Propertyable;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class CachedDatabaseAccess<V> extends DirectDatabaseAccess<V> {

	protected final Map<String, V> cache = new ConcurrentHashMap<>();

	public CachedDatabaseAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config, @Nonnull BiFunction<? super Document, ? super String, ? extends V> mapper) {
		super(database, config, mapper);
	}

	@Nullable
	@Override
	public V getValue(@Nonnull String key) throws DatabaseException {
		V value = cache.get(key);
		if (value != null) return value;

		value = super.getValue(key);
		cache.put(key, value);
		return value;
	}

	@Nonnull
	@Override
	public V getValue(@Nonnull String key, @Nonnull V def) throws DatabaseException {
		V value = cache.get(key);
		if (value != null) return value;

		value = super.getValue(key, def);
		cache.put(key, value);
		return value;
	}

	@Nonnull
	@Override
	public Optional<V> getValueOptional(@Nonnull String key) throws DatabaseException {
		V cached = cache.get(key);
		if (cached != null) return Optional.of(cached);

		return super.getValueOptional(key);
	}

	@Override
	public void setValue(@Nonnull String key, @Nullable V value) throws DatabaseException {
		cache.put(key, value);
		super.setValue(key, value);
	}

	@Nonnull
	public static CachedDatabaseAccess<String> newStringAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config) {
		return new CachedDatabaseAccess<>(database, config, Propertyable::getString);
	}

	@Nonnull
	public static CachedDatabaseAccess<Integer> newIntAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config) {
		return new CachedDatabaseAccess<>(database, config, Propertyable::getInt);
	}

	@Nonnull
	public static CachedDatabaseAccess<Long> newLongAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config) {
		return new CachedDatabaseAccess<>(database, config, Propertyable::getLong);
	}

	@Nonnull
	public static CachedDatabaseAccess<Double> newDoubleAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config) {
		return new CachedDatabaseAccess<>(database, config, Propertyable::getDouble);
	}

	@Nonnull
	public static CachedDatabaseAccess<Document> newDocumentAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config) {
		return new CachedDatabaseAccess<>(database, config, Document::getDocument);
	}

}
