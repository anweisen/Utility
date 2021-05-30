package net.anweisen.utilities.commons.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class is used to rethrow signed exception as unsigned exceptions.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class WrappedException extends RuntimeException {

	public WrappedException(@Nullable String message, @Nonnull Throwable cause) {
		super(message, cause);
	}

	public WrappedException(@Nonnull Throwable cause) {
		super(cause);
	}

}
