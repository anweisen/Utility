package net.anweisen.utilities.commons.function;

import net.anweisen.utilities.commons.common.WrappedException;

import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.2
 */
@FunctionalInterface
public interface ExceptionallySupplier<T> extends Supplier<T> {

	@Override
	default T get() {
		try {
			return getExceptionally();
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	T getExceptionally() throws Exception;

}
