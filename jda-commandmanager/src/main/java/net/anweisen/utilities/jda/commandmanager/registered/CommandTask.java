package net.anweisen.utilities.jda.commandmanager.registered;

import net.anweisen.utilities.jda.commandmanager.CommandArguments;
import net.anweisen.utilities.jda.commandmanager.CommandEvent;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface CommandTask {

	void execute(@Nonnull CommandEvent event, @Nonnull CommandArguments args) throws Exception;

}
