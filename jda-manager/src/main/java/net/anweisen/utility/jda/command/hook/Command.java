package net.anweisen.utility.jda.command.hook;

import net.anweisen.utility.jda.command.hook.access.AccessCheckers;
import net.anweisen.utility.jda.command.hook.cooldown.Cooldown;
import net.dv8tion.jda.api.Permission;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

  /**
   * The name(s) of the command.
   * Discord has very strict rules of how a slashcommand and it's sub commands have to be named!
   */
  String[] name();

  /**
   * A short description of what the command does, which will be shown in discord.
   */
  String description();

  /**
   * The scope of the command defines where the command is allowed to be executed.
   * Defaults to {@link CommandScope#EVERYWHERE}
   */
  CommandScope scope() default CommandScope.EVERYWHERE;

  /**
   * The command cooldowns limit the amount of interactions a guild/user/member can do with this command.
   */
  Cooldown[] cooldown() default {};

  Permission permission() default Permission.UNKNOWN;

  AccessCheckers access() default @AccessCheckers({});

}
