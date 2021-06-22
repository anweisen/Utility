package net.anweisen.utilities.jda.manager.defaults.commands;

import net.anweisen.utilities.jda.manager.hooks.*;
import net.anweisen.utilities.jda.manager.hooks.option.CommandScope;
import net.anweisen.utilities.jda.manager.hooks.option.CoolDownScope;
import net.anweisen.utilities.jda.manager.hooks.event.CommandArguments;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.language.Language;
import net.dv8tion.jda.api.Permission;

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
			permission = Permission.ADMINISTRATOR,
			cooldownScope = CoolDownScope.GUILD,
			cooldownSeconds = 5
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
