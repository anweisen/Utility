package net.anweisen.utilities.database.internal.abstraction;

import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.SpecificDatabase;
import net.anweisen.utilities.database.action.*;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultSpecificDatabase implements SpecificDatabase {

	protected final Database parent;
	protected final String name;

	public DefaultSpecificDatabase(@Nonnull Database parent, @Nonnull String name) {
		this.parent = parent;
		this.name = name;
	}

	@Override
	public boolean isConnected() {
		return parent.isConnected();
	}

	@Nonnull
	@Override
	public String getName() {
		return name;
	}

	@Nonnull
	@Override
	public DatabaseQuery query() {
		return parent.query(name);
	}

	@Nonnull
	@Override
	public DatabaseUpdate update() {
		return parent.update(name);
	}

	@Nonnull
	@Override
	public DatabaseInsertion insert() {
		return parent.insert(name);
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate insertOrUpdate() {
		return parent.insertOrUpdate(name);
	}

	@Nonnull
	@Override
	public DatabaseDeletion delete() {
		return parent.delete(name);
	}

	@Nonnull
	@Override
	public Database getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return "SpecificDatabase[" + name + "]";
	}
}
