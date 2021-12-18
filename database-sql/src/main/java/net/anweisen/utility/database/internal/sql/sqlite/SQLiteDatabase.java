package net.anweisen.utility.database.internal.sql.sqlite;

import net.anweisen.utility.common.misc.FileUtils;
import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.DatabaseConfig;
import net.anweisen.utility.database.action.DatabaseListTables;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.sql.abstraction.AbstractSQLDatabase;
import net.anweisen.utility.database.internal.sql.sqlite.list.SQLiteListTables;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class SQLiteDatabase extends AbstractSQLDatabase {

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException ex) {
			Database.LOGGER.error("Could not load sqlite driver");
		}
	}

	protected final File file;

	public SQLiteDatabase(@Nonnull DatabaseConfig config) {
		super(config);
		file = new File(config.getFile());
	}

	@Override
	public void connect() throws DatabaseException {
		try {
			FileUtils.createFilesIfNecessary(file);
		} catch (IOException ex) {
			throw new DatabaseException(ex);
		}

		super.connect();
	}

	@Override
	protected String createUrl() {
		return "jdbc:sqlite:" + file;
	}

	@Nonnull
	@Override
	public DatabaseListTables listTables() {
		return new SQLiteListTables(this);
	}

}
