package net.anweisen.utilities.database.access;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public final class DatabaseAccessConfig {

	private final String table;
	private final String keyField;
	private final String valueField;

	public DatabaseAccessConfig(@Nonnull String table, @Nonnull String keyField, @Nonnull String valueField) {
		this.table = table;
		this.keyField = keyField;
		this.valueField = valueField;
	}

	@Nonnull
	public String getTable() {
		return table;
	}

	@Nonnull
	public String getKeyField() {
		return keyField;
	}

	@Nonnull
	public String getValueField() {
		return valueField;
	}

}
