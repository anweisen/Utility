package net.anweisen.utility.jda.manager.impl.parser;

import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

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

	@Nonnull
	@Override
	public OptionType asSlashCommandType() {
		return OptionType.BOOLEAN;
	}
}
