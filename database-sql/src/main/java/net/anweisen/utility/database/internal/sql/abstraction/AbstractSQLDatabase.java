package net.anweisen.utility.database.internal.sql.abstraction;

import net.anweisen.utility.database.DatabaseConfig;
import net.anweisen.utility.database.SqlColumn;
import net.anweisen.utility.database.action.*;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.abstraction.AbstractDatabase;
import net.anweisen.utility.database.internal.sql.abstraction.count.SQLCountEntries;
import net.anweisen.utility.database.internal.sql.abstraction.deletion.SQLDeletion;
import net.anweisen.utility.database.internal.sql.abstraction.insertion.SQLInsertion;
import net.anweisen.utility.database.internal.sql.abstraction.insertorupdate.SQLInsertionOrUpdate;
import net.anweisen.utility.database.internal.sql.abstraction.query.SQLQuery;
import net.anweisen.utility.database.internal.sql.abstraction.update.SQLUpdate;
import net.anweisen.utility.database.internal.sql.abstraction.where.SQLWhere;
import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public abstract class AbstractSQLDatabase extends AbstractDatabase {

	protected Connection connection;

	public AbstractSQLDatabase(@Nonnull DatabaseConfig config) {
		super(config);
	}

	@Override
	public void disconnect0() throws Exception {
		connection.close();
		connection = null;
	}

	@Override
	public void connect0() throws Exception {
		connection = DriverManager.getConnection(createUrl(), config.getUser(), config.getPassword());
	}

	protected abstract String createUrl();

	@Override
	public boolean isConnected() {
		try {
			if (connection == null) return false;
			connection.isClosed();
			return true;
		} catch (SQLException ex) {
			LOGGER.error("Could not check connection state: " + ex.getMessage());
			return false;
		}
	}

	@Override
	public void createTable(@Nonnull String name, boolean update, @Nonnull SqlColumn... columns) throws DatabaseException {
		try {
			StringBuilder command = new StringBuilder();
			command.append("CREATE TABLE IF NOT EXISTS `");
			command.append(name);
			command.append("` (");

			int index = 0;
			for (SqlColumn column : columns) {
				if (index > 0) command.append(", ");
				command.append(column.toFullSql());
				index++;
			}

			command.append(")");

			PreparedStatement statement = prepare(command.toString());
			statement.execute();
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public void editTable(@Nonnull String name, @Nonnull SqlColumn... columns) throws DatabaseException {
		try {
			
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

	@Nonnull
	@Override
	public DatabaseCountEntries countEntries(@Nonnull String table) {
		return new SQLCountEntries(this, table);
	}

	@Nonnull
	@Override
	public DatabaseListColumns listColumns(@Nonnull String table) {
		return null;
	}

	@Nonnull
	@Override
	public DatabaseQuery query(@Nonnull String table) {
		return new SQLQuery(this, table);
	}

	@Nonnull
	public DatabaseQuery query(@Nonnull String table, @Nonnull Map<String, SQLWhere> where) {
		return new SQLQuery(this, table, where);
	}

	@Nonnull
	@Override
	public DatabaseUpdate update(@Nonnull String table) {
		return new SQLUpdate(this, table);
	}

	@Nonnull
	@Override
	public DatabaseInsertion insert(@Nonnull String table) {
		return new SQLInsertion(this, table);
	}

	@Nonnull
	public DatabaseInsertion insert(@Nonnull String table, @Nonnull Map<String, Object> values) {
		return new SQLInsertion(this, table, values);
	}

	@Nonnull
	@Override
	public DatabaseInsertionOrUpdate insertOrUpdate(@Nonnull String table) {
		return new SQLInsertionOrUpdate(this, table);
	}

	@Nonnull
	@Override
	public DatabaseDeletion delete(@Nonnull String table) {
		return new SQLDeletion(this, table);
	}

	@Nonnull
	public PreparedStatement prepare(@Nonnull CharSequence command, @Nonnull Object... args) throws SQLException, DatabaseException {
		checkConnection();
		PreparedStatement statement = connection.prepareStatement(command.toString());
		SQLHelper.fillParams(statement, args);
		return statement;
	}

}