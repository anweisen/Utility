package net.anweisen.utility.jda.manager.command.registered;

import net.anweisen.utility.jda.manager.command.argument.ArgumentResolver;
import net.dv8tion.jda.api.entities.ChannelType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ArgumentInfo {

  private final String name, description;
  private final boolean optional;
  private final Number maxValue, minValue;
  private final Map<String, Object> manualChoices;
  private final Collection<ChannelType> channelTypes;

  private final Class<?> originalType;
  private final ArgumentResolver<?> resolver;

  public ArgumentInfo(@Nonnull String name, @Nonnull String description, boolean optional, @Nullable Number maxValue, @Nullable Number minValue,
                      @Nonnull Map<String, Object> manualChoices, @Nullable Collection<ChannelType> channelTypes,
                      @Nonnull Class<?> originalType, @Nonnull ArgumentResolver<?> resolver) {
    this.name = name;
    this.description = description;
    this.optional = optional;
    this.maxValue = maxValue;
    this.minValue = minValue;
    this.manualChoices = manualChoices;
    this.channelTypes = channelTypes;
    this.originalType = originalType;
    this.resolver = resolver;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isOptional() {
    return optional;
  }

  public Number getMaxValue() {
    return maxValue;
  }

  public Number getMinValue() {
    return minValue;
  }

  public Map<String, Object> getManualChoices() {
    return manualChoices;
  }

  public Collection<ChannelType> getChannelTypes() {
    return channelTypes;
  }

  public Class<?> getOriginalType() {
    return originalType;
  }

  public ArgumentResolver<?> getResolver() {
    return resolver;
  }
}
