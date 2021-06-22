package net.anweisen.utilities.jda.manager.impl.parser;

import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.manager.arguments.ParserOptions;

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
