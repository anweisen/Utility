package net.anweisen.utilities.jda.commandmanager.message;

import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
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
	MessageAction reply(@Nonnull CharSequence message);

	@Nonnull
	@CheckReturnValue
	MessageAction reply(@Nonnull EmbedBuilder message);

	@Nonnull
	@CheckReturnValue
	MessageAction reply(@Nonnull MessageEmbed message);

	@Nonnull
	@CheckReturnValue
	MessageAction replyFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	MessageAction replyMessage(@Nonnull String name, @Nonnull Object... args);

	@Nonnull
	@CheckReturnValue
	MessageAction send(@Nonnull CharSequence message);

	@Nonnull
	@CheckReturnValue
	MessageAction send(@Nonnull EmbedBuilder message);

	@Nonnull
	@CheckReturnValue
	MessageAction send(@Nonnull MessageEmbed message);

	@Nonnull
	@CheckReturnValue
	MessageAction sendFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	MessageAction sendMessage(@Nonnull String name, @Nonnull Object... args);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendPrivate(@Nonnull CharSequence message);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendPrivate(@Nonnull EmbedBuilder message);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendPrivate(@Nonnull MessageEmbed message);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendFilePrivate(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	RestAction<Message> sendMessagePrivate(@Nonnull String name, @Nonnull Object... args);

	@Nonnull
	Language getLanguage();

}
