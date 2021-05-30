package net.anweisen.utilities.commons.function;

import net.anweisen.utilities.commons.common.WrappedException;

import java.util.function.Consumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
@FunctionalInterface
public interface ExceptionallyConsumer<T> extends Consumer<T> {

	@Override
	default void accept(T t) {
		try {
			acceptExceptionally(t);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	void acceptExceptionally(T t) throws Exception;

}
