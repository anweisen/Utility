package net.anweisen.utility.document.wrapped;

import net.anweisen.utility.common.concurrent.task.Task;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Path;

/**
 * @author anweisen | https://github.com/anweisen
 * @see StorableDocument
 * @see StorableBundle
 * @since 1.0
 */
public interface Storable {

	@Nonnull
	Path getPath();

	@Nonnull
	File getFile();

	default void save() {
		try {
			saveExceptionally();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void saveExceptionally() throws Exception;

	default void reload() {
		try {
			reloadExceptionally();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void reloadExceptionally() throws Exception;

	@Nonnull
	default Task<Void> saveAsync() {
		return Task.asyncRunExceptionally(this::saveExceptionally);
	}

}
