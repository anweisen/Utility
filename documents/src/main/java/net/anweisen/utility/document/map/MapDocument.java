package net.anweisen.utility.document.map;

import net.anweisen.utility.common.misc.CollectionUtils;
import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.Documents;
import net.anweisen.utility.document.IEntry;
import net.anweisen.utility.document.abstraction.AbstractDocument;
import net.anweisen.utility.document.gson.GsonHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MapDocument extends AbstractDocument {

	protected Map<Object, Object> properties;

	public MapDocument(@Nonnull Properties properties, boolean editable) {
		super(editable);
		this.properties = properties;
	}

	public MapDocument() {
		this(new Properties(), true);
	}

	public MapDocument(@Nonnull Map<Object, Object> properties) {
		super(true);
		this.properties = properties;
	}

	public MapDocument(@Nonnull Map<Object, Object> properties, @Nonnull AtomicBoolean editable) {
		super(editable);
		this.properties = properties;
	}

	@Nonnull
	@Override
	public Map<String, Object> toMap() {
		return CollectionUtils.convertEntries(properties.entrySet(), String::valueOf, Function.identity());
	}

	@Nonnull
	@Override
	public Map<String, IEntry> toEntryMap() {
		return CollectionUtils.convertEntries(properties.entrySet(), String::valueOf, MapEntry::new);
	}

	@Nonnull
	@Override
	public String toJson() {
		return GsonHelper.DEFAULT_GSON.toJson(GsonHelper.toJsonElement(toEntryMap()));
	}

	@Nonnull
	@Override
	public String toPrettyJson() {
		return GsonHelper.PRETTY_GSON.toJson(GsonHelper.toJsonElement(toEntryMap()));
	}

	@Override
	public int size() {
		return properties.size();
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return properties.containsKey(path);
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return CollectionUtils.convertCollection(properties.keySet(), String::valueOf);
	}

	@Nonnull
	@Override
	public IEntry getEntry(@Nonnull String path) {
		return new MapEntry(properties.get(path));
	}

	@Nonnull
	@Override
	public Bundle getBundle(@Nonnull String path) {
		Object value = properties.computeIfAbsent(path, key -> new MapBundle());
		if (value instanceof Bundle) return (Bundle) value;
		if (value instanceof String) return Documents.newJsonBundle((String) value);
		if (!(value instanceof Collection)) throw new IllegalStateException("'" + path + "' is not a Collection: " + value.getClass().getName());
		value = new MapBundle((Collection<?>) value);
		return (Bundle) value;
	}

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	public Document getDocument(@Nonnull String path) {
		Object value = properties.computeIfAbsent(path, key -> new MapDocument());
		if (value instanceof Document) return (Document) value;
		if (value instanceof String) return Documents.newJsonDocument((String) value);
		if (!(value instanceof Map)) throw new IllegalStateException("'" + path + "' is not a Map or Document: " + value.getClass().getName());
		value = new MapDocument((Map<Object, Object>) value, editable);
		return (Document) value;
	}

	@Override
	protected void set0(@Nonnull String path, @Nullable Object value) {
		properties.put(path, value);
	}

	@Override
	protected void remove0(@Nonnull String path) {
		properties.remove(path);
	}

	@Override
	protected void clear0() {
		properties.clear();
	}

	@Override
	public void write(@Nonnull Writer writer) {
		throw new UnsupportedOperationException("Cannot write a MapDocument");
	}

	@Nonnull
	public Map<Object, Object> getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Nonnull
	@Override
	public Document clone() {
		return new MapDocument(new LinkedHashMap<>(properties));
	}
}
