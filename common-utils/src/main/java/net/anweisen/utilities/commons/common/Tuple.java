package net.anweisen.utilities.commons.common;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class Tuple<A, B> {

	protected A first;
	protected B second;

	public Tuple() {
	}

	public Tuple(@Nullable A first, @Nullable B second) {
		this.first = first;
		this.second = second;
	}

	@Nullable
	public A getFirst() {
		return first;
	}

	@Nullable
	public B getSecond() {
		return second;
	}

	public void setFirst(@Nullable A first) {
		this.first = first;
	}

	public void setSecond(@Nullable B second) {
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
		return "Tuple{" +
				"first=" + first +
				", second=" + second +
				'}';
	}

}
