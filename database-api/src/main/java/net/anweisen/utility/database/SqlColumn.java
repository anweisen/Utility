package net.anweisen.utility.database;

import net.anweisen.utility.common.misc.ReflectionUtils;
import net.anweisen.utility.common.misc.StringUtils;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * This class represents a column of a table in a sql database.
 * It doesn't support all features (like default values or autoincrement)
 * as the api would no longer be compatible with no-sql databases which dont support these.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class SqlColumn {

	private final String name;
	private final String type;
	private final String param;
	private final boolean notNull;
	private final boolean unique;
	private final boolean primaryKey;

	/**
	 * Creates a new SqlColumn instance
	 *
	 * @param name       the name of the column
	 * @param type       the type of column
	 * @param param      extra params for creation (size, type, ...)
	 * @param notNull    whether this column only allows non-null values
	 * @param unique     whether every value must be unique
	 * @param primaryKey whether this column belongs to the primary key
	 * @throws IllegalArgumentException if the given type or name contains spaces
	 * @see SqlColumn#of(String)
	 * @see SqlColumn.Builder
	 */
	public SqlColumn(@Nonnull String name, @Nonnull String type, @Nullable String param, boolean notNull, boolean unique, boolean primaryKey) {
		if (name.contains(" ")) throw new IllegalArgumentException("Column name cannot contain spaces");
		if (type.contains(" ")) throw new IllegalArgumentException("Column type cannot contain spaces");

		this.name = name;
		this.type = type;
		this.param = param;
		this.notNull = notNull;
		this.unique = unique;
		this.primaryKey = primaryKey;
	}

	public static Builder of(@Nonnull String name) {
		return new Builder().name(name);
	}

	public static Builder of(@Nonnull String name, @Nonnull Type type) {
		return of(name).type(type);
	}

	public static Builder of(@Nonnull String name, @Nonnull Type type, @Nonnegative int size) {
		return of(name, type).size(size);
	}

	public static Builder of(@Nonnull String name, @Nonnull Type type, @Nonnegative int size, @Nonnegative int d) {
		return of(name, type, size).dValue(d);
	}

	public static Builder of(@Nonnull String name, @Nonnull Type type, @Nonnull String... params) {
		return of(name, type).param(params);
	}

	public static Builder of(@Nonnull String name, @Nonnull Type type, @Nonnull Type... params) {
		return of(name, type).param(params);
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
	public Type findType() {
		return ReflectionUtils.getEnumOrNull(type.toUpperCase(), Type.class);
	}

	@Nullable
	public String getParam() {
		return param;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public boolean isUnique() {
		return unique;
	}

	public boolean isNotNull() {
		return notNull;
	}

	@Override
	public String toString() {
		return "SqlColumn[name=" + name + " type=" + type + " param=" + param + " primary=" + primaryKey + " unique=" + unique + " notNull=" + notNull + "]";
	}

	public String toShortSql() {
		return name + " " + type
			+ (param == null ? "" : "(" + param + ")");
	}

	public String toFullSql() {
		return name + " " + type
			+ (param == null ? "" : "(" + param + ")")
			+ (unique ? " UNIQUE" : "")
			+ (notNull ? " NOT NULL" : "");
	}

	public enum Type {
		/**
		 * A FIXED length string (can contain letters, numbers, and special characters).
		 * The size parameter specifies the column length in characters - can be from 0 to 255. Default is 1
		 */
		CHAR,
		/**
		 * A VARIABLE length string (can contain letters, numbers, and special characters).
		 * The size parameter specifies the maximum column length in characters - can be from 0 to 65535
		 */
		VARCHAR,
		/**
		 * Equal to {@link #CHAR}, but stores binary byte strings.
		 * The size parameter specifies the column length in bytes. Default is 1
		 */
		BINARY,
		/**
		 * Equal to {@link #VARCHAR}, but stores binary byte strings.
		 * The size parameter specifies the maximum column length in bytes.
		 */
		VARBINARY,
		/**
		 * For BLOBs (Binary Large Objects). Max length: 255 bytes
		 */
		TINYBLOB,
		/**
		 * Holds a string with a maximum length of 255 characters
		 */
		TINYTEXT,
		/**
		 * Holds a string with a maximum length of 65,535 bytes
		 */
		TEXT,
		/**
		 * For BLOBs (Binary Large Objects). Holds up to 65,535 bytes of data
		 */
		BLOB,
		/**
		 * For BLOBs (Binary Large Objects). Holds up to 65,535 bytes of data
		 */
		MEDIUMTEXT,
		/**
		 * For BLOBs (Binary Large Objects). Holds up to 16,777,215 bytes of data
		 */
		MEDIUMBLOB,
		/**
		 * Holds a string with a maximum length of 4,294,967,295 characters
		 */
		LONGTEXT,
		/**
		 * For BLOBs (Binary Large Objects). Holds up to 4,294,967,295 bytes of data
		 */
		LONGBLOB,
		/**
		 * A string object that can have only one value, chosen from a list of possible values.
		 * You can list up to 65535 values in an ENUM list.
		 * If a value is inserted that is not in the list, a blank value will be inserted.
		 * The values are sorted in the order you enter them
		 */
		ENUM,
		/**
		 * A string object that can have 0 or more values, chosen from a list of possible values.
		 * You can list up to 64 values in a SET list
		 */
		SET,
		/**
		 * A bit-value type. The number of bits per value is specified in size.
		 * The size parameter can hold a value from 1 to 64. The default value for size is 1.
		 */
		BIT,
		/**
		 * A very small integer. Signed range is from -128 to 127. Unsigned range is from 0 to 255.
		 * The size parameter specifies the maximum display width (which is 255)
		 */
		TINYINT,
		/**
		 * Zero is considered as false, nonzero values are considered as true.
		 */
		BOOLEAN,
		/**
		 * A small integer. Signed range is from -32768 to 32767.
		 * Unsigned range is from 0 to 65535.
		 * The size parameter specifies the maximum display width (which is 255)
		 */
		SMALLINT,
		/**
		 * A medium integer. Signed range is from -8388608 to 8388607.
		 * Unsigned range is from 0 to 16777215.
		 * The size parameter specifies the maximum display width (which is 255)
		 */
		MEDIUMINT,
		/**
		 * A medium integer. Signed range is from -2147483648 to 2147483647.
		 * Unsigned range is from 0 to 4294967295.
		 * The size parameter specifies the maximum display width (which is 255)
		 */
		INT,
		/**
		 * A large integer. Signed range is from -9223372036854775808 to 9223372036854775807.
		 * Unsigned range is from 0 to 18446744073709551615.
		 * The size parameter specifies the maximum display width (which is 255)
		 */
		BIGINT,
		/**
		 * A floating point number.
		 * MySQL uses the p value to determine whether to use FLOAT or DOUBLE for the resulting data type.
		 * If p is from 0 to 24, the data type becomes FLOAT().
		 * If p is from 25 to 53, the data type becomes DOUBLE()
		 */
		FLOAT,
		/**
		 * A normal-size floating point number.
		 * The total number of digits is specified in size.
		 * The number of digits after the decimal point is specified in the d parameter
		 */
		DOUBLE,
		/**
		 * An exact fixed-point number. The total number of digits is specified in size.
		 * The number of digits after the decimal point is specified in the d parameter.
		 * The maximum number for size is 65.
		 * The maximum number for d is 30.
		 * The default value for size is 10.
		 * The default value for d is 0.
		 */
		DECIMAL
	}

	public static class Builder {

		private String name;
		private String type;
		private String param;
		private Integer size;
		private Integer d;
		private boolean notNull;
		private boolean unique;
		private boolean primaryKey;

		public Builder() {
		}

		public Builder name(@Nonnull String name) {
			this.name = name;
			return this;
		}

		public Builder type(@Nonnull String type) {
			this.type = type;
			return this;
		}

		public Builder type(@Nonnull Type type) {
			return type(type.name());
		}

		public Builder size(int size) {
			this.size = size;
			return this;
		}

		public Builder dValue(int d) {
			this.d = d;
			return this;
		}

		public Builder param(@Nonnull String param) {
			this.param = param;
			return this;
		}

		public Builder param(@Nonnull String... params) {
			this.param = StringUtils.getArrayAsString(params, ", ");
			return this;
		}

		public Builder param(@Nonnull Type... types) {
			this.param = StringUtils.getIterableAsString(Arrays.asList(types), ", ", Type::name);
			return this;
		}

		public Builder notNull(boolean notNull) {
			this.notNull = notNull;
			return this;
		}

		public Builder notNull() {
			return notNull(true);
		}

		public Builder unique(boolean unique) {
			this.unique = unique;
			return this;
		}

		public Builder unique() {
			return unique(true);
		}

		public Builder primaryKey(boolean primaryKey) {
			this.primaryKey = primaryKey;
			return this;
		}

		public Builder primaryKey() {
			return primaryKey(true);
		}

		public SqlColumn build() {
			if (param != null && (size != null || d != null))
				throw new IllegalArgumentException("Cannot use param and size or d");
			if (size != null || d != null)
				param = d == null ? size + "" : size == null ? d + "" : size + ", " + d;
			return new SqlColumn(name, type, param, notNull, unique, primaryKey);
		}
	}

}
