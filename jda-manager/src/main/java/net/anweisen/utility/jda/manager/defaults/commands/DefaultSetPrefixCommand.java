package net.anweisen.utility.jda.manager.defaults.commands;

import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.jda.manager.hooks.Command;
import net.anweisen.utility.jda.manager.hooks.event.CommandArguments;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utility.jda.manager.hooks.option.CommandScope;
import net.anweisen.utility.jda.manager.hooks.option.CoolDownScope;
import net.anweisen.utility.jda.manager.utils.CommandHelper;
import net.dv8tion.jda.api.Permission;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultSetPrefixCommand {

	private final int maxLength;

	public DefaultSetPrefixCommand(@Nonnegative int maxLength) {
		this.maxLength = maxLength;
	}

	@Command(
		name = "setprefix",
		usage = "[string prefix]",
		scope = CommandScope.GUILD,
		permission = Permission.ADMINISTRATOR,
		cooldownScope = CoolDownScope.GUILD,
		cooldownSeconds = 5
	)
	public void onCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws DatabaseException {
		String prefix = args.<String>get(0).replace("`", "");
		if (prefix.length() > maxLength) {
			event.replyMessage("command-set-prefix-max-length", maxLength).queue();
			return;
		}

		event.getManager().getPrefixProvider().setGuildPrefix(event.getGuild(), prefix);
		event.replyMessage("command-set-prefix-success", CommandHelper.removeMarkdown(prefix, true)).queue();
	}

}
