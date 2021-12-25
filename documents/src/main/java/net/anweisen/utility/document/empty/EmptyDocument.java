package net.anweisen.utility.document.empty;

import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.IEntry;
import net.anweisen.utility.document.abstraction.DocumentHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class EmptyDocument implements Document {

	public static final EmptyDocument INSTANCE = new EmptyDocument();

	public static final String JSON = "{}";

	private static final Map<String, Object> map = Collections.emptyMap();
	private static final Map<String, IEntry> entryMap = Collections.emptyMap();
	private static final Collection<String> keys = Collections.emptyList();
	private static final Collection<Object> values = Collections.emptyList();
	private static final Collection<IEntry> entries = Collections.emptyList();

	@Nonnull
	@Override
	public Map<String, Object> toMap() {
		return map;
	}

	@Nonnull
	@Override
	public Map<String, IEntry> toEntryMap() {
		return entryMap;
	}

	@Nonnull
	@Override
	public String toJson() {
		return JSON;
	}

	@Nonnull
	@Override
	public String toPrettyJson() {
		return JSON;
	}

	@Override
	public <T> T toInstance(@Nonnull Class<T> classOfT) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return false;
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return keys;
	}

	@Nonnull
	@Override
	public Collection<Object> values() {
		return values;
	}

	@Nonnull
	@Override
	public Collection<IEntry> entries() {
		return entries;
	}

	@Nonnull
	@Override
	public IEntry getEntry(@Nonnull String path) {
		return EmptyEntry.INSTANCE;
	}

	@Nonnull
	@Override
	public Bundle getBundle(@Nonnull String path) {
		return EmptyBundle.INSTANCE;
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		return EmptyDocument.INSTANCE;
	}

	@Nonnull
	@Override
	public Document set(@Nonnull String path, @Nullable Object value) {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Nonnull
	@Override
	public Document set(@Nonnull Object values) {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Nonnull
	@Override
	public Document remove(@Nonnull String path) {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Nonnull
	@Override
	public Document clear() {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Override
	public boolean canEdit() {
		return false;
	}

	@Nonnull
	@Override
	public Document markUneditable() {
		return this;
	}

	@Override
	public void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
	}

	@Override
	public void forEachEntry(@Nonnull BiConsumer<? super String, ? super IEntry> action) {
	}

	@Override
	public void write(@Nonnull Writer writer) {
	}

	@Override
	public String toString() {
		return toJson();
	}
}
