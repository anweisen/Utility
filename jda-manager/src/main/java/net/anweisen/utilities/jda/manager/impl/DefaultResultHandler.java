package net.anweisen.utilities.jda.manager.impl;

import net.anweisen.utilities.common.collection.NumberFormatter;
import net.anweisen.utilities.jda.manager.hooks.option.CommandScope;
import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utilities.jda.manager.language.Message;
import net.anweisen.utilities.jda.manager.process.CommandProcessResult;
import net.anweisen.utilities.jda.manager.process.CommandResultHandler;
import net.anweisen.utilities.jda.manager.process.CommandResultInfo;
import net.anweisen.utilities.jda.manager.utils.CommandHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultResultHandler implements CommandResultHandler {

	@Override
	public void handle(@Nonnull CommandManager manager, @Nonnull CommandEvent event, @Nonnull CommandResultInfo result) {
		if (!result.getType().isUserMistake()) return;

		String defaultMessageName = getDefaultMessageName(result);

		// Custom error message
		if (result.getErrorMessage() != null) {
			sendMessage(event, result.getErrorMessage().getFirst(), result.getErrorMessage().getSecond());
			return;
		}

		if (result.getType() == CommandProcessResult.INCORRECT_ARGUMENTS) {
			RegisteredCommand command = result.getCommand() != null ? result.getCommand() : result.getCommandsMatchingName().size() == 1 ? result.getCommandsMatchingName().get(0) : null;
			if (command == null) {
				String headerMessageName = defaultMessageName + "-multiple";
				String entryMessageName = headerMessageName + "-entry";

				StringBuilder builder = new StringBuilder();
				builder.append(getMessage(event, headerMessageName).asString());

				List<String> commands = new ArrayList<>(result.getCommandsMatchingName().size());
				for (RegisteredCommand current : result.getCommandsMatchingName()) {
					commands.add(getMessage(event, entryMessageName).asString(buildFullSyntax(result, current)));
				}
				Collections.sort(commands);
				for (String current : commands) {
					builder.append("\n").append(current);
				}

				event.reply(builder).queue();
			} else {
				sendMessage(event, defaultMessageName, buildFullSyntax(result, command));
			}
			return;
		}

		// Some results require arguments or changed names but are handled standard
		Object[] arguments = new Object[0];
		if (result.getType() == CommandProcessResult.INVALID_SCOPE) {
			defaultMessageName += "-" + (result.getCommand().getOptions().getScope() == CommandScope.GUILD ? "guild" : "private");
		} else if (result.getType() == CommandProcessResult.COOLDOWN) {
			arguments = new Object[] { NumberFormatter.TIME.format(result.getCoolDown() +  0.1) };
		} else if (result.getType() == CommandProcessResult.UNKNOWN_COMMAND) {
			arguments = new Object[] { CommandHelper.removeMarkdown(result.getCommandName(), true) };
		}

		sendMessage(event, defaultMessageName, arguments);
	}

	@Nonnull
	protected String getDefaultMessageName(@Nonnull CommandResultInfo result) {
		return result.getType().name().toLowerCase().replace('_', '-');
	}

	@Nonnull
	protected Message getMessage(@Nonnull CommandEvent event, @Nonnull String name) {
		return event.getManager().getLanguageManager().getLanguage(event).getMessage("error-" + name);
	}

	protected void sendMessage(@Nonnull CommandEvent event, @Nonnull String messageName, @Nonnull Object... args) {
		Message message = getMessage(event, messageName);
		if (message.isEmpty()) return;
		event.reply(message.asString(args)).queue();
	}

	protected String buildFullSyntax(@Nonnull CommandResultInfo result, @Nonnull RegisteredCommand command) {
		return result.getPrefix() + (result.getCommand() == command ? result.getCommandName() : command.getOptions().getFirstName()) + (command.getSyntax().isEmpty() ? "" : " ") + command.getSyntax();
	}

}
