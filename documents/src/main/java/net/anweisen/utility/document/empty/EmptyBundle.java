package net.anweisen.utility.document.empty;

import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.IEntry;
import net.anweisen.utility.document.abstraction.DocumentHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class EmptyBundle implements Bundle {

	public static final EmptyBundle INSTANCE = new EmptyBundle();

	public static final String JSON = "[]";

	private static final Object[] array = new Object[0];
	private static final List<Object> list = Collections.emptyList();
	private static final Collection<IEntry> entries = Collections.emptyList();

	@Nonnull
	@Override
	public Object[] toArray() {
		return array;
	}

	@Nonnull
	@Override
	public List<Object> toList() {
		return list;
	}

	@Nonnull
	@Override
	public <T> List<T> toInstances(@Nonnull Class<T> classOfT) {
		return Collections.emptyList();
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
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Nonnull
	@Override
	public Collection<IEntry> entries() {
		return entries;
	}

	@Nonnull
	@Override
	public Bundle set(int index, @Nullable Object value) {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Nonnull
	@Override
	public Bundle remove(int index) {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Nonnull
	@Override
	public Bundle clear() {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Nonnull
	@Override
	public Bundle add(@Nullable Object value) {
		DocumentHelper.throwUneditable();
		return this;
	}

	@Nonnull
	@Override
	public IEntry getEntry(int index) {
		throw new IllegalArgumentException("Cannot get entry at index " + index + " for size " + size());
	}

	@Override
	public boolean canEdit() {
		return false;
	}

	@Nonnull
	@Override
	public Bundle markUneditable() {
		return this;
	}

	@Override
	public void forEach(@Nonnull Consumer<? super Object> action) {
	}

	@Override
	public void forEachEntry(@Nonnull Consumer<? super IEntry> action) {
	}

	@Override
	public void write(@Nonnull Writer writer) {
	}
}
