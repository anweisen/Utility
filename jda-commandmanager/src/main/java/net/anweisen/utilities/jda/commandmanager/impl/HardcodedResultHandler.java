package net.anweisen.utilities.jda.commandmanager.impl;

import net.anweisen.utilities.commons.misc.NumberFormatter;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.message.MessageInfo;
import net.anweisen.utilities.jda.commandmanager.message.MessagePipeline;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultHandler;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultInfo;
import net.anweisen.utilities.jda.commandmanager.utils.CommandHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class HardcodedResultHandler implements CommandResultHandler {

	private final boolean unknownCommandMessage;

	public HardcodedResultHandler(boolean unknownCommandMessage) {
		this.unknownCommandMessage = unknownCommandMessage;
	}

	@Override
	public void handle(@Nonnull CommandManager manager, @Nonnull MessagePipeline pipeline, @Nonnull MessageInfo info, @Nonnull CommandResultInfo result) {
		String message = getMessage(info, result);
		if (message == null) return;
		pipeline.reply(message).queue();
	}

	@Nullable
	public String getMessage(@Nonnull MessageInfo info, @Nonnull CommandResultInfo result) {
		switch (result.getType()) {
			case ERROR:                 return "Something went wrong";
			case MISSING_PERMISSION:    return "You do not have enough permissions to execute that command";
			case MISSING_TEAM_ROLE:     return "You need the team role to execute that command";
			case INVALID_FIELD:         return "You can only use this command in a " + (info.isGuild() ? "guild" : "private chat");
			case COOLDOWN:              return "Please wait `" + NumberFormatter.FLOATING_POINT.format(result.getCoolDown() + 0.1) + "s`";
			case INCORRECT_ARGUMENTS:   return "Please use `" + result.getCorrectSyntax() + "`";
			case UNKNOWN_COMMAND:       return unknownCommandMessage ? "Unknown command `" + CommandHelper.removeMarkdown(result.getCommandName(), true) + "`" : null;
			default:                    return null;
		}
	}

}
