package net.anweisen.utility.jda.manager.impl.parser;

import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.arguments.ParserOptions;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ColorArgumentParser implements ArgumentParser<Color, Object> {

	@Nullable
	@Override
	public Color parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		return Color.decode(input);
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
				.disableMultiWords();
	}
}
