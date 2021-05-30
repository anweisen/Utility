package net.anweisen.utilities.commons.misc;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public final class FileUtils {

	private FileUtils() {}

	@Nonnull
	public static BufferedWriter newBufferedWriter(@Nonnull File file) throws IOException {
		return Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
	}

	@Nonnull
	public static BufferedReader newBufferedReader(@Nonnull File file) throws IOException {
		return Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
	}

	@Nonnull
	public static String getFileExtension(@Nonnull File file) {
		return getFileExtension(file.getName());
	}

	@Nonnull
	public static String getFileExtension(@Nonnull String filename) {
		return Optional.of(filename)
				.filter(name -> name.contains("."))
				.map(name -> name.substring(filename.lastIndexOf(".") + 1).toLowerCase())
				.orElse("");
	}

	@Nonnull
	public static String getFileName(@Nonnull File file) {
		return getFileName(file.getName());
	}

	@Nonnull
	public static String getFileName(@Nonnull String filename) {
		filename = stripFolders(filename);
		int index = filename.lastIndexOf('.');
		if (index == -1) return filename;
		return filename.substring(0, index);
	}

	@Nonnull
	@CheckReturnValue
	public static String stripFolders(@Nonnull String filename) {
		int index = filename.lastIndexOf('/');
		if (index == -1) return filename;
		return filename.substring(index + 1);
	}

	public static void createFilesIfNecessary(@Nonnull File file) throws IOException {
		if (file.exists()) return;

		if (file.isDirectory()) {
			file.mkdirs();
		} else if (file.getParentFile() != null) {
			file.getParentFile().mkdirs();
		}

		file.createNewFile();
	}

	public static void deleteWorldFolder(@Nonnull File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) return;
			for (File currentFile : files) {
				if (currentFile.isDirectory()) {
					// Don't delete directories or we'Ll minecraft won't create them again
					deleteWorldFolder(currentFile);
				} else {
					if (currentFile.getName().equals("session.lock")) continue; // Don't delete or we'll get lots of exceptions
					currentFile.delete();
				}
			}
		}
	}

	@Nonnull
	@CheckReturnValue
	public static File createTempFile(@Nonnull String type) throws IOException {
		File file = File.createTempFile(UUID.randomUUID().toString(), type);
		file.deleteOnExit();
		return file;
	}

}
