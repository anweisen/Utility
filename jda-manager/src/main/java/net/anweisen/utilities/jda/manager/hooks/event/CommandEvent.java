package net.anweisen.utilities.jda.manager.hooks.event;

import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.option.CommandScope;
import net.anweisen.utilities.jda.manager.language.Language;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandEvent extends MessagePipeline {

	@Nonnull
	CommandManager getManager();

	@Nonnull
	JDA getJDA();

	@Nullable
	default ShardManager getShardManager() {
		return getJDA().getShardManager();
	}

	@Nonnull
	default SelfUser getSelfUser() {
		return getJDA().getSelfUser();
	}

	@Nullable
	Color getSelfColor();

	@Nonnull
	Color getSelfColorNonnull();

	@Nonnull
	User getUser();

	@Nonnull
	default String getUserId() {
		return getUser().getId();
	}

	default long getUserIdLong() {
		return getUser().getIdLong();
	}

	@Nonnull
	default String getUserName() {
		return getUser().getName();
	}

	/**
	 * Returns the member's nickname if this event is {@link #isGuild() from a guild} or the {@link #getUserName() user name} otherwise
	 *
	 * @return the effective user name
	 *
	 * @see Member#getEffectiveName()
	 */
	@Nonnull
	String getEffectiveUserName();

	@Nonnull
	default String getUserTag() {
		return getUser().getAsTag();
	}

	@Nullable
	Color getUserColor();

	@Nonnull
	Color getUserColorNonnull();

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered by a message (eg through a slashcommand).
	 */
	@Nonnull
	Message getMessage();

	@Nonnull
	default String getMessageId() {
		return getMessage().getId();
	}

	default long getMessageIdLong() {
		return getMessage().getIdLong();
	}

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered by an interaction (eg through a message)
	 */
	@Nonnull
	Interaction getInteraction();

	boolean isGuild();
	boolean isPrivate();

	boolean isInteraction();

	/**
	 * @return if this event was triggered by a message
	 */
	boolean isMessage();

	/**
	 * @return either {@link CommandScope#GUILD} if {@link #isGuild()} or {@link CommandScope#PRIVATE} otherwise
	 */
	@Nonnull
	default CommandScope getScope() {
		return isGuild() ? CommandScope.GUILD : CommandScope.PRIVATE;
	}

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
	default String getGuildId() {
		return getGuild().getId();
	}

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	default long getGuildIdLong() {
		return getGuild().getIdLong();
	}

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
	default Member getSelfMember() {
		return getGuild().getSelfMember();
	}

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	default boolean hasPermission(@Nonnull Permission... permission) {
		return getMember().hasPermission(permission);
	}

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	default boolean hasChannelPermission(@Nonnull Permission... permissions) {
		return getMember().hasPermission(getTextChannel(), permissions);
	}

	/**
	 * @throws IllegalStateException
	 *         If this event was not triggered in a guild.
	 *         You can check this using {@link #isGuild()}
	 */
	boolean hasTeamRole();

	@Nonnull
	MessageChannel getChannel();

	@Nonnull
	default ChannelType getChannelType() {
		return getChannel().getType();
	}

	default boolean isFrom(@Nonnull ChannelType type) {
		return getChannelType() == type;
	}

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
	default String getChannelId() {
		return getChannel().getId();
	}

	default long getChannelIdLong() {
		return getChannel().getIdLong();
	}

	@Nonnull
	Language getLanguage();

}
