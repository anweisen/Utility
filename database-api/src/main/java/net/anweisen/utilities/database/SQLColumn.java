package net.anweisen.utilities.database;

import net.anweisen.utilities.commons.misc.StringUtils;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class SQLColumn {

	public enum Type {
		CHAR,
		VARCHAR,
		BINARY,
		VARBINARY,
		TINYBLOB,
		TINYTEXT,
		TEXT,
		BLOB,
		MEDIUMTEXT,
		MEDIUMBLOB,
		LONGTEXT,
		ENUM,
		SET,
		BIT,
		TINYINT,
		BOOLEAN,
		SMALLINT,
		MEDIUMINT,
		INT,
		BIGINT,
		FLOAT,
		DOUBLE,
		DECIMAL;
	}

	private final String name;
	private final String type;
	private final String param;

	public SQLColumn(@Nonnull String name, @Nonnull String type, @Nullable String param) {
		if (name.contains(" ")) throw new IllegalArgumentException("Column name cannot contain spaces");
		if (type.contains(" ")) throw new IllegalArgumentException("Column type cannot contain spaces");

		this.name = name;
		this.type = type;
		this.param = param;
	}

	public SQLColumn(@Nonnull String name, @Nonnull String type, @Nonnegative int size) {
		this(name, type, String.valueOf(size));
	}

	public SQLColumn(@Nonnull String name, @Nonnull String type, @Nonnegative int size, @Nonnegative int d) {
		this(name, type, String.valueOf(size), String.valueOf(d));
	}

	public SQLColumn(@Nonnull String name, @Nonnull String type, @Nonnull String... params) {
		this(name, type, StringUtils.getArrayAsString(params, ", "));
	}

	public SQLColumn(@Nonnull String name, @Nonnull Type type, @Nullable String param) {
		this(name, type.name(), param);
	}

	public SQLColumn(@Nonnull String name, @Nonnull Type type, @Nonnegative int size) {
		this(name, type.name(), size);
	}

	public SQLColumn(@Nonnull String name, @Nonnull Type type, @Nonnegative int size, @Nonnegative int d) {
		this(name, type.name(), size, d);
	}

	public SQLColumn(@Nonnull String name, @Nonnull Type type, @Nonnull String... params) {
		this(name, type.name(), params);
	}

	public SQLColumn(@Nonnull String name, @Nonnull Type type, @Nonnull Type... types) {
		this(name, type, Arrays.stream(types).map(Type::name).toArray(String[]::new));
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public String getType() {
		return type;
	}

	@Nullable
	public String getParam() {
		return param;
	}

	@Override
	public String toString() {
		return name + " " + type + (param == null ? "" : "(" + param + ")");
	}

}
