package net.anweisen.utility.jda.manager;

import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.jda.manager.hooks.option.CommandOptions;
import net.anweisen.utility.jda.manager.hooks.registered.CommandResolver;
import net.anweisen.utility.jda.manager.hooks.registered.CommandTask;
import net.anweisen.utility.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utility.jda.manager.language.LanguageManager;
import net.anweisen.utility.jda.manager.process.CommandPreProcessInfo;
import net.anweisen.utility.jda.manager.process.CommandResultHandler;
import net.dv8tion.jda.api.JDA;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandManager {

	ILogger LOGGER = ILogger.forThisClass();

	@Nonnull
	CommandManager register(@Nonnull Object command);

	@Nonnull
	default CommandManager register(@Nonnull Object... commands) {
		for (Object command : commands)
			register(command);
		return this;
	}

	@Nonnull
	default CommandManager register(@Nonnull Iterable<?> commands) {
		for (Object command : commands)
			register(command);
		return this;
	}

	@Nonnull
	CommandManager register(@Nonnull CommandTask task, @Nonnull CommandOptions options);

	@Nonnull
	Collection<RegisteredCommand> getRegisteredCommands();

	@Nonnull
	CommandManager setupSlashCommands(@Nonnull JDA jda);

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

	@Nonnull
	EventCreator getEventCreator();

	@Nonnull
	CommandManager setEventCreator(@Nonnull EventCreator creator);

	@Nonnull
	Collection<CommandResolver> getResolvers();

	@Nonnull
	CommandManager addResolver(@Nonnull CommandResolver resolver);

	boolean getReactToMentionPrefix();

	@Nonnull
	CommandManager setReactToMentionPrefix(boolean react);

	boolean getUseEmbeds();

	@Nonnull
	CommandManager setUseEmbeds(boolean useEmbeds);

	void handleCommand(@Nonnull CommandPreProcessInfo info);

}
