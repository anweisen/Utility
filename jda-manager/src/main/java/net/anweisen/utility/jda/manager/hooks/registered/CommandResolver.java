package net.anweisen.utility.jda.manager.hooks.registered;

import net.anweisen.utility.jda.manager.CommandManager;

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
