package net.anweisen.utilities.common.collection;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class Tuple<F, S> {

	protected F first;
	protected S second;

	public Tuple() {
	}

	public Tuple(@Nullable F first, @Nullable S second) {
		this.first = first;
		this.second = second;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	public void setFirst(@Nullable F first) {
		this.first = first;
	}

	public void setSecond(@Nullable S second) {
		this.second = second;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tuple<?, ?> tuple = (Tuple<?, ?>) o;
		return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public String toString() {
		return "Tuple[" + first + ", " + second + "]";
	}

	public static <F, S> Tuple<F, S> ofFirst(@Nullable F frist) {
		return new Tuple<>(frist, null);
	}

	public static <F, S> Tuple<F, S> ofSecond(@Nullable S second) {
		return new Tuple<>(null, second);
	}

	public static <F, S> Tuple<F, S> of(@Nullable F first, @Nullable S second) {
		return new Tuple<>(first, second);
	}

}
