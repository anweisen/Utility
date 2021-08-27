package net.anweisen.utilities.jda.manager.hooks.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.AttachmentOption;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.File;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface MessagePipeline {

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction reply(@Nonnull Message message);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction reply(@Nonnull CharSequence content);

	@Nonnull
	@CheckReturnValue
	default ReplyMessageAction reply(@Nonnull EmbedBuilder message) {
		return reply(message.build());
	}

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction reply(@Nonnull MessageEmbed message);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction replyFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction replyMessage(@Nonnull String name, @Nonnull Object... args);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction send(@Nonnull Message message);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction send(@Nonnull CharSequence content);

	@Nonnull
	@CheckReturnValue
	default ReplyMessageAction send(@Nonnull EmbedBuilder message) {
		return send(message.build());
	}

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction send(@Nonnull MessageEmbed message);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction sendFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction sendMessage(@Nonnull String name, @Nonnull Object... args);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendPrivate(@Nonnull CharSequence content);

	@Nonnull
	@CheckReturnValue
	default RestAction<Message> sendPrivate(@Nonnull EmbedBuilder message) {
		return sendPrivate(message.build());
	}

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendPrivate(@Nonnull MessageEmbed message);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendFilePrivate(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendMessagePrivate(@Nonnull String name, @Nonnull Object... args);

}
