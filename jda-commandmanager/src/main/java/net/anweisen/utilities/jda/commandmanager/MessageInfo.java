package net.anweisen.utilities.jda.commandmanager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface MessageInfo {

	@Nonnull
	JDA getJDA();

	@Nonnull
	User getUser();

	@Nonnull
	SelfUser getSelfUser();

	@Nonnull
	String getUserId();

	long getUserIdLong();

	@Nonnull
	String getUserName();

	/**
	 * Returns the member's nickname if this event is {@link #isGuild() from a guild} or the {@link #getUser() user name} otherwise
	 *
	 * @return the effective user name
	 */
	@Nonnull
	String getEffectiveUserName();

	@Nonnull
	Message getMessage();

	@Nonnull
	String getMessageId();

	long getMessageIdLong();

	boolean isGuild();
	boolean isPrivate();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	@Nonnull
	Guild getGuild();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	@Nonnull
	String getGuildId();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	long getGuildIdLong();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	@Nonnull
	Member getMember();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	@Nonnull
	Member getSelfMember();


}
