package net.anweisen.utility.jda.manager.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class CommandHelper {

	private CommandHelper() {}

	@Nonnull
	public static String removeMarkdown(@Nullable String input, boolean inBlockMarkdown) {
		if (input == null) return "null";

		if (inBlockMarkdown) {
			input = input.replace("`", "");
		} else {
			input = input.replace("*", "\\*");
			input = input.replace("_", "\\_");
		}

		return input;
	}

	@Nonnull
	public static String removeMarkdown(@Nullable String input) {
		return removeMarkdown(input, false);
	}

}
