package net.anweisen.utilities.commons.config.document;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.misc.FileUtils;
import net.anweisen.utilities.commons.misc.PropertiesUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

/**
 * This document only supports basic objects like {@link Number numbers}, {@link String strings}, {@link Character characters} and {@link Boolean booleans}.
 * You may use more advanced documents which are fully supported like {@link GsonDocument}
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class PropertiesDocument extends AbstractDocument {

	protected final Properties properties;

	public PropertiesDocument(@Nonnull Properties properties) {
		this.properties = properties;
	}

	public PropertiesDocument(@Nonnull File file) throws IOException {
		properties = new Properties();
		properties.load(FileUtils.newBufferedReader(file));
	}

	@Nonnull
	@Override
	public Document getDocument0(@Nonnull String path, @Nonnull Document root, @Nullable Document parent) {
		throw new UnsupportedOperationException("PropertiesDocument.getDocument(String)");
	}

	@Nonnull
	@Override
	public List<Document> getDocumentList(@Nonnull String path) {
		throw new UnsupportedOperationException("PropertiesDocument.getDocumentList(String)");
	}

	@Nonnull
	@Override
	public List<String> getStringList(@Nonnull String path) {
		throw new UnsupportedOperationException("PropertiesDocument.getList(String)");
	}

	@Nullable
	@Override
	public Object getObject(@Nonnull String path) {
		return properties.get(path);
	}

	@Nullable
	@Override
	public String getString(@Nonnull String path) {
		return properties.getProperty(path);
	}

	@Override
	public long getLong(@Nonnull String path, long def) {
		try {
			return Long.parseLong(getString(path));
		} catch (Exception ex) {
			return def;
		}
	}

	@Override
	public int getInt(@Nonnull String path, int def) {
		try {
			return Integer.parseInt(getString(path));
		} catch (Exception ex) {
			return def;
		}
	}

	@Override
	public short getShort(@Nonnull String path, short def) {
		try {
			return Short.parseShort(getString(path));
		} catch (Exception ex) {
			return def;
		}
	}

	@Override
	public byte getByte(@Nonnull String path, byte def) {
		try {
			return Byte.parseByte(getString(path));
		} catch (Exception ex) {
			return def;
		}
	}

	@Override
	public float getFloat(@Nonnull String path, float def) {
		try {
			return Float.parseFloat(getString(path));
		} catch (Exception ex) {
			return def;
		}
	}

	@Override
	public double getDouble(@Nonnull String path, double def) {
		try {
			return Double.parseDouble(getString(path));
		} catch (Exception ex) {
			return def;
		}
	}

	@Override
	public boolean getBoolean(@Nonnull String path, boolean def) {
		if (!contains(path)) return def;
		return Boolean.parseBoolean(getString(path));
	}

	@Nullable
	@Override
	public UUID getUUID(@Nonnull String path) {
		try {
			return UUID.fromString(getString(path));
		} catch (Exception ex) {
			return null;
		}
	}

	@Nullable
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull Class<E> classOfEnum) {
		try {
			String name = getString(path);
			if (name == null) return null;
			return Enum.valueOf(classOfEnum, name);
		} catch (Throwable ex) {
			return null;
		}
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return properties.containsKey(path);
	}

	@Override
	public boolean isList(@Nonnull String path) {
		return false;
	}

	@Override
	public int size() {
		return properties.size();
	}

	@Nonnull
	@Override
	public Map<String, Object> values() {
		Map<String, Object> map = new LinkedHashMap<>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			map.put((String) entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return properties.stringPropertyNames();
	}

	@Override
	public void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
		values().forEach(action);
	}

	@Override
	public void set0(@Nonnull String path, @Nullable Object value) {
		properties.setProperty(path, String.valueOf(value));
	}

	@Override
	public void clear0() {
		properties.clear();
	}

	@Override
	public void remove0(@Nonnull String path) {
		properties.remove(path);
	}

	@Override
	public void write(@Nonnull Writer writer) throws IOException {
		properties.store(writer, null);
	}

	@Nonnull
	public Properties getProperties() {
		return properties;
	}

	@Nonnull
	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<>();
		PropertiesUtils.setProperties(properties, map);
		return new GsonDocument(map).toJson();
	}

	@Override
	public boolean isReadonly() {
		return false;
	}
}
