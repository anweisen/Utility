package net.anweisen.utilities.jda.manager.hooks;

import net.anweisen.utilities.jda.manager.hooks.option.CommandScope;
import net.anweisen.utilities.jda.manager.hooks.option.CoolDownScope;
import net.dv8tion.jda.api.JDA;
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
 *
 * @see net.anweisen.utilities.jda.manager.CommandManager
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

	@Nonnull
	String[] name();

	@Nonnull
	String usage() default "";

	@Nonnull
	CommandScope scope() default CommandScope.GENERAL;

	/**
	 * Required permission to use this command.
	 * Can only be used with {@link CommandScope#GUILD CommandScope.GUILD}.
	 */
	@Nonnull
	Permission permission() default Permission.UNKNOWN;

	/**
	 * Whether the team rank (or the permission above) is required to use this command.
	 * Can only be used with {@link CommandScope#GUILD CommandScope.GUILD}.
	 */
	boolean team() default false;

	boolean async() default false;

	/**
	 * Whether bots should be allowed to use this command.
	 */
	boolean allowBots() default false;

	/**
	 * Whether webhooks should be allowed to use this command.
	 */
	boolean allowWebHooks() default false;

	/**
	 * Whether this command can be triggered when a message is edited.
	 */
	boolean allowEdits() default true;

	/**
	 * Whether all mentions should be disabled in response messages.
	 */
	boolean disableMentions() default true;

	/**
	 * Whether the bot should start typing in this channel before the command is being processed.
	 */
	boolean typing() default false;

	/**
	 * Whether this command should be registered as slash command in {@link net.anweisen.utilities.jda.manager.CommandManager#setupSlashCommands(JDA)}
	 * and should be accessible through slash commands.
	 */
	boolean allowSlashCommands() default true;

	/**
	 * The scope the cooldown is counting on.
	 *
	 * @see #cooldownSeconds()
	 */
	@Nonnull
	CoolDownScope cooldownScope() default CoolDownScope.USER;

	/**
	 * The length of the cooldown in seconds.
	 *
	 * @see #cooldownScope()
	 */
	@Nonnegative
	double cooldownSeconds() default 0;

}
