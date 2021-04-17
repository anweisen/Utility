package net.anweisen.utilities.jda.commandmanager.impl;

import net.anweisen.utilities.commons.misc.NumberFormatter;
import net.anweisen.utilities.jda.commandmanager.CommandField;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.MessageInfo;
import net.anweisen.utilities.jda.commandmanager.MessagePipeline;
import net.anweisen.utilities.jda.commandmanager.language.Message;
import net.anweisen.utilities.jda.commandmanager.process.CommandProcessResult;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultHandler;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultInfo;
import net.anweisen.utilities.jda.commandmanager.utils.CommandHelper;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class LanguageResultHandler implements CommandResultHandler {

	@Override
	public void handle(@Nonnull CommandManager manager, @Nonnull MessagePipeline pipeline, @Nonnull MessageInfo info, @Nonnull CommandResultInfo result) {
		if (!result.getType().isUserMistake()) return;
		String messageName = result.getType() == CommandProcessResult.INVALID_FIELD
				? "invalid-field-" + (result.getCommand().getAnnotation().field() == CommandField.GUILD ? "guild" : "private")
				: result.getType().name().toLowerCase().replace('_', '-');
		Message message = manager.getLanguageManager().getLanguage(info).getMessage("error-" + messageName);
		if (message.isEmpty()) return;
		pipeline.reply(message.asString(getArguments(result))).queue();
	}

	@Nonnull
	protected Object[] getArguments(@Nonnull CommandResultInfo result) {
		switch (result.getType()) {
			case INCORRECT_ARGUMENTS:   return new Object[] { result.getCorrectSyntax() };
			case UNKNOWN_COMMAND:       return new Object[] { CommandHelper.removeMarkdown(result.getCommandName(), true) };
			case COOLDOWN:              return new Object[] { NumberFormatter.FLOATING_POINT.format(result.getCoolDown() +  0.1) };
			default:                    return new Object[0];
		}
	}

}
