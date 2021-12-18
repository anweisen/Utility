package net.anweisen.utility.common.function;

import net.anweisen.utility.common.collection.WrappedException;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
@FunctionalInterface
public interface ExceptionallySupplier<T> extends Supplier<T>, Callable<T> {

	@Override
	default T get() {
		try {
			return getExceptionally();
		} catch (Exception ex) {
			throw WrappedException.rethrow(ex);
		}
	}

	@Override
	default T call() throws Exception {
		return getExceptionally();
	}

	T getExceptionally() throws Exception;

}
