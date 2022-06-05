package net.anweisen.utility.jda.command.hook.cooldown;

import java.util.concurrent.TimeUnit;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public @interface Cooldown {

  CooldownScope scope();

  long time();

  TimeUnit unit() default TimeUnit.SECONDS;

}
