package net.anweisen.utility.bukkit.core;

import net.anweisen.utility.common.config.document.YamlDocument;
import net.anweisen.utility.common.misc.FileUtils;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.gson.GsonDocument;
import net.anweisen.utility.document.wrapped.Storable;
import net.anweisen.utility.document.wrapped.StorableDocument;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public class SimpleConfigManager {

	protected final Map<String, StorableDocument> configs = new HashMap<>();
	protected final BukkitModule module;

	public SimpleConfigManager(@Nonnull BukkitModule module) {
		this.module = module;
	}

	@Nonnull
	public Storable getDocument(@Nonnull String filename) {
		if (!filename.contains(".")) filename += ".json";
		return getDocument(module.getDataFile(filename));
	}

	public synchronized Storable getDocument(@Nonnull File file) {
		String extension = FileUtils.getFileExtension(file);
		return configs.computeIfAbsent(file.getName(), key -> Storable.readFile(resolveType(extension), file));
	}

	@Nonnull
	public static Class<? extends Document> resolveType(@Nonnull String extension) {
		switch (extension.toLowerCase()) {
			case "json":    return GsonDocument.class;
			case "yml":
			case "yaml":    return YamlDocument.class;
			default:        throw new IllegalArgumentException("Unknown document file extension '" + extension + "'");
		}
	}

}
