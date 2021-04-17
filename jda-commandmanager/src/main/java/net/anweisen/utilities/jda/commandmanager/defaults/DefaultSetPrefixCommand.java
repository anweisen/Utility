package net.anweisen.utilities.jda.commandmanager.defaults;

import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.jda.commandmanager.*;
import net.anweisen.utilities.jda.commandmanager.utils.CommandHelper;

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
			field = CommandField.GUILD,
			cooldownScope = CoolDownScope.GUILD,
			cooldownSeconds = 10
	)
	public void onCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws DatabaseException {
		String prefix = args.get(0);
		if (prefix.length() > maxLength) {
			event.replyMessage("command-set-prefix-max-length", maxLength).queue();
			return;
		}

		event.getManager().getPrefixProvider().setGuildPrefix(event.getGuild(), prefix);
		event.replyMessage("command-set-prefix-success", CommandHelper.removeMarkdown(prefix, true)).queue();
	}

}
