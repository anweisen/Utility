package net.anweisen.utility.document;

import net.anweisen.utility.common.misc.FileUtils;
import net.anweisen.utility.document.wrapped.WrappedDocument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedWriter;
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
 *
 * @see Bundle
 * @see IEntry
 */
public interface Document extends JsonConvertable {

	/**
	 * Returns an immutable map containing all values of this document.
	 * All values will be unpacked to java objects.
	 *
	 * @return the unpacked values of this document as immutable map
	 */
	@Nonnull
	Map<String, Object> toMap();

	/**
	 * Returns an immutable map containing all values of this document.
	 * All values will be wrapped as {@link IEntry}.
	 *
	 * @return the values of this document wrapped as {@link IEntry} as immutable map
	 */
	@Nonnull
	Map<String, IEntry> toEntryMap();

	@Nonnull
	@Override
	String toJson();

	@Nonnull
	@Override
	String toPrettyJson();

	int size();

	boolean isEmpty();

	boolean contains(@Nonnull String path);

	/**
	 * @return the keys of this document as immutable collection
	 */
	@Nonnull
	Collection<String> keys();

	/**
	 * @return the unpacked values of this document as immutable collection
	 */
	@Nonnull
	Collection<Object> values();

	/**
	 * @return the values of this document wrapped as {@link IEntry} as immutable collection
	 */
	@Nonnull
	Collection<IEntry> entries();

	/**
	 * @param path the path of the target object
	 * @return the object at the given path wrapped as {@link IEntry}
	 */
	@Nonnull
	IEntry getEntry(@Nonnull String path);

	/**
	 * @param path the path of the target object
	 * @return the object at the given path as {@link Bundle} or a new created empty {@link Bundle} if {@code null}
	 *
	 * @throws IllegalStateException
	 *         If the object at the given path cannot be converted to a {@link Bundle}
	 */
	@Nonnull
	Bundle getBundle(@Nonnull String path);

	/**
	 * @param path the path of the target object
	 * @return the object at the given path as {@link Document} or a new created empty {@link Document} if {@code null}
	 *
	 * @throws IllegalStateException
	 *         If the object at the given path cannot be converted to a {@link Document}
	 */
	@Nonnull
	Document getDocument(@Nonnull String path);

	default String getString(@Nonnull String path) {
		return getEntry(path).toString();
	}

	default String getString(@Nonnull String path, @Nullable String def) {
		return getEntry(path).toString(def);
	}

	default long getLong(@Nonnull String path) {
		return getEntry(path).toLong();
	}

	default long getLong(@Nonnull String path, long def) {
		return getEntry(path).toLong(def);
	}

	default int getInt(@Nonnull String path) {
		return getEntry(path).toInt();
	}

	default int getInt(@Nonnull String path, int def) {
		return getEntry(path).toInt(def);
	}

	default short getShort(@Nonnull String path) {
		return getEntry(path).toShort();
	}

	default short getShort(@Nonnull String path, short def) {
		return getEntry(path).toShort(def);
	}

	default byte getByte(@Nonnull String path) {
		return getEntry(path).toByte();
	}

	default byte getByte(@Nonnull String path, byte def) {
		return getEntry(path).toByte(def);
	}

	default float getFloat(@Nonnull String path) {
		return getEntry(path).toFloat();
	}

	default float getFloat(@Nonnull String path, float def) {
		return getEntry(path).toFloat(def);
	}

	default double getDouble(@Nonnull String path) {
		return getEntry(path).toDouble();
	}

	default double getDouble(@Nonnull String path, double def) {
		return getEntry(path).toDouble(def);
	}

	default <E extends Enum<?>> E getEnum(@Nonnull String path, @Nonnull Class<E> enumClass) {
		return getEntry(path).toEnum(enumClass);
	}

	default <E extends Enum<?>> E getEnum(@Nonnull String path, @Nonnull E def) {
		return getEntry(path).toEnum(def);
	}

	/**
	 * @return whether this document can be edited
	 */
	boolean canEdit();

	@Nonnull
	Document markUneditable();

	@Nonnull
	default Document referenceUneditable() {
		return Documents.newWrappedDocument(this, false);
	}

	/**
	 * Sets the entry at given path to the given object.
	 * Setting something to {@code null} has the same effect as {@link #remove(String) removing} it.
	 *
	 * @param path the path of the target object
	 * @param value the new value
	 *
	 * @throws IllegalStateException
	 *         If this document cannot {@link #canEdit() be edited}
	 */
	@Nonnull
	Document set(@Nonnull String path, @Nullable Object value);

	/**
	 * Removes the entry at the given path.
	 * This has the same effect as {@link #set(String, Object) setting} it to {@code null}.
	 *
	 * @param path the path of the target object
	 *
	 * @throws IllegalStateException
	 *         If this document cannot {@link #canEdit() be edited}
	 */
	@Nonnull
	Document remove(@Nonnull String path);

	/**
	 * Clears all entries of this document
	 *
	 * @throws IllegalStateException
	 *         If this document cannot {@link #canEdit() be edited}
	 */
	@Nonnull
	Document clear();

	void forEach(@Nonnull BiConsumer<? super String, ? super Object> action);

	void forEachEntry(@Nonnull BiConsumer<? super String, ? super IEntry> action);

	void write(@Nonnull Writer writer);

	default void saveToFile(@Nonnull Path file) throws IOException {
		BufferedWriter writer = FileUtils.newBufferedWriter(file);
		write(writer);
		writer.flush();
		writer.close();
	}

	default void saveToFile(@Nonnull File file) throws IOException {
		BufferedWriter writer = FileUtils.newBufferedWriter(file);
		write(writer);
		writer.flush();
		writer.close();
	}
}
