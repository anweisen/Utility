package net.anweisen.utilities.jda.commandmanager.process;

import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.MessageInfo;
import net.anweisen.utilities.jda.commandmanager.MessagePipeline;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface CommandResultHandler {

	void handle(@Nonnull CommandManager manager, @Nonnull MessagePipeline pipeline, @Nonnull MessageInfo info, @Nonnull CommandResultInfo result);

}
