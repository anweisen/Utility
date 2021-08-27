package net.anweisen.utilities.jda.manager.process;

import net.anweisen.utilities.jda.manager.PrefixProvider;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class CommandPreProcessInfo {

	private final boolean isSlashCommand;

	private final Member member;
	private final Message message;
	private final CommandInteraction interaction;

	private final String content;

	public CommandPreProcessInfo(@Nullable Member member, @Nonnull Message message) {
		this.isSlashCommand = false;
		this.interaction = null;

		this.member = member;
		this.message = message;

		this.content = null;
	}

	public CommandPreProcessInfo(@Nonnull MessageReceivedEvent event) {
		this(event.isFromGuild() ? event.getMember() : null, event.getMessage());
	}

	public CommandPreProcessInfo(@Nonnull MessageUpdateEvent event) {
		this(event.isFromGuild() ? event.getMember() : null, event.getMessage());
	}

	public CommandPreProcessInfo(@Nonnull SlashCommandEvent event) {
		this.isSlashCommand = true;

		this.member = event.isFromGuild() ? event.getMember() : null;
		this.interaction = event;
		this.message = null;

		StringBuilder builder = new StringBuilder(event.getName());
		if (event.getSubcommandGroup() != null)
			builder.append(' ').append(event.getSubcommandGroup());
		if (event.getSubcommandName() != null)
			builder.append(' ').append(event.getSubcommandName());
		for (OptionMapping option : event.getOptions())
			builder.append(' ').append(option.getAsString());

		this.content = builder.toString();
	}

	@Nullable
	public Message getMessage() {
		return message;
	}

	@Nullable
	public CommandInteraction getInteraction() {
		return interaction;
	}

	@Nonnull
	public String getMessageContent() {
		return message != null ? message.getContentRaw() : content;
	}

	@Nullable
	public Member getMember() {
		return member;
	}

	@Nonnull
	public User getUser() {
		return message != null ? message.getAuthor() : interaction.getUser();
	}

	@Nonnull
	public MessageChannel getChannel() {
		return message != null ? message.getChannel() : interaction.getChannel();
	}

	public boolean isFromGuild() {
		return message != null ? message.isFromGuild() : interaction.isFromGuild();
	}

	@Nonnull
	public String getPrefix(@Nonnull PrefixProvider provider) {
		return member == null ? provider.getPrivatePrefix() : provider.getGuildPrefix(member.getGuild());
	}
}
