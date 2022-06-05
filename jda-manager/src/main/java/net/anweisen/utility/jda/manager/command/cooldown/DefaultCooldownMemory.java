package net.anweisen.utility.jda.manager.command.cooldown;

import net.anweisen.utility.jda.manager.command.registered.CommandInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultCooldownMemory implements CooldownMemory {

  // TODO this could potentially be a very big memory leak

  private final Map<CommandInfo, Long> globalExpiration = new ConcurrentHashMap<>();
  private final Map<CommandInfo, Map<Guild, Long>> guildExpiration = new ConcurrentHashMap<>();
  private final Map<CommandInfo, Map<Member, Long>> memberExpiration = new ConcurrentHashMap<>();
  private final Map<CommandInfo, Map<User, Long>> userExpiration = new ConcurrentHashMap<>();

  @Override
  public long getGlobalCooldown(@Nonnull CommandInfo command) {
    return this.getCooldownFromExpiration(globalExpiration.get(command));
  }

  @Override
  public long getGuildCooldown(@Nonnull CommandInfo command, @Nonnull Guild guild) {
    Map<Guild, Long> specificExpirations = guildExpiration.get(command);
    if (specificExpirations == null) return -1;
    return this.getCooldownFromExpiration(specificExpirations.get(guild));
  }

  @Override
  public long getUserCooldown(@Nonnull CommandInfo command, @Nonnull User user) {
    Map<User, Long> specificExpirations = userExpiration.get(command);
    if (specificExpirations == null) return -1;
    return this.getCooldownFromExpiration(specificExpirations.get(user));
  }

  @Override
  public long getMemberCooldown(@Nonnull CommandInfo command, @Nonnull Member member) {
    Map<Member, Long> specificExpirations = memberExpiration.get(command);
    if (specificExpirations == null) return -1;
    return this.getCooldownFromExpiration(specificExpirations.get(member));
  }

  @Override
  public void updateGlobalCooldown(@Nonnull CommandInfo command, @Nonnull Duration duration) {
    globalExpiration.put(command, this.getExpirationFromCooldown(duration));
  }

  @Override
  public void updateGuildCooldown(@Nonnull CommandInfo command, @Nonnull Guild guild, @Nonnull Duration duration) {
    Map<Guild, Long> specificExpirations = guildExpiration.computeIfAbsent(command, __ -> new ConcurrentHashMap<>());
    specificExpirations.put(guild, this.getExpirationFromCooldown(duration));
  }

  @Override
  public void updateUserCooldown(@Nonnull CommandInfo command, @Nonnull User user, @Nonnull Duration duration) {
    Map<User, Long> specificExpirations = userExpiration.computeIfAbsent(command, __ -> new ConcurrentHashMap<>());
    specificExpirations.put(user, this.getExpirationFromCooldown(duration));
  }

  @Override
  public void updateMemberCooldown(@Nonnull CommandInfo command, @Nonnull Member member, @Nonnull Duration duration) {
    Map<Member, Long> specificExpirations = memberExpiration.computeIfAbsent(command, __ -> new ConcurrentHashMap<>());
    specificExpirations.put(member, this.getExpirationFromCooldown(duration));
  }

  protected long getCooldownFromExpiration(@Nullable Long expiration) {
    if (expiration == null || expiration < 0)
      return -1;

    long difference = expiration - System.currentTimeMillis();
    return difference < 1 ? -1 : difference;
  }

  protected long getExpirationFromCooldown(@Nonnull Duration cooldown) {
    return System.currentTimeMillis() + cooldown.toMillis();
  }
}
