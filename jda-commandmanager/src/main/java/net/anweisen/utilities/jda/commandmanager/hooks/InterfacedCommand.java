package net.anweisen.utilities.jda.commandmanager.hooks;

import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandArguments;
import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface InterfacedCommand {

	void onCommand(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws Exception;

	@Nonnull
	CommandOptions options();

}
