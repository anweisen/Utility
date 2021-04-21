package net.anweisen.utilities.jda.commandmanager.impl.entities;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.anweisen.utilities.jda.commandmanager.utils.OrderedRestAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.entities.UserImpl;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
	protected MessageAction applyDefaults(@Nonnull MessageAction action) {
		if (disableMentions) action.allowedMentions(EnumSet.noneOf(MentionType.class));
		return action.mentionRepliedUser(false);
	}

	@Nonnull
	@Override
	public MessageAction reply() {
		return reply("");
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull CharSequence content) {
		return applyDefaults(message.reply(content));
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull EmbedBuilder content) {
		return reply(content.build());
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull MessageEmbed content) {
		return applyDefaults(message.reply(content));
	}

	@Nonnull
	@Override
	public MessageAction replyMessage(@Nonnull String name, @Nonnull Object... args) {
		return reply(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public MessageAction send() {
		return send("");
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull CharSequence content) {
		return applyDefaults(channel.sendMessage(content));
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull EmbedBuilder content) {
		return send(content.build());
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull MessageEmbed content) {
		return applyDefaults(channel.sendMessage(content));
	}

	@Nonnull
	@Override
	public MessageAction sendMessage(@Nonnull String name, @Nonnull Object... args) {
		return send(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public RestAction<Void> sendPrivate(@Nonnull CharSequence message) {
		return new OrderedRestAction(getJDA(), () -> getUser().openPrivateChannel(), () -> ((UserImpl) getUser()).getPrivateChannel().sendMessage(message));
	}

	@Nonnull
	@Override
	public RestAction<Void> sendPrivate(@Nonnull EmbedBuilder message) {
		return sendPrivate(message.build());
	}

	@Nonnull
	@Override
	public RestAction<Void> sendPrivate(@Nonnull MessageEmbed message) {
		return new OrderedRestAction(getJDA(), () -> getUser().openPrivateChannel(), () -> ((UserImpl) getUser()).getPrivateChannel().sendMessage(message));
	}

	@Nonnull
	@Override
	public RestAction<Void> sendMessagePrivate(@Nonnull String name, @Nonnull Object... args) {
		return sendPrivate(getLanguage().getMessage(name).asString(args));
	}

	@Nonnull
	@Override
	public JDA getJDA() {
		return member.getJDA();
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
	public String getEffectiveUserName() {
		return member != null ? member.getEffectiveName() : getUserName();
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

	@NotNull
	@Override
	public PrivateChannel getPrivateChannel() {
		if (!isPrivate()) throw new IllegalStateException("Not from a private channel");
		return (PrivateChannel) channel;
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
