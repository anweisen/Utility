package net.anweisen.utilities.database.internal.sql.mysql;

import net.anweisen.utilities.database.DatabaseConfig;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.internal.sql.abstraction.AbstractSQLDatabase;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class MySQLDatabase extends AbstractSQLDatabase {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			LOGGER.error("Could not load mysql driver");
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
	public Collection<String> listTables() throws DatabaseException {
		try {
			PreparedStatement statement = prepare("SHOW TABLES");
			ResultSet result = statement.executeQuery();

			Collection<String> tables = new ArrayList<>();
			while (result.next()) {
				tables.add(result.getString(1));
			}
			return tables;
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}
}
