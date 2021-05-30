package net.anweisen.utilities.commons.config;

import net.anweisen.utilities.commons.common.WrappedException;
import net.anweisen.utilities.commons.config.document.EmptyDocument;
import net.anweisen.utilities.commons.config.document.GsonDocument;
import net.anweisen.utilities.commons.misc.FileUtils;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Document extends Config, Json {

	/**
	 * Gets the document located at the given path.
	 * If there is no document assigned to this path,
	 * a new document is being created and assigned to this path.
	 *
	 * @return the document assigned to this path
	 */
	@Nonnull
	Document getDocument(@Nonnull String path);

	/**
	 * Returns the list of documents located at the given path.
	 * The returned list will not contain any null elements.
	 * If there are no documents, the list will be empty.
	 * Elements which are no documents in the original list will be skipped.
	 * If an element is {@code null} an empty document will be added
	 * if this document is not {@link #isReadonly() readonly}.
	 *
	 * @return the list of documents assigned to this path
	 */
	@Nonnull
	List<Document> getDocumentList(@Nonnull String path);

	@Nonnull
	<T> List<T> getSerializableList(@Nonnull String path, @Nonnull Class<T> classOfT);

	@Nullable
	<T> T getSerializable(@Nonnull String path, @Nonnull Class<T> classOfT);

	@Nonnull
	<T> T getSerializable(@Nonnull String path, @Nonnull T def);

	@Nonnull
	<K, V> Map<K, V> mapDocuments(@Nonnull Function<? super String, ? extends K> keyMapper, @Nonnull Function<? super Document, ? extends V> valueMapper);

	/**
	 * Returns the parent document of this document.
	 * If {@code this} is the {@link #getRoot() root} this method will return {@code null}.
	 *
	 * @return the parent document of this document
	 */
	@Nullable
	Document getParent();

	/**
	 * Returns the root document of this document.
	 * If the {@link #getParent() parent} is {@code null} this will return {@code this}.
	 *
	 * @return the root document of this document
	 */
	@Nonnull
	Document getRoot();

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	@Override
	Document set(@Nonnull String path, @Nullable Object value);

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	@Override
	Document clear();

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	@Override
	Document remove(@Nonnull String path);

	/**
	 * {@inheritDoc}
	 */
	@Nonnull
	@Override
	@CheckReturnValue
	Document readonly();

	@Nonnull
	Map<String, Document> children();

	boolean isDocument(@Nonnull String path);

	boolean hasChildren(@Nonnull String path);

	void write(@Nonnull Writer writer) throws IOException;

	void saveToFile(@Nonnull File file) throws IOException;

	@Nonnull
	@CheckReturnValue
	default FileDocument asFileDocument(@Nonnull File file) {
		return (this instanceof FileDocument) ? (FileDocument) this :  FileDocument.wrap(this, file);
	}

	/**
	 * @return a new empty and immutable document
	 *
	 * @see EmptyDocument
	 */
	@Nonnull
	@CheckReturnValue
	static Document empty() {
		return new EmptyDocument();
	}

	@Nonnull
	@CheckReturnValue
	static Document readFile(@Nonnull Class<? extends Document> classOfDocument, @Nonnull File file) {
		try {
			if (file.exists()) {
				Constructor<? extends Document> constructor = classOfDocument.getConstructor(File.class);
				return constructor.newInstance(file);
			} else {
				Constructor<? extends Document> constructor = classOfDocument.getConstructor();
				return constructor.newInstance();
			}
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
			throw new UnsupportedOperationException(classOfDocument.getName() + " does not support File creation");
		} catch (InvocationTargetException ex) {
			throw new WrappedException(ex);
		}
	}

	/**
	 * @return a json document parsed by the input
	 *
	 * @see GsonDocument
	 */
	@Nonnull
	@CheckReturnValue
	static Document parseJson(@Nonnull String jsonInput) {
		return new GsonDocument(jsonInput);
	}

	@Nonnull
	@CheckReturnValue
	static Document readJsonFile(@Nonnull File file) {
		return readFile(GsonDocument.class, file);
	}

	@Nonnull
	@CheckReturnValue
	static Document newJsonDocument() {
		return new GsonDocument();
	}

}
