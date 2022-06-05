package net.anweisen.utility.jda.manager.command.argument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ArgumentResolverRegistry {

  @Nullable
  <T> ArgumentResolver<T> find(@Nonnull Class<T> type);

  void register(@Nonnull ArgumentResolver<?> resolver);

}
