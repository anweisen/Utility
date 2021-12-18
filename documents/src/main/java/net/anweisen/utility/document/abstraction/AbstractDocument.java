package net.anweisen.utility.document.abstraction;

import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.IEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractDocument implements Document {

	public static void throwUneditable() {
		throw new IllegalStateException("This Document cannot be edited");
	}

	protected final AtomicBoolean editable;

	public AbstractDocument(boolean editable) {
		this(new AtomicBoolean(editable));
	}

	public AbstractDocument(@Nonnull AtomicBoolean editable) {
		this.editable = editable;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean canEdit() {
		return editable.get();
	}

	@Nonnull
	@Override
	public Document markUneditable() {
		editable.set(false);
		return this;
	}

	@Nonnull
	@Override
	public Document set(@Nonnull String path, @Nullable Object value) {
		if (!canEdit()) throwUneditable();
		set0(path, value);
		return this;
	}

	protected abstract void set0(@Nonnull String path, @Nullable Object value);

	@Nonnull
	@Override
	public Document remove(@Nonnull String path) {
		if (!canEdit()) throwUneditable();
		remove0(path);
		return this;
	}

	protected abstract void remove0(@Nonnull String path);

	@Nonnull
	@Override
	public Document clear() {
		if (!canEdit()) throwUneditable();
		clear0();
		return this;
	}

	protected abstract void clear0();

	@Nonnull
	@Override
	public Collection<Object> values() {
		return toMap().values();
	}

	@Nonnull
	@Override
	public Collection<IEntry> entries() {
		return toEntryMap().values();
	}

	@Override
	public void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
		toMap().forEach(action);
	}

	@Override
	public void forEachEntry(@Nonnull BiConsumer<? super String, ? super IEntry> action) {
		toEntryMap().forEach(action);
	}
}
