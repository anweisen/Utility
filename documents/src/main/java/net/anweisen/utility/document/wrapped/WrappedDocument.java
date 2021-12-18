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
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface WrappedDocument extends Document {

	@Nonnull
	Document getTargetDocument();

	@Nonnull
	@Override
	default Map<String, Object> toMap() {
		return getTargetDocument().toMap();
	}

	@Nonnull
	@Override
	default Map<String, IEntry> toEntryMap() {
		return getTargetDocument().toEntryMap();
	}

	@Nonnull
	@Override
	default String toJson() {
		return getTargetDocument().toJson();
	}

	@Nonnull
	@Override
	default String toPrettyJson() {
		return getTargetDocument().toPrettyJson();
	}

	@Override
	default int size() {
		return getTargetDocument().size();
	}

	@Override
	default boolean isEmpty() {
		return getTargetDocument().isEmpty();
	}

	@Override
	default boolean contains(@Nonnull String path) {
		return getTargetDocument().contains(path);
	}

	@Nonnull
	@Override
	default Collection<String> keys() {
		return getTargetDocument().keys();
	}

	@Nonnull
	@Override
	default Collection<Object> values() {
		return getTargetDocument().values();
	}

	@Nonnull
	@Override
	default Collection<IEntry> entries() {
		return getTargetDocument().entries();
	}

	@Nonnull
	@Override
	default IEntry getEntry(@Nonnull String path) {
		return getTargetDocument().getEntry(path);
	}

	@Nonnull
	@Override
	default Bundle getBundle(@Nonnull String path) {
		return getTargetDocument().getBundle(path);
	}

	@Nonnull
	@Override
	default Document getDocument(@Nonnull String path) {
		return getTargetDocument().getDocument(path);
	}

	@Override
	default String getString(@Nonnull String path) {
		return getTargetDocument().getString(path);
	}

	@Override
	default String getString(@Nonnull String path, @Nullable String def) {
		return getTargetDocument().getString(path, def);
	}

	@Override
	default long getLong(@Nonnull String path) {
		return getTargetDocument().getLong(path);
	}

	@Override
	default long getLong(@Nonnull String path, long def) {
		return getTargetDocument().getLong(path, def);
	}

	@Override
	default int getInt(@Nonnull String path) {
		return getTargetDocument().getInt(path);
	}

	@Override
	default int getInt(@Nonnull String path, int def) {
		return getTargetDocument().getInt(path, def);
	}

	@Override
	default short getShort(@Nonnull String path) {
		return getTargetDocument().getShort(path);
	}

	@Override
	default short getShort(@Nonnull String path, short def) {
		return getTargetDocument().getShort(path, def);
	}

	@Override
	default byte getByte(@Nonnull String path) {
		return getTargetDocument().getByte(path);
	}

	@Override
	default byte getByte(@Nonnull String path, byte def) {
		return getTargetDocument().getByte(path, def);
	}

	@Override
	default float getFloat(@Nonnull String path) {
		return getTargetDocument().getFloat(path);
	}

	@Override
	default float getFloat(@Nonnull String path, float def) {
		return getTargetDocument().getFloat(path, def);
	}

	@Override
	default double getDouble(@Nonnull String path) {
		return getTargetDocument().getDouble(path);
	}

	@Override
	default double getDouble(@Nonnull String path, double def) {
		return getTargetDocument().getDouble(path, def);
	}

	@Override
	default <E extends Enum<?>> E getEnum(@Nonnull String path, @Nonnull Class<E> enumClass) {
		return getTargetDocument().getEnum(path, enumClass);
	}

	@Override
	default <E extends Enum<?>> E getEnum(@Nonnull String path, @Nonnull E def) {
		return getTargetDocument().getEnum(path, def);
	}

	@Override
	default boolean canEdit() {
		return getTargetDocument().canEdit();
	}

	@Nonnull
	@Override
	default Document markUneditable() {
		return getTargetDocument().markUneditable();
	}

	@Nonnull
	@Override
	default Document referenceUneditable() {
		return getTargetDocument().referenceUneditable();
	}

	@Nonnull
	@Override
	default Document set(@Nonnull String path, @Nullable Object value) {
		return getTargetDocument().set(path, value);
	}

	@Nonnull
	@Override
	default Document remove(@Nonnull String path) {
		return getTargetDocument().remove(path);
	}

	@Nonnull
	@Override
	default Document clear() {
		return getTargetDocument().clear();
	}

	@Override
	default void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
		getTargetDocument().forEach(action);
	}

	@Override
	default void forEachEntry(@Nonnull BiConsumer<? super String, ? super IEntry> action) {
		getTargetDocument().forEachEntry(action);
	}

	@Override
	default void write(@Nonnull Writer writer) {
		getTargetDocument().write(writer);
	}

	@Override
	default void saveToFile(@Nonnull Path file) throws IOException {
		getTargetDocument().saveToFile(file);
	}

	@Override
	default void saveToFile(@Nonnull File file) throws IOException {
		getTargetDocument().saveToFile(file);
	}
}
