package net.anweisen.utility.jda.manager.command;

import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.jda.manager.command.access.CommandAccessProvider;
import net.anweisen.utility.jda.manager.command.cooldown.CooldownMemory;
import net.anweisen.utility.jda.manager.command.handle.CommandHandle;
import net.anweisen.utility.jda.manager.command.registered.CommandInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandHandler {

  void handleCommandEvent(@Nonnull SlashCommandEvent event);

  @Nonnull
  Collection<CommandData> getGeneralSlashCommandData();

  @Nonnull
  Collection<CommandData> getGuildSlashCommandData(@Nonnull Guild guild);

  @Nonnull
  CommandHandler registerCommand(@Nonnull Object command);

  @Nonnull
  default CommandHandler registerCommands(@Nonnull Object... commands) {
    for (Object command : commands)
      registerCommand(command);
    return this;
  }

  @Nonnull
  CommandHandler registerCommandHandle(@Nonnull CommandInfo info, @Nonnull CommandHandle handle);

  @Nonnull
  Set<Tuple<CommandInfo, CommandHandle>> getRegisteredCommands();

  @Nonnull
  CommandHandler registerAccessProvider(@Nonnull String name, @Nonnull CommandAccessProvider provider);

  @Nullable
  CommandAccessProvider getAccessProvider(@Nonnull String name);

  @Nonnull
  CooldownMemory getCooldownMemory();

}
