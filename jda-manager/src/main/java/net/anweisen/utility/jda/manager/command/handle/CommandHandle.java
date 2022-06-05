package net.anweisen.utility.jda.manager.command.handle;

import net.anweisen.utility.jda.command.context.CommandContext;
import net.anweisen.utility.jda.manager.services.ServiceProvider;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandHandle {

  void execute(@Nonnull CommandContext context, @Nonnull ServiceProvider serviceProvider) throws Exception;

}
