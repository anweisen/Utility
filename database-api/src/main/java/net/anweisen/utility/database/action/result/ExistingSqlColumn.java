package net.anweisen.utility.database.action.result;

import net.anweisen.utility.database.SqlColumn;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public record ExistingSqlColumn(String name, SqlColumn.Type type, int size) {
}
