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
public class DirectDatabaseAccess<V> implements DatabaseAccess<V> {

	protected final Database database;
	protected final DatabaseAccessConfig config;
	protected final BiFunction<? super Document, ? super String, ? extends V> mapper;

	public DirectDatabaseAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config, @Nonnull BiFunction<? super Document, ? super String, ? extends V> mapper) {
		this.database = database;
		this.config = config;
		this.mapper = mapper;
	}

	@Nullable
	@Override
	public V getValue(@Nonnull String key) throws DatabaseException {
		return getValue0(key).orElse(null);
	}

	@Nonnull
	@Override
	public V getValue(@Nonnull String key, @Nonnull V def) throws DatabaseException {
		return getValue0(key).orElse(def);
	}

	@Nonnull
	@Override
	public Optional<V> getValueOptional(@Nonnull String key) throws DatabaseException {
		return getValue0(key);
	}

	@Nonnull
	protected Optional<V> getValue0(@Nonnull String key) throws DatabaseException {
		return database.query(config.getTable())
				.where(config.getKeyField(), key)
				.execute().first()
				.map(document -> mapper.apply(document, config.getValueField()));
	}

	@Override
	public void setValue(@Nonnull String key, @Nonnull V value) throws DatabaseException {
		database.insertOrUpdate(config.getTable())
				.set(config.getValueField(), value)
				.where(config.getKeyField(), key)
				.execute();
	}

	@Nonnull
	@Override
	public Database getDatabase() {
		return database;
	}

	@Nonnull
	@Override
	public DatabaseAccessConfig getConfig() {
		return config;
	}
}
