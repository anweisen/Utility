package net.anweisen.utility.database;

import net.anweisen.utility.common.misc.ReflectionUtils;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
public final class SimpleDatabaseTypeResolver {

	private static final Map<String, String> registry = new ConcurrentHashMap<>();

	static {
		// register default built-in database classes
		registerType("mongodb", "net.anweisen.utility.database.internal.mongodb.MongoDBDatabase");
		registerType("mysql", "net.anweisen.utility.database.internal.sql.mysql.MySQLDatabase");
		registerType("sqlite", "net.anweisen.utility.database.internal.sql.sqlite.SQLiteDatabase");
	}

	private SimpleDatabaseTypeResolver() {
	}

	@Nullable
	public static Class<? extends Database> findDatabaseType(@Nonnull String name) {
		return ReflectionUtils.getClassOrNull(registry.get(name));
	}

	@Nullable
	public static Class<? extends Database> findDatabaseType(@Nonnull String name, boolean initialize, @Nonnull ClassLoader classLoader) {
		return ReflectionUtils.getClassOrNull(registry.get(name), initialize, classLoader);
	}

	public static void registerType(@Nonnull String name, @Nonnull String className) {
		registry.put(name, className);
	}

	public static void registerType(@Nonnull String name, @Nonnull Class<? extends Database> databaseClass) {
		registerType(name, databaseClass.getName());
	}

}
