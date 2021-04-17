package net.anweisen.utilities.jda.commandmanager.impl.entities;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.CommandManager;
import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.anweisen.utilities.jda.commandmanager.language.LanguageManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CommandEventImpl extends MessageInfoAndPipeline implements CommandEvent {

	protected final CommandManager manager;

	public CommandEventImpl(@Nonnull Message message, @Nullable Member member, @Nonnull CommandManager manager) {
		super(message, member);
		this.manager = manager;
	}

	@Nonnull
	@Override
	public CommandManager getManager() {
		return manager;
	}

	@Nonnull
	@Override
	public LanguageManager getLanguageManager() {
		return manager.getLanguageManager();
	}

	@Nonnull
	@Override
	public Language getLanguage() {
		return getLanguageManager().getLanguage(this);
	}

	@Nonnull
	@Override
	public MessageAction replyMessage(@Nonnull String name, @Nonnull Object... args) {
		return reply(getLanguage().getMessage(name).asString(name, args));
	}

	@Nonnull
	@Override
	public MessageAction sendMessage(@Nonnull String name, @Nonnull Object... args) {
		return send(getLanguage().getMessage(name).asString(name, args));
	}

}
