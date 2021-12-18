package net.anweisen.utility.common.function;

import net.anweisen.utility.common.collection.WrappedException;

import java.util.function.LongFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface ExceptionallyLongFunction<R> extends LongFunction<R> {

	@Override
	default R apply(long value) {
		try {
			return applyExceptionally(value);
		} catch (Exception ex) {
			throw WrappedException.rethrow(ex);
		}
	}

	R applyExceptionally(long value) throws Exception;

}
