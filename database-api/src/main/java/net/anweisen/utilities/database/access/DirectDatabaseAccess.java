package net.anweisen.utilities.database.access;

import net.anweisen.utilities.commons.config.Propertyable;
import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.exceptions.DatabaseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class DirectDatabaseAccess implements DatabaseAccess {

	protected final Database database;
	protected final DatabaseAccessConfig config;

	public DirectDatabaseAccess(@Nonnull Database database, @Nonnull DatabaseAccessConfig config) {
		this.database = database;
		this.config = config;
	}


	@Nullable
	@Override
	public String getValue(@Nonnull String key) throws DatabaseException {
		return getValue0(key).orElse(null);
	}

	@Nonnull
	@Override
	public String getValue(@Nonnull String key, @Nonnull String def) throws DatabaseException {
		return getValue0(key).orElse(def);
	}

	@Nonnull
	protected Optional<String> getValue0(@Nonnull String key) throws DatabaseException {
		return database.query(config.getTable())
				.where(config.getKeyField(), key)
				.execute().firstOrEmpty()
				.getOptional(config.getValueField(), Propertyable::getString);
	}

	@Override
	public void setValue(@Nonnull String key, @Nonnull String value) throws DatabaseException {
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
