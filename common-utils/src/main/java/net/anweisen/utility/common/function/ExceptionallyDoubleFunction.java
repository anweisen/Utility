package net.anweisen.utility.common.function;

import net.anweisen.utility.common.collection.WrappedException;

import java.util.function.DoubleFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface ExceptionallyDoubleFunction<R> extends DoubleFunction<R> {

	@Override
	default R apply(double value) {
		try {
			return applyExceptionally(value);
		} catch (Exception ex) {
			throw WrappedException.rethrow(ex);
		}
	}

	R applyExceptionally(double value) throws Exception;

}
