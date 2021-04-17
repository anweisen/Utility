package net.anweisen.utilities.jda.commandmanager.utils;

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
		input = String.valueOf(input);

		if (inBlockMarkdown) {
			input = input.replace("`", "");
		} else {
			input = input.replace("*", "");
			input = input.replace("_", "");
			input = input.replace("@everyone", "everyone");
			input = input.replace("@here", "here");
		}

		return input;
	}

	@Nonnull
	public static String removeMarkdown(@Nullable String input) {
		return removeMarkdown(input, false);
	}

}
