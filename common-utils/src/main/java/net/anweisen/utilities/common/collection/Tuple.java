package net.anweisen.utilities.common.collection;

import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 *
 * @param <F> The type of the first value
 * @param <S> The type of the second value
 */
@Deprecated
public class Tuple<F, S> extends net.anweisen.utilities.common.collection.pair.Tuple<F, S> {

	public Tuple() {
	}

	public Tuple(@Nullable F first, @Nullable S second) {
		super(first, second);
	}

}
