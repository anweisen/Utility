package net.anweisen.utilities.jda.manager.impl.parser;

import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.manager.arguments.ParserOptions;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CharArgumentParser implements ArgumentParser<Character, Object> {

	@Nullable
	@Override
	public Character parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		if (input.length() != 1) return null;
		return input.charAt(0);
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
				.disableMultiWords();
	}

}
