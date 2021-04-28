package net.anweisen.utilities.jda.commandmanager.registered.resolver;

import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.registered.RegisteredCommand;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface CommandResolver {

	@Nonnull
	Collection<RegisteredCommand> resolve(@Nonnull Object command, @Nonnull CommandManager manager);

}
