package net.anweisen.utilities.common.function;

import net.anweisen.utilities.common.collection.WrappedException;

import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
@FunctionalInterface
public interface ExceptionallyBiConsumer<T, U> extends BiConsumer<T, U> {

	@Override
	default void accept(T t, U u) {
		try {
			acceptExceptionally(t, u);
		} catch (Exception ex) {
			throw WrappedException.rethrow(ex);
		}
	}

	void acceptExceptionally(T t, U u) throws Exception;

}
