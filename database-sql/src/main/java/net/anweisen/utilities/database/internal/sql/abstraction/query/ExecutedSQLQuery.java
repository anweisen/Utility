package net.anweisen.utilities.database.internal.sql.abstraction.query;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.database.internal.abstraction.AbstractExecutedQuery;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class ExecutedSQLQuery extends AbstractExecutedQuery {

	public ExecutedSQLQuery(@Nonnull ResultSet result) throws SQLException {
		super(new ArrayList<>());

		ResultSetMetaData data = result.getMetaData();
		while (result.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				Object value = result.getObject(i);
				map.put(data.getColumnLabel(i), value);
			}
			Document row = new SQLResult(map);
			results.add(row);
		}
	}

}
