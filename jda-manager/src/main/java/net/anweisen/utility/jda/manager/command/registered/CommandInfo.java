package net.anweisen.utility.jda.manager.command.registered;

import net.anweisen.utility.jda.command.hook.Command;
import net.anweisen.utility.jda.command.hook.CommandScope;
import net.anweisen.utility.jda.command.hook.cooldown.CooldownScope;
import net.dv8tion.jda.api.Permission;
import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class CommandInfo {

  private final UUID internalId = UUID.randomUUID();

  private final String[] name;
  private final String description;
  private final CommandScope scope;
  private final Collection<CommandCooldownInfo> cooldown;
  private final Permission permission;
  private final Collection<String> accessCheckers;

  private final List<ArgumentInfo> arguments;

  public CommandInfo(@Nonnull String[] name, @Nonnull String description, @Nonnull CommandScope scope, @Nonnull Collection<CommandCooldownInfo> cooldown,
                     @Nonnull Permission permission, @Nonnull Collection<String> accessCheckers, @Nonnull List<ArgumentInfo> arguments) {
    this.name = name;
    this.description = description;
    this.scope = scope;
    this.cooldown = cooldown;
    this.permission = permission;
    this.accessCheckers = accessCheckers;
    this.arguments = arguments;
  }

  @Nonnull
  public UUID getInternalId() {
    return internalId;
  }

  @Nonnull
  public String[] getName() {
    return name;
  }

  @Nonnull
  public String getDescription() {
    return description;
  }

  @Nonnull
  public CommandScope getScope() {
    return scope;
  }

  @Nonnull
  public Collection<CommandCooldownInfo> getCooldown() {
    return cooldown;
  }

  @Nonnull
  public Permission getPermission() {
    return permission;
  }

  @Nonnull
  public Collection<String> getAccessCheckers() {
    return accessCheckers;
  }

  @Nonnull
  public List<ArgumentInfo> getArguments() {
    return arguments;
  }

  @Override
  public String toString() {
    return "CommandInfo" + Arrays.toString(name);
  }

  @Override
  public int hashCode() {
    return internalId.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommandInfo that = (CommandInfo) o;
    return internalId.equals(that.internalId);
  }

  public static class CommandCooldownInfo {

    private final CooldownScope scope;
    private final Duration duration;

    public CommandCooldownInfo(@Nonnull CooldownScope scope, @Nonnull Duration duration) {
      this.scope = scope;
      this.duration = duration;
    }

    @Nonnull
    public CooldownScope getScope() {
      return scope;
    }

    @Nonnull
    public Duration getDuration() {
      return duration;
    }
  }
}
