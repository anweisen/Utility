package net.anweisen.utility.jda.manager.services;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ServiceProvider {

  // TODO docs

  @Nonnull
  <T, I extends T> ServiceProvider bindService(@Nonnull Class<T> apiClass, @Nonnull I instance);

  @Nonnull
  ServiceProvider unbindServices(@Nonnull Class<?> apiClass);

  @Nullable
  <T> T provide(@Nonnull Class<T> apiClass);

  boolean isRepresented(@Nonnull Class<?> apiClass);

  boolean isBound(@Nonnull Object instance);

}
