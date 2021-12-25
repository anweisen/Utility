package net.anweisen.utility.document.bson;

import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.Documents;
import net.anweisen.utility.document.IEntry;
import net.anweisen.utility.document.abstraction.DocumentHelper;
import org.bson.BsonNull;
import org.bson.BsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class BsonEntry implements IEntry {

	protected Object value;
	protected BsonValue bsonValue;

	public BsonEntry(@Nullable Object value) {
		this.bsonValue = value instanceof BsonValue ? (BsonValue) value : value == null ? BsonNull.VALUE : null;
		this.value = value;
	}

	@Override
	public boolean isNull() {
		return value != null;
	}

	@Override
	public boolean isBundle() {
		return bsonValue != null && bsonValue.isArray();
	}

	@Override
	public boolean isDocument() {
		return value instanceof org.bson.Document || value instanceof org.bson.BsonDocument;
	}

	@Override
	public boolean isNumber() {
		return value instanceof Number || bsonValue != null && bsonValue.isNumber();
	}

	@Override
	public boolean isBoolean() {
		return value instanceof Boolean || bsonValue != null && bsonValue.isBoolean();
	}

	@Override
	public boolean isChar() {
		return value instanceof String && ((String)value).length() == 1
			|| value instanceof Character
			|| bsonValue != null && bsonValue.isString() && bsonValue.asString().getValue().length() == 1;
	}

	@Override
	public Object toObject() {
		return DocumentHelper.toObject(this, value);
	}

	@Override
	public String toString(@Nullable String def) {
		return isNull() ? def :
			bsonValue == null ? value.toString() :
			bsonValue.isString() ? bsonValue.asString().getValue() :
			bsonValue.isSymbol() ? bsonValue.asSymbol().getSymbol() :
			toJson();
	}

	@Override
	public String toString() {
		return toString(null);
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
		if (!isChar()) DocumentHelper.throwNotChar();
		return toString().charAt(0);
	}

	@Override
	public boolean toBoolean(boolean def) {
		return isNull() ? def :
			value instanceof Boolean ? (boolean) value :
			value instanceof String ? Boolean.parseBoolean((String) value) :
			bsonValue == null ? false :
			bsonValue.isBoolean() ? bsonValue.asBoolean().getValue() :
			bsonValue.isString() ? Boolean.parseBoolean(bsonValue.asString().getValue())
			: def;
	}

	@Override
	public Number toNumber() {
		if (!isNumber()) DocumentHelper.throwNotNumber();
		return value instanceof Number ? (Number) value :
			bsonValue.isInt32() ? bsonValue.asInt32().getValue() :
			bsonValue.isInt64() ? bsonValue.asInt64().getValue() :
			bsonValue.isDecimal128() ? bsonValue.asDecimal128().getValue() :
			bsonValue.isDouble() ? bsonValue.asDouble().getValue() :
			bsonValue.asNumber().doubleValue();
	}

	@Override
	public Document toDocument() {
		if (!isDocument()) DocumentHelper.throwNotDocument();
		return new BsonDocument(value instanceof org.bson.Document ? (org.bson.Document) value : org.bson.Document.parse(bsonValue.asDocument().toJson()), new AtomicBoolean(false));
	}

	@Override
	public Bundle toBundle() {
		if (!isBundle()) DocumentHelper.throwNotBundle();
		return new BsonBundle(bsonValue.asArray(), new AtomicBoolean(false));
	}

	@Override
	public UUID toUniqueId() {
		if (isNull()) return null;
		if (value instanceof UUID) return (UUID) value;
		if (value instanceof String) return UUID.fromString(String.valueOf(value));

		DocumentHelper.throwNotUniqueId();
		return null;
	}

	@Override
	public <T> T toInstance(@Nonnull Class<T> classOfT) {
		if (classOfT.isInstance(value))
			return classOfT.cast(value);

		return Documents.newJsonEntry(value).toInstance(classOfT);
	}

	@Nonnull
	@Override
	public String toJson() {
		return BsonHelper.toJson(value, false);
	}

	@Nonnull
	@Override
	public String toPrettyJson() {
		return BsonHelper.toJson(value, true);
	}

}
