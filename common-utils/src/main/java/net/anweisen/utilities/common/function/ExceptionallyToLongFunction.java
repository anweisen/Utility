package net.anweisen.utilities.common.function;

import net.anweisen.utilities.common.collection.WrappedException;

import java.util.function.ToLongFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface ExceptionallyToLongFunction<T> extends ToLongFunction<T> {

	@Override
	default long applyAsLong(T t) {
		try {
			return applyExceptionally(t);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	long applyExceptionally(T t) throws Exception;

}
