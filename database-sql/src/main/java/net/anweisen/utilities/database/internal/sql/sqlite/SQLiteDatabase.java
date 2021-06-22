package net.anweisen.utilities.database.internal.sql.sqlite;

import net.anweisen.utilities.common.misc.FileUtils;
import net.anweisen.utilities.database.DatabaseConfig;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.database.internal.sql.abstraction.AbstractSQLDatabase;

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
			LOGGER.error("Could not load sqlite driver");
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
	protected String createURL() {
		return "jdbc:sqlite:" + file;
	}

}
