package net.anweisen.utilities.commons.function;

import net.anweisen.utilities.commons.common.WrappedException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
@FunctionalInterface
public interface ExceptionallyRunnable extends Runnable {

	@Override
	default void run() {
		try {
			runExceptionally();
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	void runExceptionally() throws Exception;

}
