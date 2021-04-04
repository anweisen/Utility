package net.anweisen.utilities.commons.config.document.wrapper;


import net.anweisen.utilities.commons.config.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class DocumentWrapper implements Document {

	protected final Document document;

	public DocumentWrapper(@Nonnull Document document) {
		this.document = document;
	}

	@Override
	public boolean isReadonly() {
		return document.isReadonly();
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		return document.getDocument(path);
	}

	@Nonnull
	@Override
	public List<Document> getDocumentList(@Nonnull String path) {
		return document.getDocumentList(path);
	}

	@Nonnull
	@Override
	public <T> List<T> getSerializableList(@Nonnull String path, @Nonnull Class<T> classOfT) {
		return document.getSerializableList(path, classOfT);
	}

	@Nonnull
	@Override
	public Document set(@Nonnull String path, @Nullable Object value) {
		return document.set(path, value);
	}

	@Nonnull
	@Override
	public Document clear() {
		return document.clear();
	}

	@Nonnull
	@Override
	public Document remove(@Nonnull String path) {
		return document.remove(path);
	}

	@Override
	public void write(@Nonnull Writer writer) throws IOException {
		document.write(writer);
	}

	@Override
	public void save(@Nonnull File file) throws IOException {
		document.save(file);
	}

	@Nonnull
	@Override
	public String toJson() {
		return document.toJson();
	}

	@Nullable
	@Override
	public Object getObject(@Nonnull String path) {
		return document.getObject(path);
	}

	@Nonnull
	@Override
	public Object getObject(@Nonnull String path, @Nonnull Object def) {
		return document.getObject(path, def);
	}

	@Nullable
	@Override
	public String getString(@Nonnull String path) {
		return document.getString(path);
	}

	@Nonnull
	@Override
	public String getString(@Nonnull String path, @Nonnull String def) {
		return document.getString(path, def);
	}

	@Override
	public char getChar(@Nonnull String path) {
		return document.getChar(path);
	}

	@Override
	public char getChar(@Nonnull String path, char def) {
		return document.getChar(path, def);
	}

	@Override
	public long getLong(@Nonnull String path) {
		return document.getLong(path);
	}

	@Override
	public long getLong(@Nonnull String path, long def) {
		return document.getLong(path, def);
	}

	@Override
	public int getInt(@Nonnull String path) {
		return document.getInt(path);
	}

	@Override
	public int getInt(@Nonnull String path, int def) {
		return document.getInt(path, def);
	}

	@Override
	public short getShort(@Nonnull String path) {
		return document.getShort(path);
	}

	@Override
	public short getShort(@Nonnull String path, short def) {
		return document.getShort(path, def);
	}

	@Override
	public byte getByte(@Nonnull String path) {
		return document.getByte(path);
	}

	@Override
	public byte getByte(@Nonnull String path, byte def) {
		return document.getByte(path, def);
	}

	@Override
	public float getFloat(@Nonnull String path) {
		return document.getFloat(path);
	}

	@Override
	public float getFloat(@Nonnull String path, float def) {
		return document.getFloat(path, def);
	}

	@Override
	public double getDouble(@Nonnull String path) {
		return document.getDouble(path);
	}

	@Override
	public double getDouble(@Nonnull String path, double def) {
		return document.getDouble(path, def);
	}

	@Override
	public boolean getBoolean(@Nonnull String path) {
		return document.getBoolean(path);
	}

	@Override
	public boolean getBoolean(@Nonnull String path, boolean def) {
		return document.getBoolean(path, def);
	}

	@Nonnull
	@Override
	public List<String> getStringList(@Nonnull String path) {
		return document.getStringList(path);
	}

	@Nonnull
	@Override
	public <T> List<T> getList(@Nonnull String path, @Nonnull Function<String, ? extends T> mapper) {
		return document.getList(path, mapper);
	}

	@Nonnull
	@Override
	public <E extends Enum<E>> List<E> getEnumList(@Nonnull String path, @Nonnull Class<E> classOfEnum) {
		return document.getEnumList(path, classOfEnum);
	}

	@Nonnull
	@Override
	public List<UUID> getUUIDList(@Nonnull String path) {
		return document.getUUIDList(path);
	}

	@Nonnull
	@Override
	public List<Character> getCharacterList(@Nonnull String path) {
		return document.getCharacterList(path);
	}

	@Nonnull
	@Override
	public List<Byte> getByteList(@Nonnull String path) {
		return document.getByteList(path);
	}

	@Nonnull
	@Override
	public List<Short> getShortList(@Nonnull String path) {
		return document.getShortList(path);
	}

	@Nonnull
	@Override
	public List<Integer> getIntegerList(@Nonnull String path) {
		return document.getIntegerList(path);
	}

	@Nonnull
	@Override
	public List<Long> getLongList(@Nonnull String path) {
		return document.getLongList(path);
	}

	@Nonnull
	@Override
	public List<Float> getFloatList(@Nonnull String path) {
		return document.getFloatList(path);
	}

	@Nonnull
	@Override
	public List<Double> getDoubleList(@Nonnull String path) {
		return document.getDoubleList(path);
	}

	@Nullable
	@Override
	public UUID getUUID(@Nonnull String path) {
		return document.getUUID(path);
	}

	@Nonnull
	@Override
	public UUID getUUID(@Nonnull String path, @Nonnull UUID def) {
		return document.getUUID(path, def);
	}

	@Nullable
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull Class<E> classOfEnum) {
		return document.getEnum(path, classOfEnum);
	}

	@Nonnull
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull E def) {
		return document.getEnum(path, def);
	}

	@Nullable
	@Override
	public <T> T getSerializable(@Nonnull String path, @Nonnull Class<T> classOfT) {
		return document.getSerializable(path, classOfT);
	}

	@Nonnull
	@Override
	public <T> T getSerializable(@Nonnull String path, @Nonnull T def) {
		return document.getSerializable(path, def);
	}

	@Nullable
	@Override
	public Class<?> getClass(@Nonnull String path) {
		return document.getClass(path);
	}

	@Nonnull
	@Override
	public Class<?> getClass(@Nonnull String path, @Nonnull Class<?> def) {
		return document.getClass(path, def);
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return document.contains(path);
	}

	@Override
	public boolean isEmpty() {
		return document.isEmpty();
	}

	@Override
	public int size() {
		return document.size();
	}

	@Nonnull
	@Override
	public Map<String, Object> values() {
		return document.values();
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return document.keys();
	}

	@Override
	public void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
		document.forEach(action);
	}

	@Nonnull
	@Override
	public Document readonly() {
		return document.readonly();
	}

	@Nullable
	@Override
	public Document getParent() {
		return document.getParent();
	}

	@Nonnull
	@Override
	public Document getRoot() {
		return document.getRoot();
	}
}
