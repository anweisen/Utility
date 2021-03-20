package net.anweisen.utilities.database;

import net.anweisen.utilities.commons.config.document.EmptyDocument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class EmptyResult extends EmptyDocument implements Result {

	@Nonnull
	@Override
	@Deprecated
	public Result set(@Nonnull String path, @Nullable Object value) {
		return (Result) super.set(path, value);
	}

	@Nonnull
	@Override
	@Deprecated
	public Result remove(@Nonnull String path) {
		return (Result) super.remove(path);
	}

	@Nonnull
	@Override
	@Deprecated
	public Result clear() {
		return (Result) super.clear();
	}

}
