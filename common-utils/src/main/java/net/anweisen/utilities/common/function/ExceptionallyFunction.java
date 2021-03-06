package net.anweisen.utilities.common.function;

import net.anweisen.utilities.common.collection.WrappedException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
@FunctionalInterface
public interface ExceptionallyFunction<T, R> extends Function<T, R> {

	@Override
	default R apply(T t) {
		try {
			return applyExceptionally(t);
		} catch (Exception ex) {
			throw WrappedException.rethrow(ex);
		}
	}

	R applyExceptionally(T t) throws Exception;

	@Nonnull
	@CheckReturnValue
	static <T> ExceptionallyFunction<T, T> identity() {
		return t -> t;
	}

}
