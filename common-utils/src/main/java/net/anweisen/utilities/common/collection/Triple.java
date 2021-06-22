package net.anweisen.utilities.common.collection;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class Triple<F, S, T> {

	protected F first;
	protected S second;
	protected T third;

	public Triple() {
	}

	public Triple(@Nullable F first, @Nullable S second, @Nullable T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	public T getThird() {
		return third;
	}

	public void setFirst(@Nullable F first) {
		this.first = first;
	}

	public void setSecond(@Nullable S second) {
		this.second = second;
	}

	public void setThird(@Nullable T third) {
		this.third = third;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
		return Objects.equals(first, triple.first) && Objects.equals(second, triple.second) && Objects.equals(third, triple.third);
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second, third);
	}

	@Override
	public String toString() {
		return "Triple[" + first + ", " + second + ", " + third + "]";
	}

	public static <F, S, T> Triple<F, S, T> ofFirst(@Nullable F first) {
		return new Triple<>(first, null, null);
	}

	public static <F, S, T> Triple<F, S, T> ofSecond(@Nullable S second) {
		return new Triple<>(null, second, null);
	}

	public static <F, S, T> Triple<F, S, T> ofThird(@Nullable T third) {
		return new Triple<>(null, null, third);
	}

	public static <F, S, T> Triple<F, S, T> of(@Nullable F first, @Nullable S second, @Nullable T third) {
		return new Triple<>(first, second, third);
	}

}
