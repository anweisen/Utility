package net.anweisen.utilitites.commons.config.document.readonly;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.readonly.ReadOnlyDocumentWrapper;
import net.anweisen.utilitites.commons.config.document.YamlDocument;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class ReadOnlyYamlDocument extends YamlDocument {

	public ReadOnlyYamlDocument() {
		super();
	}

	public ReadOnlyYamlDocument(@Nonnull ConfigurationSection config) {
		super(config);
	}

	public ReadOnlyYamlDocument(@Nonnull File file) {
		super(file);
	}

	@Nonnull
	@Override
	@Deprecated
	public Document set(@Nonnull String path, @Nullable Object value) {
		throw new UnsupportedOperationException("ReadOnlyYamlDocument.set(String, Object)");
	}

	@Nonnull
	@Override
	@Deprecated
	public Document remove(@Nonnull String path) {
		throw new UnsupportedOperationException("ReadOnlyYamlDocument.remove(String)");
	}

	@Nonnull
	@Override
	@Deprecated
	public Document clear() {
		throw new UnsupportedOperationException("ReadOnlyYamlDocument.clear()");
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		return new ReadOnlyDocumentWrapper(super.getDocument(path));
	}

	@Override
	public boolean isReadonly() {
		return true;
	}

}
