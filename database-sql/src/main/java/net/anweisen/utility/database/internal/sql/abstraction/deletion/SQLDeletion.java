package net.anweisen.utility.database.internal.sql.abstraction.deletion;

import net.anweisen.utility.database.action.DatabaseDeletion;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.sql.abstraction.AbstractSQLDatabase;
import net.anweisen.utility.database.internal.sql.abstraction.where.ObjectWhere;
import net.anweisen.utility.database.internal.sql.abstraction.where.SQLWhere;
import net.anweisen.utility.database.internal.sql.abstraction.where.StringIgnoreCaseWhere;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class SQLDeletion implements DatabaseDeletion {

	protected final AbstractSQLDatabase database;
	protected final String table;
	protected final Map<String, SQLWhere> where = new HashMap<>();

	public SQLDeletion(@Nonnull AbstractSQLDatabase database, @Nonnull String table) {
		this.database = database;
		this.table = table;
	}

	@Nonnull
	@Override
	public DatabaseDeletion where(@Nonnull String column, @Nullable Object value) {
		where.put(column, new ObjectWhere(column, value, "="));
		return this;
	}

	@Nonnull
	@Override
	public DatabaseDeletion where(@Nonnull String column, @Nullable Number value) {
		return where(column, (Object) value);
	}

	@Nonnull
	@Override
	public DatabaseDeletion where(@Nonnull String column, @Nullable String value) {
		return where(column, (Object) value);
	}

	@Nonnull
	@Override
	public DatabaseDeletion where(@Nonnull String column, @Nullable String value, boolean ignoreCase) {
		if (!ignoreCase) return where(column, value);
		if (value == null) throw new NullPointerException("Cannot use where ignore case with null value");
		where.put(column, new StringIgnoreCaseWhere(column, value));
		return this;
	}

	@Nonnull
	@Override
	public DatabaseDeletion whereNot(@Nonnull String column, @Nullable Object value) {
		where.put(column, new ObjectWhere(column, value, "!="));
		return this;
	}

	@Nonnull
	protected PreparedStatement prepare() throws SQLException, DatabaseException {
		StringBuilder command = new StringBuilder();
		List<Object> args = new ArrayList<>();

		command.append("DELETE FROM ");
		command.append(table);

		if (!where.isEmpty()) {
			command.append(" WHERE ");
			int index = 0;
			for (Entry<String, SQLWhere> entry : where.entrySet()) {
				SQLWhere where = entry.getValue();
				if (index > 0) command.append(" AND ");
				command.append(where.getAsSQLString());
				args.addAll(Arrays.asList(where.getArgs()));
				index++;
			}
		}

		return database.prepare(command.toString(),args.toArray());
	}

	@Override
	public Void execute() throws DatabaseException {
		try {
			PreparedStatement statement = prepare();
			statement.execute();
			return null;
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SQLDeletion that = (SQLDeletion) o;
		return database.equals(that.database) && table.equals(that.table) && where.equals(that.where);
	}

	@Override
	public int hashCode() {
		return Objects.hash(database, table, where);
	}

}
