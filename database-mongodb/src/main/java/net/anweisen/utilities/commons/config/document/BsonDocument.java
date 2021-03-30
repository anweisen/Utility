package net.anweisen.utilities.commons.config.document;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.misc.FileUtils;
import org.bson.BsonArray;
import org.bson.BsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class BsonDocument extends AbstractDocument {

	protected org.bson.Document bsonDocument;

	public BsonDocument(@Nonnull File file) throws IOException {
		this(FileUtils.newBufferedReader(file));
	}

	public BsonDocument(@Nonnull Reader reader) {
		BufferedReader buffered = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
		StringBuilder content = new StringBuilder();
		buffered.lines().forEach(content::append);
		bsonDocument = org.bson.Document.parse(content.toString());
	}

	public BsonDocument(@Nonnull org.bson.Document bsonDocument) {
		this.bsonDocument = bsonDocument;
	}

	public BsonDocument(@Nonnull org.bson.Document bsonDocument, @Nonnull Document root, @Nullable Document parent) {
		super(root, parent);
		this.bsonDocument = bsonDocument;
	}

	public BsonDocument() {
		this(new org.bson.Document());
	}

	@Nonnull
	@Override
	public Document getDocument0(@Nonnull String path, @Nonnull Document root, @Nullable Document parent) {
		return new BsonDocument(bsonDocument.get(path, org.bson.Document.class), root, parent);
	}

	@Nonnull
	@Override
	public List<Document> getDocumentList(@Nonnull String path) {
		BsonArray array = bsonDocument.get(path, BsonArray.class);
		if (array == null) return new ArrayList<>();
		List<Document> documents = new ArrayList<>(array.size());
		for (BsonValue value : array) {
			if (!value.isDocument()) continue;
			String json = value.asDocument().toJson();
			org.bson.Document document = org.bson.Document.parse(json);
			documents.add(new BsonDocument(document, root, this));
		}
		return documents;
	}

	@Nullable
	@Override
	public String getString(@Nonnull String path) {
		return bsonDocument.getString(path);
	}

	@Override
	public long getLong(@Nonnull String path, long def) {
		Long value = bsonDocument.getLong(path);
		if (value == null) return def;
		return value;
	}

	@Override
	public int getInt(@Nonnull String path, int def) {
		Integer value = bsonDocument.getInteger(path);
		if (value == null) return def;
		return value;
	}

	@Override
	public short getShort(@Nonnull String path, short def) {
		Number value = bsonDocument.getInteger(path);
		if (value == null) return def;
		return value.shortValue();
	}

	@Override
	public byte getByte(@Nonnull String path, byte def) {
		Number value = bsonDocument.getInteger(path);
		if (value == null) return def;
		return value.byteValue();
	}

	@Override
	public double getDouble(@Nonnull String path, double def) {
		Double value = bsonDocument.getDouble(path);
		if (value == null) return def;
		return value;
	}

	@Override
	public float getFloat(@Nonnull String path, float def) {
		Double value = bsonDocument.getDouble(path);
		if (value == null) return def;
		return (float) (double) value;
	}

	@Override
	public boolean getBoolean(@Nonnull String path, boolean def) {
		try {
			Object value = bsonDocument.get(path);
			if (value instanceof Boolean) return (Boolean) value;
			if (value instanceof String) return Boolean.parseBoolean((String) value);
		} catch (Exception ex) {
		}
		return def;
	}

	@Nullable
	@Override
	public Object getObject(@Nonnull String path) {
		return bsonDocument.get(path);
	}

	@Nonnull
	@Override
	public List<String> getStringList(@Nonnull String path) {
		return bsonDocument.getList(path, String.class);
	}

	@Nullable
	@Override
	public UUID getUUID(@Nonnull String path) {
		try {
			Object value = bsonDocument.get(path);
			if (value instanceof UUID) return (UUID) value;
			if (value instanceof String) return UUID.fromString((String) value);
		} catch (Exception ex) {
		}
		return null;
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return bsonDocument.containsKey(path);
	}

	@Override
	public boolean isEmpty() {
		return bsonDocument.isEmpty();
	}

	@Override
	public int size() {
		return bsonDocument.size();
	}

	@Override
	public void clear0() {
		bsonDocument.clear();
	}

	@Override
	public void set0(@Nonnull String path, @Nullable Object value) {
		bsonDocument.put(path, value);
	}

	@Override
	public void remove0(@Nonnull String path) {
		bsonDocument.remove(path);
	}

	@Override
	public void write(@Nonnull Writer writer) throws IOException {
		String json = bsonDocument.toString();
		writer.write(json);
	}

	@Nonnull
	@Override
	public Map<String, Object> values() {
		return Collections.unmodifiableMap(bsonDocument);
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return bsonDocument.keySet();
	}

	@Override
	public void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
		bsonDocument.forEach(action);
	}

	@Nonnull
	@Override
	public String toJson() {
		return bsonDocument.toJson();
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Override
	public boolean isReadonly() {
		return false;
	}

}
