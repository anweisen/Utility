package net.anweisen.utility.jda.command.hook.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessCheckers {

  /**
   * The names of the access check providers.
   * This implementation is an or-system, meaning only one of the checkers/providers
   * has to be {@code true} in order to get access to the command.
   */
  String[] value();

}
