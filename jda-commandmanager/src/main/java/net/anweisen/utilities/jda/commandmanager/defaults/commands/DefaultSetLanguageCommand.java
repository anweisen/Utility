package net.anweisen.utilities.jda.commandmanager.defaults.commands;

import net.anweisen.utilities.jda.commandmanager.*;
import net.anweisen.utilities.jda.commandmanager.language.Language;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultSetLanguageCommand {

	@Command(
			name = { "setlanguage", "setlang", "lang", "language" },
			usage = "[string language]",
			scope = CommandScope.GUILD,
			cooldownScope = CoolDownScope.GUILD,
			cooldownSeconds = 10
	)
	public void onCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws Exception {
		Language language = event.getManager().getLanguageManager().getLanguageByName(args.get(0));
		if (language == null) {
			event.replyMessage("command-set-language-invalid").queue();
			return;
		}

		event.getManager().getLanguageManager().setLanguage(event.getGuild(), language);
		event.replyMessage("command-set-language-success", language.getName()).queue();
	}

}
