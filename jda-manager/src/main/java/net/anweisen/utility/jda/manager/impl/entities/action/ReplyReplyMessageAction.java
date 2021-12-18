package net.anweisen.utility.jda.manager.impl.entities.action;

import net.anweisen.utility.jda.manager.hooks.event.ReplyMessageAction;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageAction;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ReplyReplyMessageAction extends AbstractReplyMessageAction<WebhookMessageAction<Message>, Message> {

	public static final Function<Message, Message> MAPPER = Function.identity();

	public ReplyReplyMessageAction(@Nonnull WebhookMessageAction<Message> action) {
		super(action);
	}

	@Nonnull
	@Override
	public Function<Message, Message> getResponseMapper() {
		return MAPPER;
	}

	@Nonnull
	@Override
	public ReplyMessageAction setCheck(@Nullable BooleanSupplier checks) {
		action.setCheck(checks);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction deadline(long timestamp) {
		action.deadline(timestamp);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction setContent(@Nonnull CharSequence content) {
		action.setContent(content.toString());
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction setTTS(boolean isTTS) {
		action.setTTS(isTTS);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction setEmbed(@Nonnull MessageEmbed embed) {
		action.addEmbeds(embed);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction addActionRows(@Nonnull ActionRow... rows) {
		action.addActionRows(rows);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction addFile(@Nonnull File file, @Nonnull AttachmentOption... options) {
		action.addFile(file, options);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction addFile(@Nonnull File file, @Nonnull String name, @Nonnull AttachmentOption... options) {
		action.addFile(file, name, options);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction addFile(@Nonnull byte[] data, @Nonnull String name, @Nonnull AttachmentOption... options) {
		action.addFile(data, name, options);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction addFile(@Nonnull InputStream input, @Nonnull String name, @Nonnull AttachmentOption... options) {
		action.addFile(input, name, options);
		return this;
	}
}
