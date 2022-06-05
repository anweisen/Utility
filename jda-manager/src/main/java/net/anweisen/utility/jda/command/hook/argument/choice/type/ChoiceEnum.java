package net.anweisen.utility.jda.command.hook.argument.choice.type;

import javax.annotation.Nonnull;

/**
 * This interface must be implemented into enums which are wanted to be used as argument types in commands.
 *
 * @author anweisen | https://github.com/anweisen
 * @see net.anweisen.utility.jda.command.hook.argument.Argument
 * @since 1.0
 */
public interface ChoiceEnum {

  /**
   * The display name to show in discord / the value the users have to use when executing the command
   *
   * @return the display name
   */
  @Nonnull
  String getDisplayName();

  /**
   * @return the enum constant name  (default java.lang.Enum implementation)
   */
  @Nonnull
  String name();

  /**
   * @return the enum constant index (default java.lang.Enum implementation)
   */
  int ordinal();

}
