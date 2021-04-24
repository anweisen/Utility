package net.anweisen.utilities.jda.commandmanager.arguments.parser;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.commandmanager.arguments.ParserOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CharParser implements ArgumentParser<Character> {

	@Nullable
	@Override
	public Character parse(@Nonnull CommandEvent event, @Nonnull String input) throws Exception {
		if (input.length() != 1) return null;
		return input.charAt(0);
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options().disableMultiWords();
	}

}
