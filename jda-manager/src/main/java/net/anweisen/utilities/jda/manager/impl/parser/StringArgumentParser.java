package net.anweisen.utilities.jda.manager.impl.parser;

import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class StringArgumentParser implements ArgumentParser<String, Object> {

	@Nullable
	@Override
	public String parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		return input;
	}

}
