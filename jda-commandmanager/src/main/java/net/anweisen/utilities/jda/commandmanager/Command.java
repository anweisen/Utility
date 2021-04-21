package net.anweisen.utilities.jda.commandmanager;

import net.dv8tion.jda.api.Permission;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
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

	@Nonnull
	String[] name();

	@Nonnull
	String usage() default "";

	@Nonnull
	CommandField field() default CommandField.GENERAL;

	@Nonnull
	Permission permission() default Permission.UNKNOWN;

	boolean team() default false;

	boolean async() default false;

	boolean allowBots() default false;

	boolean allowWebHooks() default false;

	boolean allowEdits() default true;

	boolean disableMentions() default true;

	@Nonnull
	CoolDownScope cooldownScope() default CoolDownScope.USER;

	@Nonnegative
	double cooldownSeconds() default 0;

}
