package net.anweisen.utility.database.internal.mongodb.list;

import net.anweisen.utility.database.action.DatabaseListTables;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.database.internal.mongodb.MongoDBDatabase;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MongoDBListTables implements DatabaseListTables {

	protected final MongoDBDatabase database;

	public MongoDBListTables(@Nonnull MongoDBDatabase database) {
		this.database = database;
	}

	@Nonnull
	@Override
	public List<String> execute() throws DatabaseException {
		try {
			return database.getDatabase().listCollectionNames().into(new ArrayList<>());
		} catch (Exception ex) {
			throw new DatabaseException(ex);
		}
	}

}
