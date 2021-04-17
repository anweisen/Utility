package net.anweisen.utilities.jda.commandmanager;

import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.anweisen.utilities.jda.commandmanager.language.LanguageManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ManagedMessagePipeline {

	@Nonnull
	LanguageManager getLanguageManager();

	@Nonnull
	Language getLanguage();

	@Nonnull
	@CheckReturnValue
	MessageAction replyMessage(@Nonnull String name, @Nonnull Object... args);

	@Nonnull
	@CheckReturnValue
	MessageAction sendMessage(@Nonnull String name, @Nonnull Object... args);

}
