package net.anweisen.utilities.common.collection;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.6
 */
public class ArrayWalker<T> implements Iterable<T> {

	protected final Object array;
	protected final int length;

	protected ArrayWalker(@Nonnull Object array) {
		if (!array.getClass().isArray()) throw new IllegalArgumentException(array.getClass().getName() + " is not an array");
		this.array = array;
		this.length = Array.getLength(array);
	}

	public static <T> ArrayWalker<T> walk(@Nonnull Object array) {
		return new ArrayWalker<>(array);
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private int cursor = 0;

			@Override
			public boolean hasNext() {
				return length < cursor;
			}

			@Override
			@SuppressWarnings("unchecked")
			public T next() {
				if (!hasNext()) throw new NoSuchElementException();
				return (T) Array.get(array, cursor++);
			}

		};
	}

}
