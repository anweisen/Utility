package net.anweisen.utilities.jda.commandmanager.impl.entities;

import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.hooks.CommandScope;
import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.anweisen.utilities.commons.common.Colors;
import net.anweisen.utilities.jda.commandmanager.utils.OrderedRestAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.AttachmentOption;
import net.dv8tion.jda.internal.entities.UserImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.util.EnumSet;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CommandEventImpl implements CommandEvent {

	protected final Message message;
	protected final MessageChannel channel;
	protected final Member member;
	protected final CommandManager manager;

	protected final boolean disableMentions;

	public CommandEventImpl(@Nonnull CommandManager manager, @Nonnull Message message, @Nullable Member member, boolean disableMentions) {
		this.manager = manager;
		this.message = message;
		this.channel = message.getChannel();
		this.member = member;
		this.disableMentions = disableMentions;
	}

	@Nonnull
	protected MessageAction applySettings(@Nonnull MessageAction action) {
		return (disableMentions ? action.allowedMentions(EnumSet.noneOf(MentionType.class)) : action).mentionRepliedUser(false);
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull CharSequence content) {
		return applySettings(message.reply(content));
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull EmbedBuilder content) {
		return reply(content.build());
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull MessageEmbed content) {
		return applySettings(message.reply(content));
	}

	@Nonnull
	@Override
	public MessageAction replyFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options) {
		return applySettings(message.reply(file, filename, options));
	}

	@Nonnull
	@Override
	public MessageAction replyMessage(@Nonnull String name, @Nonnull Object... args) {
		return reply(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull CharSequence content) {
		return applySettings(channel.sendMessage(content));
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull EmbedBuilder content) {
		return send(content.build());
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull MessageEmbed content) {
		return applySettings(channel.sendMessage(content));
	}

	@Nonnull
	@Override
	public MessageAction sendFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options) {
		return applySettings(channel.sendFile(file, filename, options));
	}

	@Nonnull
	@Override
	public MessageAction sendMessage(@Nonnull String name, @Nonnull Object... args) {
		return send(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public RestAction<Message> sendPrivate(@Nonnull CharSequence message) {
		return new OrderedRestAction<>(getJDA(), () -> getUser().openPrivateChannel(), () -> ((UserImpl) getUser()).getPrivateChannel().sendMessage(message));
	}

	@Nonnull
	@Override
	public RestAction<Message> sendPrivate(@Nonnull EmbedBuilder message) {
		return sendPrivate(message.build());
	}

	@Nonnull
	@Override
	public RestAction<Message> sendPrivate(@Nonnull MessageEmbed message) {
		return new OrderedRestAction<>(getJDA(), () -> getUser().openPrivateChannel(), () -> ((UserImpl) getUser()).getPrivateChannel().sendMessage(message));
	}

	@Nonnull
	@Override
	public RestAction<Message> sendFilePrivate(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options) {
		return new OrderedRestAction<>(getJDA(), () -> getUser().openPrivateChannel(), () -> ((UserImpl) getUser()).getPrivateChannel().sendFile(file, filename, options));
	}

	@Nonnull
	@Override
	public RestAction<Message> sendMessagePrivate(@Nonnull String name, @Nonnull Object... args) {
		return sendPrivate(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public JDA getJDA() {
		return member.getJDA();
	}

	@Nullable
	@Override
	public ShardManager getShardManager() {
		return getJDA().getShardManager();
	}

	@Nonnull
	@Override
	public User getUser() {
		return message.getAuthor();
	}

	@Nonnull
	@Override
	public SelfUser getSelfUser() {
		return getJDA().getSelfUser();
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

	@Nonnull
	@Override
	public String getUserId() {
		return getUser().getId();
	}

	@Override
	public long getUserIdLong() {
		return getUser().getIdLong();
	}

	@Nonnull
	@Override
	public String getUserName() {
		return getUser().getName();
	}

	@Nonnull
	@Override
	public String getUserTag() {
		return getUser().getAsTag();
	}

	@Nonnull
	@Override
	public String getEffectiveUserName() {
		return member != null ? member.getEffectiveName() : getUserName();
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
	public Message getMessage() {
		return message;
	}

	@Nonnull
	@Override
	public String getMessageId() {
		return message.getId();
	}

	@Override
	public long getMessageIdLong() {
		return message.getIdLong();
	}

	@Override
	public boolean isGuild() {
		return message.isFromGuild();
	}

	@Override
	public boolean isPrivate() {
		return !message.isFromGuild();
	}

	@Nonnull
	@Override
	public CommandScope getScope() {
		return isGuild() ? CommandScope.GUILD : CommandScope.PRIVATE;
	}

	@Nonnull
	@Override
	public Guild getGuild() {
		return message.getGuild();
	}

	@Nonnull
	@Override
	public String getGuildId() {
		return getGuild().getId();
	}

	@Override
	public long getGuildIdLong() {
		return getGuild().getIdLong();
	}

	@Nonnull
	@Override
	public Member getMember() {
		if (member == null) throw new IllegalStateException();
		return member;
	}

	@Nonnull
	@Override
	public Member getSelfMember() {
		return getGuild().getSelfMember();
	}

	@Override
	public boolean hasMemberPermission(@Nonnull Permission... permission) {
		return getMember().hasPermission(permission);
	}

	@Override
	public boolean hasMemberChannelPermission(@Nonnull Permission... permissions) {
		return getMember().hasPermission(getTextChannel(), permissions);
	}

	@Override
	public boolean hasTeamRole() {
		return manager.getTeamRoleManager() != null && (!manager.getTeamRoleManager().isTeamRoleSet(getMember().getGuild()) || !manager.getTeamRoleManager().hasTeamRole(getMember()));
	}

	@Nonnull
	@Override
	public MessageChannel getChannel() {
		return channel;
	}

	@Nonnull
	@Override
	public TextChannel getTextChannel() {
		if (!isGuild()) throw new IllegalStateException("Not from a guild channel");
		return (TextChannel) channel;
	}

	@Nonnull
	@Override
	public PrivateChannel getPrivateChannel() {
		if (!isPrivate()) throw new IllegalStateException("Not from a private channel");
		return (PrivateChannel) channel;
	}

	@Nonnull
	@Override
	public String getChannelId() {
		return channel.getId();
	}

	@Override
	public long getChannelIdLong() {
		return channel.getIdLong();
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

}
