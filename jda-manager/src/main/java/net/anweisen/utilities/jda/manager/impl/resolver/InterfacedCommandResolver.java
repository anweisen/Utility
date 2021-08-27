package net.anweisen.utilities.jda.manager.impl.resolver;

import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.InterfacedCommand;
import net.anweisen.utilities.jda.manager.hooks.registered.CommandResolver;
import net.anweisen.utilities.jda.manager.hooks.registered.RegisteredCommand;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class InterfacedCommandResolver implements CommandResolver {

	@Nonnull
	@Override
	public Collection<RegisteredCommand> resolve(@Nonnull Object command, @Nonnull CommandManager manager) {
		if (!(command instanceof InterfacedCommand)) return Collections.emptyList();
		InterfacedCommand interfacedCommand = (InterfacedCommand) command;
		return Collections.singletonList(new RegisteredCommand(interfacedCommand, manager));
	}

}
