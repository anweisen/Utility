package net.anweisen.utilities.jda.manager.impl.entities.event;

import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.event.ReplyMessageAction;
import net.anweisen.utilities.jda.manager.utils.Embeds;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class SlashCommandEvent extends AbstractCommandEvent {

	private final CommandInteraction interaction;

	public SlashCommandEvent(@Nonnull CommandManager manager, @Nonnull CommandInteraction interaction, @Nullable Member member, boolean disableMentions, boolean embeds) {
		super(manager, interaction.getMessageChannel(), member, disableMentions, embeds);
		this.interaction = interaction;
	}

	@Override
	public boolean isInteraction() {
		return true;
	}

	@Override
	public boolean isMessage() {
		return false;
	}

	@Nonnull
	@Override
	public User getUser() {
		return interaction.getUser();
	}

	@Nonnull
	@Override
	public Message getMessage() {
		requireMessage();
		throw new IllegalStateException("Unreachable code");
	}

	@Nonnull
	@Override
	public Interaction getInteraction() {
		return interaction;
	}

	@Nonnull
	@Override
	public ReplyMessageAction reply(@Nonnull Message message) {
		return wrap(applySettings(interaction.getHook().sendMessage(message)));
	}

	@Nonnull
	@Override
	public ReplyMessageAction reply(@Nonnull CharSequence content) {
		return embeds
			? reply(Embeds.construct(this).setDescription(content))
			: wrap(applySettings(interaction.getHook().sendMessage(content.toString())));
	}

	@Nonnull
	@Override
	public ReplyMessageAction reply(@Nonnull MessageEmbed message) {
		return wrap(interaction.getHook().sendMessage(new MessageBuilder(message).build()));
	}

	@Nonnull
	@Override
	public ReplyMessageAction replyFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options) {
		return wrap(interaction.getHook().sendFile(file, filename, options));
	}

	@Nonnull
	@Override
	public ReplyMessageAction send(@Nonnull Message message) {
		return reply(message);
	}

	@Nonnull
	@Override
	public ReplyMessageAction send(@Nonnull CharSequence content) {
		return reply(content);
	}

	@Nonnull
	@Override
	public ReplyMessageAction send(@Nonnull MessageEmbed message) {
		return reply(message);
	}

	@Nonnull
	@Override
	public ReplyMessageAction sendFile(@Nonnull File file, @Nonnull String filename, @Nonnull AttachmentOption... options) {
		return replyFile(file, filename, options);
	}
}
