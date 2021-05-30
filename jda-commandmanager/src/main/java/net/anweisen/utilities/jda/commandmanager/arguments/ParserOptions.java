package net.anweisen.utilities.jda.commandmanager.arguments;

import net.anweisen.utilities.jda.commandmanager.hooks.CommandScope;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ParserOptions {

	private Collection<CommandScope> scopes = EnumSet.allOf(CommandScope.class);
	private String multiWordSeparator;
	private int maxMultiWords;
	private boolean requireExtraInfo;

	public ParserOptions() {
		multiWordSeparator = " ";
		maxMultiWords = 0;
		requireExtraInfo = false;
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
	public ParserOptions requireExtraInfo() {
		requireExtraInfo = true;
		return this;
	}

	@Nonnull
	public ParserOptions withScopes(@Nonnull CommandScope... scopes) {
		if (scopes.length == 0) throw new IllegalArgumentException("Cannot use no scopes");
		this.scopes = Arrays.asList(scopes);
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

	public boolean isExtraInfoRequired() {
		return requireExtraInfo;
	}

	@Nonnull
	public Collection<CommandScope> getScopes() {
		return scopes;
	}

}
