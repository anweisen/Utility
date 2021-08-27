package net.anweisen.utilities.common.misc;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.6
 */
public final class MathHelper {

	private MathHelper() {}

	public static double percentage(double total, double proportion) {
		if (proportion == 0) return 0;
		return (proportion * 100) / total;
	}

}
