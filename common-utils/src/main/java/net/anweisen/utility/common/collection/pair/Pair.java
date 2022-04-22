package net.anweisen.utility.common.collection.pair;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @see Wrap
 * @see Tuple
 * @see Triple
 * @see Quadro
 * @since 1.0
 */
public interface Pair {

	/**
	 * @return The amount of values
	 */
	@Nonnegative
	int amount();

	@Nonnull
	Object[] values();

	/**
	 * @return {@code true} when all of the values are null, {@code false} otherwise
	 */
	boolean allNull();

	/**
	 * @return {@code true} when none of the values are null, {@code false} otherwise
	 */
	boolean noneNull();

}
