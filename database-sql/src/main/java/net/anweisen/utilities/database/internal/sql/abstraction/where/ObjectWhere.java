package net.anweisen.utilities.database.internal.sql.abstraction.where;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class ObjectWhere implements SQLWhere {

	protected final String column;
	protected final Object value;

	public ObjectWhere(@Nonnull String column, @Nullable Object value) {
		this.column = column;
		this.value = value;
	}

	@Nonnull
	@Override
	public Object[] getArgs() {
		return new Object[] { value };
	}

	@Nonnull
	@Override
	public String getAsSQLString() {
		return String.format("%s = ?", column);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ObjectWhere that = (ObjectWhere) o;
		return column.equals(that.column) && Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, value);
	}

}
