package net.anweisen.utility.jda.manager.command.argument;

import net.anweisen.utility.jda.manager.command.registered.ArgumentInfo;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ArgumentResolver<T> {

  boolean applies(@Nonnull Class<?> type);

  T resolve(@Nonnull ArgumentInfo info, @Nonnull CommandInteraction interaction);

  @Nonnull
  OptionData build(@Nonnull ArgumentInfo info);

}
