package net.anweisen.utilities.commons.common;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * This class is used to rethrow signed exception as unsigned exceptions.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class WrappedException extends RuntimeException {

	public WrappedException(@Nonnull Throwable cause) {
		super(cause);
	}

	@Nonnull
	@CheckReturnValue
	public static RuntimeException wrap(@Nonnull Throwable ex) {
		return ex instanceof RuntimeException ? (RuntimeException) ex : new WrappedException(ex);
	}

}
