package net.anweisen.utilities.jda.commandmanager.language;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.jda.commandmanager.message.MessageInfo;
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
	Language getLanguage(@Nonnull String name);

	@Nonnull
	Language getLanguage(@Nonnull MessageInfo info);

	void setLanguage(@Nonnull Guild guild, @Nonnull Language language) throws DatabaseException;

	@Nonnull
	LanguageManager setDefaultLanguage(@Nonnull String name);

	@Nonnull
	LanguageManager readLanguages(@Nonnull String folder) throws IOException;

	@Nonnull
	LanguageManager readLanguages(@Nonnull File folder) throws IOException;

	@Nonnull
	LanguageManager readLanguage(@Nonnull String filename) throws IOException;

	@Nonnull
	LanguageManager readLanguage(@Nonnull File file) throws IOException;

}
