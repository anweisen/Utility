package net.anweisen.utility.jda.manager.hooks;

import net.anweisen.utility.jda.manager.hooks.event.CommandArguments;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utility.jda.manager.hooks.option.CommandOptions;

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
