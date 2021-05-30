package net.anweisen.utilities.jda.commandmanager.message;

import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.hooks.CommandScope;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface MessageInfo {

	@Nonnull
	CommandManager getManager();

	@Nonnull
	JDA getJDA();

	@Nullable
	ShardManager getShardManager();

	@Nonnull
	SelfUser getSelfUser();

	@Nullable
	Color getSelfColor();

	@Nonnull
	Color getSelfColorNonnull();

	@Nonnull
	User getUser();

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
	String getUserTag();

	@Nullable
	Color getUserColor();

	@Nonnull
	Color getUserColorNonnull();

	@Nonnull
	Message getMessage();

	@Nonnull
	String getMessageId();

	long getMessageIdLong();

	boolean isGuild();
	boolean isPrivate();

	/**
	 * @return either {@link CommandScope#GUILD} if {@link #isGuild()} or {@link CommandScope#PRIVATE} otherwise
	 */
	@Nonnull
	CommandScope getScope();

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

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	boolean hasMemberPermission(@Nonnull Permission... permission);

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	boolean hasMemberChannelPermission(@Nonnull Permission... permissions);

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	boolean hasTeamRole();

	@Nonnull
	MessageChannel getChannel();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	@Nonnull
	TextChannel getTextChannel();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a private chat
	 *         You can check this using {@link #isPrivate()}
	 */
	@Nonnull
	PrivateChannel getPrivateChannel();

	@Nonnull
	String getChannelId();

	long getChannelIdLong();

}
