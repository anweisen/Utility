package net.anweisen.utilities.jda.commandmanager.arguments;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ParserOptions {

	private String multiWordSeparator;
	private int maxMultiWords;

	public ParserOptions() {
		multiWordSeparator = " ";
		maxMultiWords = 0;
	}

	@Nonnull
	public ParserOptions withMultiWordSeparator(@Nonnull String multiWordSeparator) {
		this.multiWordSeparator = multiWordSeparator;
		return this;
	}

	@Nonnull
	public ParserOptions removeMultiWordSeparator() {
		return withMultiWordSeparator("");
	}

	@Nonnull
	public ParserOptions withMaxMultiWorlds(@Nonnegative int max) {
		if (max < 0) throw new IllegalArgumentException("max cannot be smaller than 0");
		this.maxMultiWords = max;
		return this;
	}

	@Nonnull
	public ParserOptions disableMultiWords() {
		withMaxMultiWorlds(1);
		removeMultiWordSeparator();
		return this;
	}

	@Nonnull
	public String getMultiWordSeparator() {
		return multiWordSeparator;
	}

	@Nonnegative
	public int getMaxMultiWords() {
		return maxMultiWords;
	}

	public boolean hasMultiWorldEnabled() {
		return maxMultiWords > 1;
	}

}
