package net.anweisen.utility.database.internal.sql.abstraction.count;

import net.anweisen.utility.database.action.DatabaseCountEntries;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.sql.abstraction.AbstractSQLDatabase;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class SQLCountEntries implements DatabaseCountEntries {

	protected final AbstractSQLDatabase database;
	protected final String table;

	public SQLCountEntries(@Nonnull AbstractSQLDatabase database, @Nonnull String table) {
		this.database = database;
		this.table = table;
	}

	@Nonnull
	@Override
	public Long execute() throws DatabaseException {
		try {
			PreparedStatement statement = database.prepare("SELECT COUNT(*) FROM `" + table + "`");
			ResultSet result = statement.executeQuery();

			if (!result.next()) {
				result.close();
				return 0L;
			}

			long count = result.getLong(1);
			result.close();
			return count;
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SQLCountEntries that = (SQLCountEntries) o;
		return Objects.equals(database, that.database) && Objects.equals(table, that.table);
	}

	@Override
	public int hashCode() {
		return Objects.hash(database, table);
	}
}
