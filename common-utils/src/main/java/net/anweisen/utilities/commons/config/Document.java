package net.anweisen.utilities.commons.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Document extends Config, Json {

	@Nonnull
	Document getDocument(@Nonnull String path);

	@Nonnull
	List<Document> getDocumentList(@Nonnull String path);

	@Nonnull
	<T> List<T> getSerializableList(@Nonnull String path, @Nonnull Class<T> classOfT);

	@Nullable
	Document getParent();

	@Nonnull
	Document getRoot();

	@Nonnull
	@Override
	Document set(@Nonnull String path, @Nullable Object value);

	@Nonnull
	@Override
	Document clear();

	@Nonnull
	@Override
	Document remove(@Nonnull String path);

	@Nonnull
	@Override
	Document readonly();

	void write(@Nonnull Writer writer) throws IOException;

	void save(@Nonnull File file) throws IOException;

}
