package net.anweisen.utility.document.wrapped;

import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.IEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface WrappedBundle extends Bundle {

	@Nonnull
	Bundle getTargetBundle();

	@Nonnull
	@Override
	default Object[] toArray() {
		return getTargetBundle().toArray();
	}

	@Nonnull
	@Override
	default List<Object> toList() {
		return getTargetBundle().toList();
	}

	@Nonnull
	@Override
	default String toJson() {
		return getTargetBundle().toJson();
	}

	@Nonnull
	@Override
	default String toPrettyJson() {
		return getTargetBundle().toPrettyJson();
	}

	@Override
	default int size() {
		return getTargetBundle().size();
	}

	@Override
	default boolean isEmpty() {
		return getTargetBundle().isEmpty();
	}

	@Nonnull
	@Override
	default Collection<IEntry> entries() {
		return getTargetBundle().entries();
	}

	@Nonnull
	@Override
	default IEntry getEntry(int index) {
		return getTargetBundle().getEntry(index);
	}

	@Override
	default Document getDocument(int index) {
		return getTargetBundle().getDocument(index);
	}

	@Override
	default Bundle getBundle(int index) {
		return getTargetBundle().getBundle(index);
	}

	@Nonnull
	@Override
	default <T> List<T> toInstances(@Nonnull Class<T> classOfT) {
		return getTargetBundle().toInstances(classOfT);
	}

	@Nonnull
	@Override
	default List<String> toStrings() {
		return getTargetBundle().toStrings();
	}

	@Nonnull
	@Override
	default List<Document> toDocuments() {
		return getTargetBundle().toDocuments();
	}

	@Nonnull
	@Override
	default List<Bundle> toBundles() {
		return getTargetBundle().toBundles();
	}

	@Nonnull
	@Override
	default List<Long> toLongs() {
		return getTargetBundle().toLongs();
	}

	@Nonnull
	@Override
	default List<Integer> toInts() {
		return getTargetBundle().toInts();
	}

	@Nonnull
	@Override
	default List<Short> toShorts() {
		return getTargetBundle().toShorts();
	}

	@Nonnull
	@Override
	default List<Byte> toBytes() {
		return getTargetBundle().toBytes();
	}

	@Nonnull
	@Override
	default List<Double> toDoubles() {
		return getTargetBundle().toDoubles();
	}

	@Nonnull
	@Override
	default List<Float> toFloats() {
		return getTargetBundle().toFloats();
	}

	@Nonnull
	@Override
	default List<Boolean> toBooleans() {
		return getTargetBundle().toBooleans();
	}

	@Nonnull
	@Override
	default Bundle set(int index, @Nullable Object value) {
		return getTargetBundle().set(index, value);
	}

	@Nonnull
	@Override
	default Bundle remove(int index) {
		return getTargetBundle().remove(index);
	}

	@Nonnull
	@Override
	default Bundle clear() {
		return getTargetBundle().clear();
	}

	@Nonnull
	@Override
	default Bundle add(@Nullable Object value) {
		return getTargetBundle().add(value);
	}

	@Nonnull
	@Override
	default Bundle addAll(@Nonnull Object... values) {
		return getTargetBundle().addAll(values);
	}

	@Nonnull
	@Override
	default Bundle addAll(@Nonnull Iterable<?> values) {
		return getTargetBundle().addAll(values);
	}

	@Override
	default boolean canEdit() {
		return getTargetBundle().canEdit();
	}

	@Nonnull
	@Override
	default Bundle markUneditable() {
		return getTargetBundle().markUneditable();
	}

	@Override
	default void forEach(@Nonnull Consumer<? super Object> action) {
		getTargetBundle().forEach(action);
	}

	@Override
	default void forEachEntry(@Nonnull Consumer<? super IEntry> action) {
		getTargetBundle().forEachEntry(action);
	}

	@Override
	default void write(@Nonnull Writer writer) {
		getTargetBundle().write(writer);
	}

	@Override
	default void saveToFile(@Nonnull Path file) throws IOException {
		getTargetBundle().saveToFile(file);
	}

	@Override
	default void saveToFile(@Nonnull File file) throws IOException {
		getTargetBundle().saveToFile(file);
	}
}
