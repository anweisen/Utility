package net.anweisen.utilities.commons.common;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class Triple<A, B, C> {

	protected A first;
	protected B second;
	protected C third;

	public Triple() {
	}

	public Triple(@Nullable A first, @Nullable B second, @Nullable C third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	public C getThird() {
		return third;
	}

	public void setFirst(@Nullable A first) {
		this.first = first;
	}

	public void setSecond(@Nullable B second) {
		this.second = second;
	}

	public void setThird(@Nullable C third) {
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
		return "Triple{" +
				"first=" + first +
				", second=" + second +
				", third=" + third +
				'}';
	}

}
