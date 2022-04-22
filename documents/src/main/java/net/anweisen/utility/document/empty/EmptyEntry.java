package net.anweisen.utility.document.empty;

import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.IEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class EmptyEntry implements IEntry {

	public static final EmptyEntry INSTANCE = new EmptyEntry();

	@Override
	public boolean isNull() {
		return true;
	}

	@Override
	public boolean isBundle() {
		return false;
	}

	@Override
	public boolean isDocument() {
		return false;
	}

	@Override
	public boolean isObject() {
		return false;
	}

	@Override
	public boolean isNumber() {
		return false;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

	@Override
	public boolean isChar() {
		return false;
	}

	@Override
	public Object toObject() {
		return null;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public String toString(@Nullable String def) {
		return def;
	}

	@Override
	public long toLong(long def) {
		return def;
	}

	@Override
	public int toInt(int def) {
		return def;
	}

	@Override
	public short toShort(short def) {
		return def;
	}

	@Override
	public byte toByte(byte def) {
		return def;
	}

	@Override
	public float toFloat(float def) {
		return def;
	}

	@Override
	public double toDouble(double def) {
		return def;
	}

	@Override
	public char toChar(char def) {
		return def;
	}

	@Override
	public boolean toBoolean(boolean def) {
		return def;
	}

	@Override
	public Number toNumber() {
		return null;
	}

	@Override
	public Document toDocument() {
		return null;
	}

	@Override
	public Bundle toBundle() {
		return null;
	}

	@Override
	public <T> T toInstance(@Nonnull Class<T> classOfT) {
		return null;
	}

	@Nonnull
	@Override
	public String toJson() {
		return "null";
	}

	@Nonnull
	@Override
	public String toPrettyJson() {
		return "null";
	}
}
