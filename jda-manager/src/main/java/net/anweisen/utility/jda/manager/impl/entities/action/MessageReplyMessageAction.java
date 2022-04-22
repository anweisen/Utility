package net.anweisen.utility.jda.manager.impl.entities.action;

import net.anweisen.utility.common.collection.WrappedException;
import net.anweisen.utility.jda.manager.hooks.event.ReplyMessageAction;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class MessageReplyMessageAction extends AbstractReplyMessageAction<MessageAction, Message> {

	public static final Function<Message, Message> MAPPER = Function.identity();

	public MessageReplyMessageAction(@Nonnull MessageAction action) {
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
		action.content(content.toString());
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction setTTS(boolean isTTS) {
		action.tts(isTTS);
		return this;
	}

	@Nonnull
	@Override
	public ReplyMessageAction setEmbed(@Nonnull MessageEmbed embed) {
		action.setEmbeds(embed);
		return this;
	}

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	public ReplyMessageAction addActionRows(@Nonnull ActionRow... rows) {
		try {
			Class<?> actionClass = action.getClass();
			Field componentsField = actionClass.getDeclaredField("components");
			componentsField.setAccessible(true);

			final Collection<ActionRow> components;

			Object componentsObject = componentsField.get(action);
			if (componentsObject instanceof Collection)
				components = (Collection<ActionRow>) componentsObject;
			else
				components = new ArrayList<>(rows.length);

			Collections.addAll(components, rows);
			action.setActionRows(components);
			return this;
		} catch (Throwable ex) {
			throw new WrappedException("Reflections for injecting components failed, JDA v" + JDAInfo.VERSION + " | ActionClass " + action.getClass().getName(), ex);
		}
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
