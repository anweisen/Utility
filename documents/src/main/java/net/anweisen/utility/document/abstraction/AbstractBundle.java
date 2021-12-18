package net.anweisen.utility.document.abstraction;

import net.anweisen.utility.common.misc.CollectionUtils;
import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.IEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.anweisen.utility.document.abstraction.DocumentHelper.throwOutOfBounds;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractBundle implements Bundle {

	public static void throwUneditable() {
		throw new IllegalStateException("This Bundle cannot be edited");
	}

	protected AtomicBoolean editable;

	public AbstractBundle(@Nonnull AtomicBoolean editable) {
		this.editable = editable;
	}

	public AbstractBundle(boolean editable) {
		this(new AtomicBoolean(editable));
	}

	@Nonnull
	@Override
	public Object[] toArray() {
		return toList().toArray();
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
	public Bundle markUneditable() {
		editable.set(false);
		return this;
	}

	@Nonnull
	@Override
	public Bundle set(int index, @Nullable Object value) {
		if (!canEdit()) throwUneditable();
		if (index >= size()) throwOutOfBounds(index, size());
		set0(index, value);
		return this;
	}

	protected abstract void set0(int index, @Nullable Object value);

	@Nonnull
	@Override
	public Bundle add(@Nullable Object value) {
		if (!canEdit()) throwUneditable();
		add0(value);
		return this;
	}

	protected abstract void add0(@Nullable Object value);

	@Nonnull
	@Override
	public Bundle remove(int index) {
		if (!canEdit()) throwUneditable();
		if (index >= size()) throwOutOfBounds(index, size());
		remove0(index);
		return this;
	}

	protected abstract void remove0(int index);

	@Nonnull
	@Override
	public Bundle clear() {
		if (!canEdit()) throwUneditable();
		clear0();
		return this;
	}

	protected abstract void clear0();

	@Nonnull
	@Override
	public IEntry getEntry(int index) {
		if (index >= size()) throwOutOfBounds(index, size());
		return getEntry0(index);
	}

	protected abstract IEntry getEntry0(int index);


	@Nonnull
	protected <T> List<T> convertEntries(@Nonnull Function<IEntry, T> mapper) {
		return CollectionUtils.convertCollection(entries(), mapper);
	}

	@Override
	public void forEach(@Nonnull Consumer<? super Object> action) {
		toList().forEach(action);
	}

	@Override
	public void forEachEntry(@Nonnull Consumer<? super IEntry> action) {
		entries().forEach(action);
	}
}
