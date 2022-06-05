package net.anweisen.utility.jda.command.hook.argument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a parameter as an argument.
 * The parameter type will be resolved and mapped to the correct option type.
 * Discord supports a bunch of intern types like users, channels, roles and can also limit the range of choices for strings/numbers.
 * TODO: example types & annotations
 *
 * @author anweisen | https://github.com/anweisen
 * @see net.anweisen.utility.jda.command.hook.argument.Optional
 * @see net.anweisen.utility.jda.command.hook.argument.choice.ChoiceChannels
 * @see net.anweisen.utility.jda.command.hook.argument.choice.type.ChoiceEnum
 * @see net.anweisen.utility.jda.command.hook.argument.choice.ChoiceString
 * @see net.anweisen.utility.jda.command.hook.argument.choice.ChoiceNumber
 * @see net.anweisen.utility.jda.command.hook.argument.choice.ChoiceInt
 * @see net.anweisen.utility.jda.command.hook.argument.limit.MaxValue
 * @see net.anweisen.utility.jda.command.hook.argument.limit.MinValue
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {

  /**
   * The static name of the argument.
   * Needed because using the parameter name could cause problems if some type of obfuscation is used.
   * Also makes the command usage independent of the code and its naming strategy.
   */
  String value();

}
