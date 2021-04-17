package net.anweisen.utilities.jda.commandmanager;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandEvent extends MessagePipeline, ManagedMessagePipeline, MessageInfo {

	@Nonnull
	CommandManager getManager();

}
