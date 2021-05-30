package net.anweisen.utilities.jda.commandmanager.impl.resolver;

import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.hooks.InterfacedCommand;
import net.anweisen.utilities.jda.commandmanager.hooks.registered.RegisteredCommand;
import net.anweisen.utilities.jda.commandmanager.hooks.registered.CommandResolver;

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
