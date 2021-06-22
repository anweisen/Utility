package net.anweisen.utilities.bukkit.core;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.config.FileDocument;
import net.anweisen.utilities.common.config.document.GsonDocument;
import net.anweisen.utilities.common.config.document.YamlDocument;
import net.anweisen.utilities.common.misc.FileUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public class SimpleConfigManager {

	protected final Map<String, FileDocument> configs = new HashMap<>();
	protected final BukkitModule module;

	public SimpleConfigManager(@Nonnull BukkitModule module) {
		this.module = module;
	}

	@Nonnull
	public FileDocument getDocument(@Nonnull String filename) {
		if (!filename.contains(".")) filename += ".json";
		return getDocument(module.getDataFile(filename));
	}

	public synchronized FileDocument getDocument(@Nonnull File file) {
		String extension = FileUtils.getFileExtension(file);
		return configs.computeIfAbsent(file.getName(), key -> FileDocument.readFile(resolveType(extension), file));
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
