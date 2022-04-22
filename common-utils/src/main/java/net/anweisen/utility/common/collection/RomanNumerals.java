package net.anweisen.utility.common.collection;

import javax.annotation.Nonnull;
import java.util.TreeMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class RomanNumerals {

	public static final class IllegalRomanNumeralException extends IllegalArgumentException {

		private IllegalRomanNumeralException(int number) {
			super("Number " + number + " out of bounds for " + MIN_VALUE + " to " + MAX_VALUE);
		}

	}

	public static final int MAX_VALUE = 3999;
	public static final int MIN_VALUE = 0;

	private static final TreeMap<Integer, String> values = new TreeMap<>();

	static {
		values.put(1000, "M");
		values.put(900, "CM");
		values.put(500, "D");
		values.put(400, "CD");
		values.put(100, "C");
		values.put(90, "XC");
		values.put(50, "L");
		values.put(40, "XL");
		values.put(10, "X");
		values.put(9, "IX");
		values.put(5, "V");
		values.put(4, "IV");
		values.put(1, "I");
	}

	private RomanNumerals() {
	}

	@Nonnull
	public static String forNumber(int number) {
		if (number < MIN_VALUE || number > MAX_VALUE) throw new IllegalRomanNumeralException(number);
		if (number == 0) return "";
		int i = values.floorKey(number);
		if (number == i) {
			return values.get(number);
		}
		return values.get(i) + forNumber(number - i);
	}

}
