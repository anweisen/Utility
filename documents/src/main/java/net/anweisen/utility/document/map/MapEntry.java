package net.anweisen.utility.document.map;

import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.IEntry;
import net.anweisen.utility.document.abstraction.DocumentHelper;
import net.anweisen.utility.document.gson.GsonHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MapEntry implements IEntry {

	private Object value;

	public MapEntry(@Nullable Object value) {
		this.value = value;
	}

	@Override
	public boolean isNull() {
		return value == null;
	}

	@Override
	public boolean isBundle() {
		return value instanceof Bundle || value instanceof Collection; // TODO arrays & iterables & iterators
	}

	@Override
	public boolean isDocument() {
		return value instanceof Document || value instanceof Map;
	}

	@Override
	public boolean isNumber() {
		return value instanceof Number;
	}

	@Override
	public boolean isBoolean() {
		return value instanceof Boolean;
	}

	@Override
	public boolean isChar() {
		return value instanceof Character || value instanceof String && ((String)value).length() == 1;
	}

	@Override
	public Object toObject() {
		return value;
	}

	@Override
	public String toString() {
		return toString(null);
	}

	@Override
	public String toString(@Nullable String def) {
		return value == null ? def : String.valueOf(value);
	}

	@Override
	public long toLong(long def) {
		return isNull() ? def : toNumber().longValue();
	}

	@Override
	public int toInt(int def) {
		return isNull() ? def : toNumber().intValue();
	}

	@Override
	public short toShort(short def) {
		return isNull() ? def : toNumber().shortValue();
	}

	@Override
	public byte toByte(byte def) {
		return isNull() ? def : toNumber().byteValue();
	}

	@Override
	public float toFloat(float def) {
		return isNull() ? def : toNumber().floatValue();
	}

	@Override
	public double toDouble(double def) {
		return isNull() ? def : toNumber().doubleValue();
	}

	@Override
	public char toChar(char def) {
		if (isNull()) return def;
		if (!isChar()) throw new IllegalStateException("Not a char");
		return value instanceof Character ? (char) value : String.valueOf(value).charAt(0);
	}

	@Override
	public boolean toBoolean(boolean def) {
		return isNull() ? def : isBoolean() ? (boolean) value : isNumber() ? toInt() == 1 : Boolean.parseBoolean(toString());
	}

	@Override
	public Number toNumber() {
		if (!isNumber()) DocumentHelper.throwNotNumber();
		return (Number) value;
	}

	@Override
	public Document toDocument() {
		if (!isDocument()) DocumentHelper.throwNotDocument();
		return (Document) value;
	}

	@Override
	public Bundle toBundle() {
		if (!isBundle()) DocumentHelper.throwNotBundle();
		if (value instanceof Collection) value = new MapBundle((Collection<?>) value);
		return (Bundle) value;
	}

	@Override
	public OffsetDateTime toOffsetDateTime() {
		if (value == null) return null;
		if (value instanceof OffsetDateTime) return (OffsetDateTime) value;
		if (value instanceof String) return OffsetDateTime.parse((CharSequence) value);
		if (value instanceof Date) return ((Date)value).toInstant().atOffset(ZoneOffset.UTC);
		if (value instanceof Instant) return ((Instant)value).atOffset(ZoneOffset.UTC);
		throw new IllegalStateException("Not a OffsetDateTime");
	}

	@Override
	public Date toDate() {
		if (value == null) return null;
		if (value instanceof Date) return (Date) value;
		if (value instanceof String) return DocumentHelper.toDate((String) value);
		if (value instanceof OffsetDateTime) return Date.from(((OffsetDateTime)value).toInstant());
		if (value instanceof Instant) return Date.from((Instant) value);
		throw new IllegalStateException("Not a date");
	}

	@Override
	public Color toColor() {
		if (value == null) return null;
		if (value instanceof Color) return (Color) value;
		if (value instanceof String) return Color.decode((String) value);
		throw new IllegalStateException("Not a color");
	}

	@Override
	public Class<?> toClass() {
		if (value == null) return null;
		if (value instanceof Class) return (Class<?>) value;
		if (value instanceof String) return DocumentHelper.toClass((String) value);
		throw new IllegalStateException("Not a class");
	}

	@Override
	public <T> T toInstance(@Nonnull Class<T> classOfT) {
		try {
			return classOfT.cast(value);
		} catch (ClassCastException ex) {
			throw new IllegalStateException("Not a " + classOfT.getSimpleName());
		}
	}

	@Nonnull
	@Override
	public String toJson() {
		return GsonHelper.toGsonEntry(value).toJson();
	}

	@Nonnull
	@Override
	public String toPrettyJson() {
		return GsonHelper.toGsonEntry(value).toPrettyJson();
	}
}
