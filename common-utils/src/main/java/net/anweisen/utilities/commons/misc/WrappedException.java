package net.anweisen.utilities.commons.misc;

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

}
