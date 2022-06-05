package net.anweisen.utility.jda.manager.services;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultServiceProvider implements ServiceProvider {

  protected final Map<Class<?>, RegisteredServiceEntry<?>> registry = new ConcurrentHashMap<>();

  @Nonnull
  @Override
  public <T, I extends T> ServiceProvider bindService(@Nonnull Class<T> apiClass, @Nonnull I instance) {
    registry.put(apiClass, new RegisteredServiceEntry<>(instance));
    return this;
  }

  @Nonnull
  @Override
  public ServiceProvider unbindServices(@Nonnull Class<?> apiClass) {
    registry.remove(apiClass);
    return this;
  }

  @Nullable
  @Override
  public <T> T provide(@Nonnull Class<T> apiClass) {
    return apiClass.cast(registry.get(apiClass));
  }

  @Override
  public boolean isRepresented(@Nonnull Class<?> apiClass) {
    return registry.containsKey(apiClass);
  }

  @Override
  public boolean isBound(@Nonnull Object instance) {
    return registry.values().stream().anyMatch(entry -> entry.instance == instance);
  }

  public static class RegisteredServiceEntry<T> {

    private final T instance;

    public RegisteredServiceEntry(@Nonnull T instance) {
      this.instance = instance;
    }
  }

}
