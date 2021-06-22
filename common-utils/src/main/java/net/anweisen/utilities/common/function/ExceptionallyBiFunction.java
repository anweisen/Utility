package net.anweisen.utilities.common.function;

import net.anweisen.utilities.common.collection.WrappedException;

import java.util.function.BiFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
@FunctionalInterface
public interface ExceptionallyBiFunction<T, U, R> extends BiFunction<T, U, R> {

	@Override
	default R apply(T t, U u) {
		try {
			return applyExceptionally(t, u);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	R applyExceptionally(T t, U u) throws Exception;

}
