package net.anweisen.utility.jda.command.context;

import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandContext {

  @Nonnull
  CommandInteraction getInteraction();

}
