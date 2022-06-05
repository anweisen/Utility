package net.anweisen.utility.jda.command.context.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author anweisen | https://github.com/anweisen
 * @see net.anweisen.utility.jda.manager.services.ServiceProvider
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

  boolean nullable() default false;

}
