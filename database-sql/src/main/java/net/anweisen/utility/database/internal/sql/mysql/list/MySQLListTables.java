package net.anweisen.utility.database.internal.sql.mysql.list;

import net.anweisen.utility.database.action.DatabaseListTables;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.sql.abstraction.AbstractSQLDatabase;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MySQLListTables implements DatabaseListTables {

	protected final AbstractSQLDatabase database;

	public MySQLListTables(@Nonnull AbstractSQLDatabase database) {
		this.database = database;
	}

	@Nonnull
	@Override
	public List<String> execute() throws DatabaseException {
		try {
			PreparedStatement statement = database.prepare("SHOW TABLES");
			ResultSet result = statement.executeQuery();

			List<String> tables = new ArrayList<>();
			while (result.next()) {
				tables.add(result.getString(1));
			}
			result.close();
			return tables;
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

}
