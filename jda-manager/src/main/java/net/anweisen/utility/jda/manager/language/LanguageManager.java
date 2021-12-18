package net.anweisen.utility.jda.manager.language;

import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface LanguageManager {

	ILogger LOGGER = ILogger.forThisClass();

	@Nonnull
	Language getDefaultLanguage();

	@Nonnull
	Language getLanguage(@Nonnull Guild guild);

	@Nullable
	Language getLanguageByIdentifier(@Nonnull String identifier);

	@Nullable
	Language getLanguageByName(@Nonnull String name);

	@Nonnull
	Language getLanguage(@Nonnull CommandEvent event);

	void setLanguage(@Nonnull Guild guild, @Nonnull Language language) throws DatabaseException;

	@Nonnull
	LanguageManager setDefaultLanguage(@Nonnull String identifier);

	@Nonnull
	LanguageManager readFolder(@Nonnull String folder) throws IOException;

	@Nonnull
	LanguageManager readFolder(@Nonnull File folder) throws IOException;

	@Nonnull
	LanguageManager readFile(@Nonnull String filename) throws IOException;

	@Nonnull
	LanguageManager readFile(@Nonnull File file) throws IOException;

	@Nonnull
	LanguageManager readResource(@Nonnull String filename) throws IOException;

	@Nonnull
	LanguageManager register(@Nonnull Language language);

}
