package net.anweisen.utilities.common.function;

import net.anweisen.utilities.common.collection.WrappedException;

import java.util.concurrent.Callable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
@FunctionalInterface
public interface ExceptionallyRunnable extends Runnable, Callable<Void> {

	@Override
	default void run() {
		try {
			runExceptionally();
		} catch (Exception ex) {
			throw WrappedException.rethrow(ex);
		}
	}

	@Override
	default Void call() throws Exception {
		runExceptionally();
		return null;
	}

	void runExceptionally() throws Exception;

}
