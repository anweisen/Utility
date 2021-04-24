package net.anweisen.utilities.jda.commandmanager.arguments.parser;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.commandmanager.arguments.ParserOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class NumberParser implements ArgumentParser<Number> {

	private final Function<String, ? extends Number> parser;

	public NumberParser(@Nonnull Function<String, ? extends Number> parser) {
		this.parser = parser;
	}

	@Nullable
	@Override
	public Number parse(@Nonnull CommandEvent event, @Nonnull String input) throws Exception {
		return parser.apply(input);
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options().disableMultiWords();
	}

}
