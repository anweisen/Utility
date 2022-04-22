package net.anweisen.utility.jda.manager.impl.parser;

import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.arguments.ParserOptions;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class UUIDArgumentParser implements ArgumentParser<UUID, Object> {

	@Nullable
	@Override
	public UUID parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		return UUID.fromString(input);
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
			.disableMultiWords();
	}
}
