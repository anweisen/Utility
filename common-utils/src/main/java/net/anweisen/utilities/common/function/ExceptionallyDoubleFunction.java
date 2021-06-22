package net.anweisen.utilities.common.function;

import net.anweisen.utilities.common.collection.WrappedException;

import java.util.function.DoubleFunction;
import java.util.function.LongFunction;

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
			throw new WrappedException(ex);
		}
	}

	R applyExceptionally(double value) throws Exception;

}
