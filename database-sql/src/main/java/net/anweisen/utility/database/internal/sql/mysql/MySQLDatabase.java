package net.anweisen.utility.database.internal.sql.mysql;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.DatabaseConfig;
import net.anweisen.utility.database.action.DatabaseListTables;
import net.anweisen.utility.database.internal.sql.abstraction.AbstractSQLDatabase;
import net.anweisen.utility.database.internal.sql.mysql.list.MySQLListTables;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class MySQLDatabase extends AbstractSQLDatabase {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			Database.LOGGER.error("Could not load mysql driver");
		}
	}

	public MySQLDatabase(@Nonnull DatabaseConfig config) {
		super(config);
	}

	@Nonnull
	@Override
	protected String createUrl() {
		return "jdbc:mysql://" + config.getHost() + (config.isPortSet() ? ":" + config.getPort() : "") + "/" + config.getDatabase();
	}

	@Nonnull
	@Override
	public DatabaseListTables listTables() {
		return new MySQLListTables(this);
	}

}
