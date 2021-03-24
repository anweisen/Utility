package net.anweisen.utilities.database;

import net.anweisen.utilities.commons.config.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Writer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Result extends Document {

	@Nonnull
	@Override
	@Deprecated
	default Document set(@Nonnull String path, @Nullable Object value) {
		throw new UnsupportedOperationException("Result.set(String, Object)");
	}

	@Nonnull
	@Override
	@Deprecated
	default Document remove(@Nonnull String path) {
		throw new UnsupportedOperationException("Result.remove(String)");
	}

	@Nonnull
	@Override
	@Deprecated
	default Document clear() {
		throw new UnsupportedOperationException("Result.clear()");
	}

	@Override
	default void write(@Nonnull Writer writer) throws IOException {
		throw new UnsupportedOperationException("Result.write(Writer)");
	}

	@Override
	default boolean isReadonly() {
		return true;
	}

}
