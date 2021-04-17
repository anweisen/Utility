package net.anweisen.utilities.jda.commandmanager.impl.language;

import net.anweisen.utilities.database.access.DatabaseAccess;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DatabaseLanguageManager extends AbstractLanguageManager {

	private final DatabaseAccess<String> access;

	public DatabaseLanguageManager(@Nonnull DatabaseAccess<String> access) {
		this.access = access;
	}

	@Nonnull
	@Override
	public Language getLanguage(@Nonnull Guild guild) {
		try {
			return getLanguageName(guild).map(this::getLanguage).orElseGet(this::getDefaultLanguage);
		} catch (DatabaseException ex) {
			LOGGER.error("Unable to get language for guild {}", guild, ex);
			return getDefaultLanguage();
		}
	}

	@Nonnull
	public Optional<String> getLanguageName(@Nonnull Guild guild) throws DatabaseException {
		return access.getValueOptional(guild.getId());
	}

	@Override
	public void setLanguage(@Nonnull Guild guild, @Nonnull Language language) throws DatabaseException {
		access.setValue(guild.getId(), language.getName());
	}

}
