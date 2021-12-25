package net.anweisen.utility.document;

import net.anweisen.utility.common.collection.WrappedException;
import net.anweisen.utility.common.misc.FileUtils;
import net.anweisen.utility.document.empty.EmptyBundle;
import net.anweisen.utility.document.empty.EmptyDocument;
import net.anweisen.utility.document.gson.GsonBundle;
import net.anweisen.utility.document.gson.GsonDocument;
import net.anweisen.utility.document.gson.GsonEntry;
import net.anweisen.utility.document.wrapped.StorableBundle;
import net.anweisen.utility.document.wrapped.StorableDocument;
import net.anweisen.utility.document.wrapped.WrappedBundle;
import net.anweisen.utility.document.wrapped.WrappedDocument;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Document
 * @see Bundle
 * @see IEntry
 */
public final class Documents {

	@Nonnull
	@CheckReturnValue
	public static Document emptyDocument() {
		return EmptyDocument.INSTANCE;
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle emptyBundle() {
		return EmptyBundle.INSTANCE;
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument() {
		return new GsonDocument();
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull String json) {
		return new GsonDocument(json);
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull Object value) {
		return new GsonDocument(value);
	}

	@Nullable
	@CheckReturnValue
	public static Document newJsonDocumentNullable(@Nullable Object object) {
		return object == null ? null : newJsonDocument(object);
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull String key, @Nonnull Object value) {
		return newJsonDocument().set(key, value);
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull Object... keysAndValues) {
		Document document = newJsonDocument();
		if (keysAndValues.length % 2 != 0) throw new IllegalArgumentException("Cannot create document of " + keysAndValues.length + " arguments");
		for (int i = 0; i < keysAndValues.length; i += 2) {
			document.set(String.valueOf(keysAndValues[i]), keysAndValues[i + 1]);
		}
		return document;
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull Reader reader) {
		return new GsonDocument(reader);
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull InputStream input) {
		return newJsonDocument(new InputStreamReader(input, StandardCharsets.UTF_8));
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull Path file) throws IOException {
		if (Files.exists(file))
			return new GsonDocument(FileUtils.newBufferedReader(file));
		return new GsonDocument();
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocument(@Nonnull File file) throws IOException {
		if (file.exists())
			return new GsonDocument(FileUtils.newBufferedReader(file));
		return new GsonDocument();
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocumentUnchecked(@Nonnull Path file) {
		try {
			return newJsonDocument(file);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	@Nonnull
	@CheckReturnValue
	public static Document newJsonDocumentUnchecked(@Nonnull File file) {
		try {
			return newJsonDocument(file);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle() {
		return new GsonBundle();
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(int initialSize) {
		return new GsonBundle(initialSize);
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(@Nonnull String json) {
		return new GsonBundle(json);
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(@Nonnull Object... values) {
		return newJsonBundle().addAll(values);
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(@Nonnull Iterable<?> values) {
		return newJsonBundle().addAll(values);
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(@Nonnull Reader reader) {
		return new GsonBundle(reader);
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(@Nonnull InputStream input) {
		return newJsonBundle(new InputStreamReader(input, StandardCharsets.UTF_8));
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(@Nonnull Path file) throws IOException {
		if (Files.exists(file))
			return new GsonBundle(FileUtils.newBufferedReader(file));
		return new GsonBundle();
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundle(@Nonnull File file) throws IOException {
		if (file.exists())
			return new GsonBundle(FileUtils.newBufferedReader(file));
		return new GsonBundle();
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundleUnchecked(@Nonnull Path file) {
		try {
			return newJsonBundle(file);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	@Nonnull
	@CheckReturnValue
	public static Bundle newJsonBundleUnchecked(@Nonnull File file) {
		try {
			return newJsonBundle(file);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	@Nonnull
	@CheckReturnValue
	public static IEntry newJsonEntry(@Nullable Object value) {
		return new GsonEntry(value);
	}

	@Nonnull
	@CheckReturnValue
	public static StorableDocument newStorableJsonDocument(@Nonnull Path file) throws IOException {
		return newStorableDocument(newJsonDocument(file), file);
	}

	@Nonnull
	@CheckReturnValue
	public static StorableBundle newStorableJsonBundle(@Nonnull Path file) throws IOException {
		return newStorableBundle(newJsonBundle(file), file);
	}

	@Nonnull
	@CheckReturnValue
	public static StorableDocument newStorableJsonDocumentUnchecked(@Nonnull Path file) {
		try {
			return newStorableJsonDocument(file);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	@Nonnull
	@CheckReturnValue
	public static StorableBundle newStorableJsonBundleUnchecked(@Nonnull Path file) {
		try {
			return newStorableJsonBundle(file);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	@Nonnull
	@CheckReturnValue
	public static StorableDocument newStorableDocument(@Nonnull Document document, @Nonnull Path file) {
		class DocumentClass implements WrappedDocument, StorableDocument {
			@Nonnull public Path getPath() { return file; }
			@Nonnull public File getFile() { return file.toFile(); }
			@Nonnull public Document getTargetDocument() { return document; }
			public void saveExceptionally() throws Exception { saveToFile(file); }
		}
		return new DocumentClass();
	}

	@Nonnull
	@CheckReturnValue
	public static StorableDocument newStorableDocument(@Nonnull Document document, @Nonnull File file) {
		return newStorableDocument(document, file.toPath());
	}

	@Nonnull
	@CheckReturnValue
	public static StorableBundle newStorableBundle(@Nonnull Bundle bundle, @Nonnull Path file) {
		class BundleClass implements WrappedBundle, StorableBundle {
			@Nonnull public Path getPath() { return file; }
			@Nonnull public File getFile() { return file.toFile(); }
			@Nonnull public Bundle getTargetBundle() { return bundle; }
			public void saveExceptionally() throws Exception { saveToFile(file); }
		}
		return new BundleClass();
	}

	@Nonnull
	@CheckReturnValue
	public static StorableBundle newStorableBundle(@Nonnull Bundle bundle, @Nonnull File file) {
		return newStorableBundle(bundle, file.toPath());
	}

	@Nonnull
	@CheckReturnValue
	public static WrappedDocument newWrappedDocument(@Nonnull Document document, @Nullable Boolean overwriteEditable) {
		return new WrappedDocument() {
			@Nonnull public Document getTargetDocument() { return document; }
			public boolean canEdit() { return overwriteEditable != null ? overwriteEditable : WrappedDocument.super.canEdit(); }
		};
	}

	@Nonnull
	@CheckReturnValue
	public static WrappedBundle newWrappedBundle(@Nonnull Bundle bundle, @Nullable Boolean overwriteEditable) {
		return new WrappedBundle() {
			@Nonnull public Bundle getTargetBundle() { return bundle; }
			public boolean canEdit() { return overwriteEditable != null ? overwriteEditable : WrappedBundle.super.canEdit(); }
		};
	}

	private Documents() {}
}
