package net.anweisen.utilities.jda.commandmanager.defaults.commands;

import net.anweisen.utilities.jda.commandmanager.hooks.*;
import net.anweisen.utilities.jda.commandmanager.hooks.CoolDownScope;
import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandArguments;
import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.6
 */
public class DefaultSetTeamRoleCommand {

	@Command(
			name = { "setteamrole", "setteamrank", "setteam" },
			usage = "[guild:role]",
			scope = CommandScope.GUILD,
			permission = Permission.ADMINISTRATOR,
			cooldownScope = CoolDownScope.GUILD,
			cooldownSeconds = 5
	)
	public void onSetCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws Exception {
		Role role = args.get(0);
		event.getManager().getTeamRoleManager().setTeamRole(event.getGuild(), role);
		event.replyMessage("command-set-team-role", role.getAsMention()).queue();
	}

	@Command(
			name = { "removeteamrole", "removeteam", "removeteamrank", "resetteam", "resetteamrole", "resetteamrank" },
			scope = CommandScope.GUILD,
			permission = Permission.ADMINISTRATOR
	)
	public void onRemoveCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws Exception {
		event.getManager().getTeamRoleManager().setTeamRole(event.getGuild(), null);
		event.replyMessage("command-remove-team-role").queue();
	}

}
