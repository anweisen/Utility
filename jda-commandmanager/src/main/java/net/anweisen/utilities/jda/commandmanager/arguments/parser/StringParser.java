package net.anweisen.utilities.jda.commandmanager.arguments.parser;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class StringParser implements ArgumentParser<String> {

	@Nullable
	@Override
	public String parse(@Nonnull CommandEvent event, @Nonnull String input) throws Exception {
		return input;
	}

}
