package net.anweisen.utilities.jda.manager.examples;

import net.anweisen.utilities.jda.manager.hooks.*;
import net.anweisen.utilities.jda.manager.hooks.option.CommandScope;
import net.anweisen.utilities.jda.manager.hooks.option.CoolDownScope;
import net.anweisen.utilities.jda.manager.hooks.event.CommandArguments;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CommandExample {

	@Command(
			name = { "help", "h" }
	)
	public void onHelpCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) {
		// Reply hardcoded message
		event.reply("Help is coming for you! D:").queue();
	}

	@Command(
			name = "ban",
			usage = "[guild:member]",
			scope = CommandScope.GUILD,
			permission = Permission.BAN_MEMBERS,
			team = true,
			cooldownScope = CoolDownScope.GUILD,
			cooldownSeconds = 5
	)
	public void onBanCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) {
		Member member = args.get(0);

		if (!event.getMember().canInteract(member)) {
			// Reply translated message "command-ban-too-high" with argument {0} being the members mention
			event.replyMessage("command-ban-too-high", member.getAsMention()).queue();
			return;
		}
		if (!event.getSelfMember().canInteract(member)) {
			// Reply translated message "command-ban-self-too-high" with argument {0} being the members mention
			event.replyMessage("command-ban-self-too-high", member.getAsMention()).queue();
			return;
		}

		member.ban(0, "Banned by " + event.getUserTag()).queue();
		event.replyMessage("command-ban-success", member.getUser().getAsTag(), event.getMember().getAsMention()).queue();
	}

}
