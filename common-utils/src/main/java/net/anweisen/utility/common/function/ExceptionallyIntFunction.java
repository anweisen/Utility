package net.anweisen.utility.common.function;

import net.anweisen.utility.common.collection.WrappedException;

import java.util.function.IntFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface ExceptionallyIntFunction<R> extends IntFunction<R> {

	@Override
	default R apply(int value) {
		try {
			return applyExceptionally(value);
		} catch (Exception ex) {
			throw WrappedException.rethrow(ex);
		}
	}

	R applyExceptionally(int value) throws Exception;

}
