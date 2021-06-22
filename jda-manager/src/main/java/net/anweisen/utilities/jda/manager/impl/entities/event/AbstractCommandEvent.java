package net.anweisen.utilities.jda.manager.impl.entities.event;

import net.anweisen.utilities.common.collection.Colors;
import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.action.ReplyMessageAction;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.impl.entities.action.MessageReplyMessageAction;
import net.anweisen.utilities.jda.manager.impl.entities.action.ReplyReplyMessageAction;
import net.anweisen.utilities.jda.manager.language.Language;
import net.anweisen.utilities.jda.manager.utils.Embeds;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageAction;
import net.dv8tion.jda.api.utils.AttachmentOption;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.util.EnumSet;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public abstract class AbstractCommandEvent implements CommandEvent {

	protected final CommandManager manager;
	protected final MessageChannel channel;
	protected final Member member;
	protected final boolean disableMentions, embeds;

	public AbstractCommandEvent(@Nonnull CommandManager manager, @Nonnull MessageChannel channel, @Nullable Member member, boolean disableMentions, boolean embeds) {
		this.manager = manager;
		this.channel = channel;
		this.member = member;
		this.disableMentions = disableMentions;
		this.embeds = embeds;
	}

	@Nonnull
	protected MessageAction applySettings(@Nonnull MessageAction action) {
		return (disableMentions ? action.allowedMentions(EnumSet.noneOf(MentionType.class)) : action).mentionRepliedUser(false);
	}

	@Nonnull
	protected <T> WebhookMessageAction<T> applySettings(@Nonnull WebhookMessageAction<T> action) {
		return (disableMentions ? action.allowedMentions(EnumSet.noneOf(MentionType.class)) : action).mentionRepliedUser(false);
	}

	@Nonnull
	protected ReplyMessageAction wrap(@Nonnull MessageAction action) {
		return new MessageReplyMessageAction(action);
	}

	@Nonnull
	protected ReplyMessageAction wrap(@Nonnull WebhookMessageAction<Message> action) {
		return new ReplyReplyMessageAction(action);
	}

	@Nonnull
	@Override
	public RestAction<Message> sendPrivate(@Nonnull MessageEmbed message) {
		return getUser().openPrivateChannel().flatMap(channel -> applySettings(channel.sendMessage(message)));
	}

	@Nonnull
	@Override
	public RestAction<Message> sendPrivate(@Nonnull CharSequence content) {
		return embeds
			? sendPrivate(Embeds.construct(this).setDescription(content))
			: getUser().openPrivateChannel().flatMap(channel -> applySettings(channel.sendMessage(content)));
	}

	@Nonnull
	@Override
	public RestAction<Message> sendFilePrivate(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options) {
		return getUser().openPrivateChannel().flatMap(channel -> applySettings(channel.sendFile(file, filename, options)));
	}

	@Nonnull
	@Override
	public RestAction<Message> sendMessagePrivate(@Nonnull String name, @Nonnull Object... args) {
		return sendPrivate(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public ReplyMessageAction sendMessage(@Nonnull String name, @Nonnull Object... args) {
		return send(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public ReplyMessageAction replyMessage(@Nonnull String name, @Nonnull Object... args) {
		return reply(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public JDA getJDA() {
		return channel.getJDA();
	}

	@Nullable
	@Override
	public Color getSelfColor() {
		return isGuild() ? getSelfMember().getColor() : null;
	}

	@Nonnull
	@Override
	public Color getSelfColorNonnull() {
		return getColorNonnull(getSelfColor());
	}

	@Nullable
	@Override
	public Color getUserColor() {
		return isGuild() ? getMember().getColor() : null;
	}

	@Nonnull
	@Override
	public Color getUserColorNonnull() {
		return getColorNonnull(getUserColor());
	}

	@Nonnull
	protected Color getColorNonnull(@Nullable Color origin) {
		return origin == null ? Colors.EMBED : origin;
	}

	@Nonnull
	@Override
	public String getEffectiveUserName() {
		return member != null ? member.getEffectiveName() : getUserName();
	}

	@Nonnull
	@Override
	public MessageChannel getChannel() {
		return channel;
	}

	@Nonnull
	@Override
	public TextChannel getTextChannel() {
		requireGuild();
		return (TextChannel) channel;
	}

	@Nonnull
	@Override
	public PrivateChannel getPrivateChannel() {
		requirePrivate();
		return (PrivateChannel) channel;
	}

	@Override
	public boolean hasTeamRole() {
		return manager.getTeamRoleManager() != null && (!manager.getTeamRoleManager().isTeamRoleSet(getGuild()) || !manager.getTeamRoleManager().hasTeamRole(getMember()));
	}

	@Nonnull
	@Override
	public Language getLanguage() {
		return manager.getLanguageManager().getLanguage(this);
	}

	@Nonnull
	@Override
	public CommandManager getManager() {
		return manager;
	}

	@Override
	public boolean isGuild() {
		return member != null;
	}

	@Override
	public boolean isPrivate() {
		return !isGuild();
	}

	@Nonnull
	@Override
	public Guild getGuild() {
		requireGuild();
		return member.getGuild();
	}

	@Nonnull
	@Override
	public Member getMember() {
		requireGuild();
		return member;
	}

	protected void requireGuild() {
		if (!isGuild())
			throw new IllegalStateException("CommandEvent was not triggered in a guild chat");
	}

	protected void requirePrivate() {
		if (!isPrivate())
			throw new IllegalStateException("CommandEvent was not triggered in a private chat");
	}

	protected void requireMessage() {
		if (!isMessage())
			throw new IllegalStateException("CommandEvent was not triggered with a message");
	}

	protected void requireInteraction() {
		if (!isInteraction())
			throw new IllegalStateException("CommandEvent was not triggered with a interaction");
	}

	@Override
	public String toString() {
		return "CommandEvent[" + getUserTag() + ": " + getScope() + "/" + (isInteraction() ? "SLASH_COMMAND" : "MESSAGE") + "]";
	}
}
