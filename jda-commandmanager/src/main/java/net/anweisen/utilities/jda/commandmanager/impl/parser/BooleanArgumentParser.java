package net.anweisen.utilities.jda.commandmanager.impl.parser;

import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class BooleanArgumentParser implements ArgumentParser<Boolean, Object> {

	@Nullable
	@Override
	public Boolean parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		switch (input.toLowerCase()) {
			case "true":
			case "right":
			case "correct":
			case "yes":
			case "ja":
			case "wahr":
			case "richtig":
				return true;
			case "false":
			case "wrong":
			case "nein":
			case "falsch":
				return false;
			default:
				return null;
		}
	}

}
