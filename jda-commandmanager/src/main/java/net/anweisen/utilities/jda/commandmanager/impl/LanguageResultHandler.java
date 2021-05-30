package net.anweisen.utilities.jda.commandmanager.impl;

import net.anweisen.utilities.commons.misc.NumberFormatter;
import net.anweisen.utilities.jda.commandmanager.hooks.CommandScope;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.message.MessageInfo;
import net.anweisen.utilities.jda.commandmanager.message.MessagePipeline;
import net.anweisen.utilities.jda.commandmanager.language.Message;
import net.anweisen.utilities.jda.commandmanager.process.CommandProcessResult;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultHandler;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultInfo;
import net.anweisen.utilities.jda.commandmanager.utils.CommandHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class LanguageResultHandler implements CommandResultHandler {

	@Override
	public void handle(@Nonnull CommandManager manager, @Nonnull MessagePipeline pipeline, @Nonnull MessageInfo info, @Nonnull CommandResultInfo result) {
		if (!result.getType().isUserMistake()) return;

		String messageName = getExceptionMessageName(result);
		Object[] arguments = result.getErrorMessage() == null ? getArguments(result) : result.getErrorMessage().getSecond() == null ? new Object[0] : result.getErrorMessage().getSecond();
		if (messageName == null || getMessage(manager, info, messageName).isEmpty()) {
			messageName = getDefaultMessageName(result);
			arguments = getArguments(result);
		}

		Message message = getMessage(manager, info, messageName);
		if (message.isEmpty()) return;
		pipeline.reply(message.asString(arguments)).queue();
	}

	@Nullable
	protected String getExceptionMessageName(@Nonnull CommandResultInfo result) {
		if (result.getErrorMessage() != null)
			return result.getErrorMessage().getFirst();
		if (result.getType() == CommandProcessResult.INVALID_SCOPE)
			return "invalid-scope-" + (result.getCommand().getOptions().getScope() == CommandScope.GUILD ? "guild" : "private");

		return null;
	}

	@Nonnull
	protected String getDefaultMessageName(@Nonnull CommandResultInfo result) {
		return result.getType().name().toLowerCase().replace('_', '-');
	}

	@Nonnull
	protected Message getMessage(@Nonnull CommandManager manager, @Nonnull MessageInfo info, @Nonnull String name) {
		return manager.getLanguageManager().getLanguage(info).getMessage("error-" + name);
	}

	@Nonnull
	protected Object[] getArguments(@Nonnull CommandResultInfo result) {
		switch (result.getType()) {
			case INCORRECT_ARGUMENTS:   return new Object[] { result.getCorrectSyntax() };
			case UNKNOWN_COMMAND:       return new Object[] { CommandHelper.removeMarkdown(result.getCommandName(), true) };
			case COOLDOWN:              return new Object[] { NumberFormatter.DOUBLE_FLOATING_POINT.format(result.getCoolDown() +  0.1) };
			default:                    return new Object[0];
		}
	}

}
