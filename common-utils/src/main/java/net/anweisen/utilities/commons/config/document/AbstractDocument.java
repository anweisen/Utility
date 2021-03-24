package net.anweisen.utilities.commons.config.document;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.readonly.ReadOnlyDocumentWrapper;
import net.anweisen.utilities.commons.config.exceptions.ConfigReadOnlyException;
import net.anweisen.utilities.commons.misc.FileUtils;
import net.anweisen.utilities.commons.misc.SerializationUtils;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractDocument extends AbstractConfig implements Document {

	@Nullable
	@Override
	public <T> T getSerializable(@Nonnull String path, @Nonnull Class<T> classOfT) {
		if (!contains(path)) return null;
		return SerializationUtils.deserializeObject(getDocument(path).values(), classOfT);
	}

	@Override
	public void save(@Nonnull File file) throws IOException {
		Writer writer = FileUtils.newBufferedWriter(file);
		write(writer);
		writer.flush();
		writer.close();
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		Document document = getDocument0(path);
		return isReadonly() ? new ReadOnlyDocumentWrapper(document) : document;
	}

	@Nonnull
	@Override
	public Document set(@Nonnull String path, @Nullable Object value) {
		if (isReadonly()) throw new ConfigReadOnlyException("set");
		set0(path, value);
		return this;
	}

	@Nonnull
	@Override
	public Document remove(@Nonnull String path) {
		if (isReadonly()) throw new ConfigReadOnlyException("set");
		remove0(path);
		return this;
	}

	@Nonnull
	@Override
	public Document clear() {
		if (isReadonly()) throw new ConfigReadOnlyException("set");
		clear0();
		return this;
	}

	@Nonnull
	@CheckReturnValue
	public Document readonly() {
		return isReadonly() ? this : new ReadOnlyDocumentWrapper(this);
	}

	@Nonnull
	protected abstract Document getDocument0(@Nonnull String path);

	protected abstract void set0(@Nonnull String path, @Nullable Object value);

	protected abstract void remove0(@Nonnull String path);

	protected abstract void clear0();

}
