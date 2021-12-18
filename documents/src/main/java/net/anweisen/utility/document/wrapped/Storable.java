package net.anweisen.utility.document.wrapped;

import net.anweisen.utility.common.concurrent.task.Task;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Path;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see StorableDocument
 * @see StorableBundle
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

	@Nonnull
	default Task<Void> saveAsync() {
		return Task.asyncRunExceptionally(this::saveExceptionally);
	}

}
