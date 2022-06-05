package net.anweisen.utility.database.action.result;

import net.anweisen.utility.database.SqlColumn;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ExistingSqlColumn {

	private final String name;
	private final SqlColumn.Type type;
	private final int size;

	public ExistingSqlColumn(@Nonnull String name, @Nonnull SqlColumn.Type type, int size) {
		this.name = name;
		this.type = type;
		this.size = size;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public SqlColumn.Type getType() {
		return type;
	}

	public int getSize() {
		return size;
	}
}
