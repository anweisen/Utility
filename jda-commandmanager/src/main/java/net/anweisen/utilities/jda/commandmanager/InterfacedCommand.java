package net.anweisen.utilities.jda.commandmanager;

import net.anweisen.utilities.jda.commandmanager.registered.CommandOptions;

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

