package net.anweisen.utility.jda.manager.command.cooldown;

import net.anweisen.utility.jda.manager.command.registered.CommandInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import javax.annotation.Nonnull;
import java.time.Duration;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CooldownMemory {

  /**
   * @param command the command to which the cooldown is assigned
   * @return the remaining cooldown in milliseconds, or -1
   */
  long getGlobalCooldown(@Nonnull CommandInfo command);

  /**
   * @param command the command to which the cooldown is assigned
   * @param guild   the guild which has the command cooldown
   * @return the remaining cooldown in milliseconds, or -1
   */
  long getGuildCooldown(@Nonnull CommandInfo command, @Nonnull Guild guild);

  /**
   * @param command the command to which the cooldown is assigned
   * @param user    the user who has the command cooldown
   * @return the remaining cooldown in milliseconds, or -1
   */
  long getUserCooldown(@Nonnull CommandInfo command, @Nonnull User user);

  /**
   * @param command the command to which the cooldown is assigned
   * @param member  the member who has the command cooldown
   * @return the remaining cooldown in milliseconds, or -1
   */
  long getMemberCooldown(@Nonnull CommandInfo command, @Nonnull Member member);

  /**
   * Assigns a global cooldown to the target command.
   *
   * @param command  the target command to which the cooldown should be assigned to
   * @param duration the duration of the cooldown
   */
  void updateGlobalCooldown(@Nonnull CommandInfo command, @Nonnull Duration duration);

  /**
   * @param command  the target command to which the cooldown should be assigned to
   * @param guild    the guild which has the command cooldown
   * @param duration the duration of the cooldown
   */
  void updateGuildCooldown(@Nonnull CommandInfo command, @Nonnull Guild guild, @Nonnull Duration duration);

  /**
   * @param command  the target command to which the cooldown should be assigned to
   * @param user     the user who has the command cooldown
   * @param duration the duration of the cooldown
   */
  void updateUserCooldown(@Nonnull CommandInfo command, @Nonnull User user, @Nonnull Duration duration);

  /**
   * @param command  the target command to which the cooldown should be assigned to
   * @param member   the member who has the command cooldown
   * @param duration the duration of the cooldown
   */
  void updateMemberCooldown(@Nonnull CommandInfo command, @Nonnull Member member, @Nonnull Duration duration);

  default long checkGuildUserCooldown(@Nonnull CommandInfo command, @Nonnull Member member) {
    long globalCooldown = getGlobalCooldown(command);
    if (globalCooldown > 0)
      return globalCooldown;

    long guildCooldown = getGuildCooldown(command, member.getGuild());
    if (guildCooldown > 0)
      return globalCooldown;

    long memberCooldown = getMemberCooldown(command, member);
    if (memberCooldown > 0)
      return memberCooldown;

    long userCooldown = getUserCooldown(command, member.getUser());
    if (userCooldown > 0)
      return userCooldown;

    return -1;
  }

  default long checkUserCooldown(@Nonnull CommandInfo command, @Nonnull User user) {
    long globalCooldown = getGlobalCooldown(command);
    if (globalCooldown > 0)
      return globalCooldown;

    long userCooldown = getUserCooldown(command, user);
    if (userCooldown > 0)
      return userCooldown;

    return -1;
  }

  default long checkGuildCooldown(@Nonnull CommandInfo command, @Nonnull Guild guild) {
    long globalCooldown = getGlobalCooldown(command);
    if (globalCooldown > 0)
      return globalCooldown;

    long guildCooldown = getGuildCooldown(command, guild);
    if (guildCooldown > 0)
      return guildCooldown;

    return -1;
  }

}
