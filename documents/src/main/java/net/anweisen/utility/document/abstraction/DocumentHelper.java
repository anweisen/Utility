package net.anweisen.utility.document.abstraction;

import net.anweisen.utility.document.IEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class DocumentHelper {

	@Nullable
	public static Object toObject(@Nonnull IEntry entry, @Nullable Object realValue) {
		return entry.isNull() ? null
			: entry.isDocument() ? entry.toDocument()
			: entry.isBundle() ? entry.toBundle()
			: entry.isBoolean() ? entry.toBoolean()
			: entry.isNumber() ? entry.toNumber()
			: entry.isChar() ? entry.toChar()
			: realValue;
	}

	public static void throwOutOfBounds(int index, int size) {
		throw new IllegalArgumentException("Index " + index + " out of bounds for size " + size);
	}

	public static void throwUneditable() {
		throw new IllegalStateException("Cannot be edited");
	}

	public static void throwNotNumber() {
		throw new IllegalStateException("Not a number");
	}

	public static void throwNotDocument() {
		throw new IllegalStateException("Not a document");
	}

	public static void throwNotBundle() {
		throw new IllegalStateException("Not a bundle");
	}

	public static void throwNotChar() {
		throw new IllegalStateException("Not a char");
	}

	public static void throwNotUniqueId() {
		throw new IllegalStateException("Not a uuid");
	}

	public static Date toDate(@Nonnull String value) {
		try {
			return DateFormat.getDateTimeInstance().parse(value);
		} catch (ParseException ex) {
			throw new IllegalStateException("Not a date");
		}
	}

	public static Class<?> toClass(@Nonnull String value) {
		try {
			return Class.forName(value);
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Not a class");
		}
	}

	private DocumentHelper() {
	}
}
