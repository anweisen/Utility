package net.anweisen.utilities.jda.commandmanager.impl.entities;

import net.anweisen.utilities.jda.commandmanager.MessageInfo;
import net.anweisen.utilities.jda.commandmanager.MessagePipeline;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MessageInfoAndPipeline implements MessagePipeline, MessageInfo {

	protected final Message message;
	protected final MessageChannel channel;
	protected final Member member;

	public MessageInfoAndPipeline(@Nonnull Message message, @Nullable Member member) {
		this.message = message;
		this.channel = message.getChannel();
		this.member = member;
	}

	@Nonnull
	@Override
	public MessageAction reply() {
		return reply("");
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull CharSequence message) {
		return this.message.reply(message).mentionRepliedUser(false);
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull EmbedBuilder message) {
		return reply(message.build());
	}

	@Nonnull
	@Override
	public MessageAction reply(@Nonnull MessageEmbed message) {
		return this.message.reply(message).mentionRepliedUser(false);
	}

	@Nonnull
	@Override
	public MessageAction send() {
		return send("");
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull CharSequence message) {
		return channel.sendMessage(message);
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull EmbedBuilder message) {
		return send(message.build());
	}

	@Nonnull
	@Override
	public MessageAction send(@Nonnull MessageEmbed message) {
		return channel.sendMessage(message);
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

}
