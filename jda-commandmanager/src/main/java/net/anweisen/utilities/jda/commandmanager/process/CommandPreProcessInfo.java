package net.anweisen.utilities.jda.commandmanager.process;

import net.anweisen.utilities.jda.commandmanager.PrefixProvider;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class CommandPreProcessInfo {

	private final Member member;
	private final Message message;

	public CommandPreProcessInfo(@Nullable Member member, @Nonnull Message message) {
		this.member = member;
		this.message = message;
	}

	public CommandPreProcessInfo(@Nonnull MessageReceivedEvent event) {
		this(event.isFromGuild() ? event.getMember() : null, event.getMessage());
	}

	public CommandPreProcessInfo(@Nonnull MessageUpdateEvent event) {
		this(event.isFromGuild() ? event.getMember() : null, event.getMessage());
	}

	@Nonnull
	public Message getMessage() {
		return message;
	}

	@Nullable
	public Member getMember() {
		return member;
	}

	@Nonnull
	public String getPrefix(@Nonnull PrefixProvider provider) {
		return member == null ? provider.getPrivatePrefix() : provider.getGuildPrefix(member.getGuild());
	}

}
