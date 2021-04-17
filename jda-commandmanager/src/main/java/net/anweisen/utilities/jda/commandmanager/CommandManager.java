package net.anweisen.utilities.jda.commandmanager;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.jda.commandmanager.language.LanguageManager;
import net.anweisen.utilities.jda.commandmanager.process.CommandPreProcessInfo;
import net.anweisen.utilities.jda.commandmanager.process.CommandResultHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandManager {

	ILogger LOGGER = ILogger.forThisClass();

	@Nonnull
	CommandManager register(@Nonnull Object command);

	@Nonnull
	CommandManager register(@Nonnull Object... commands);

	@Nonnull
	PrefixProvider getPrefixProvider();

	@Nonnull
	CommandManager setPrefixProvider(@Nonnull PrefixProvider provider);

	@Nonnull
	ParserContext getParserContext();

	@Nonnull
	CommandManager setParserContext(@Nonnull ParserContext context);

	@Nullable
	CommandResultHandler getResultHandler();

	@Nonnull
	CommandManager setResultHandler(@Nullable CommandResultHandler handler);

	@Nonnull
	LanguageManager getLanguageManager();

	@Nonnull
	CommandManager setLanguageManager(@Nonnull LanguageManager manager);

	@Nullable
	TeamRoleManager getTeamRoleManager();

	@Nonnull
	CommandManager setTeamRoleManager(@Nullable TeamRoleManager manager);

	boolean getReactToMentionPrefix();

	@Nonnull
	CommandManager setReactToMentionPrefix(boolean react);

	void handleCommand(@Nonnull CommandPreProcessInfo info);

}
