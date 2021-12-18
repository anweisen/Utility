package net.anweisen.utility.jda.manager.hooks.event;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.AttachmentOption;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public interface ReplyMessageAction extends RestAction<Message> {

	@Nonnull
	@Override
	@CheckReturnValue
	ReplyMessageAction setCheck(@Nullable BooleanSupplier checks);

	@Nonnull
	@Override
	@CheckReturnValue
	default ReplyMessageAction timeout(long timeout, @Nonnull TimeUnit unit) {
		return (ReplyMessageAction) RestAction.super.timeout(timeout, unit);
	}

	@Nonnull
	@Override
	@CheckReturnValue
	ReplyMessageAction deadline(long timestamp);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction setContent(@Nonnull CharSequence content);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction setTTS(boolean isTTS);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction setEmbed(@Nonnull MessageEmbed embed);

	@Nonnull
	@CheckReturnValue
	default ReplyMessageAction addActionRow(@Nonnull Component... components) {
		return addActionRow(ActionRow.of(components));
	}

	@Nonnull
	@CheckReturnValue
	default ReplyMessageAction addActionRow(@Nonnull ActionRow row) {
		return addActionRows(row);
	}

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction addActionRows(@Nonnull ActionRow... rows);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction addFile(@Nonnull File file, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction addFile(@Nonnull File file, @Nonnull String name, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction addFile(@Nonnull byte[] data, @Nonnull String name, @Nonnull AttachmentOption... options);

	@Nonnull
	@CheckReturnValue
	ReplyMessageAction addFile(@Nonnull InputStream input, @Nonnull String name, @Nonnull AttachmentOption... options);

}
