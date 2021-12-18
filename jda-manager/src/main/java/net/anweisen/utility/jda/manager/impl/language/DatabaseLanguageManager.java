package net.anweisen.utility.jda.manager.impl.language;

import net.anweisen.utility.database.Database;
import net.anweisen.utility.database.access.CachedDatabaseAccess;
import net.anweisen.utility.database.access.DatabaseAccess;
import net.anweisen.utility.database.access.DatabaseAccessConfig;
import net.anweisen.utility.database.exception.DatabaseException;
import net.anweisen.utility.jda.manager.language.Language;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DatabaseLanguageManager extends AbstractLanguageManager {

	protected final DatabaseAccess<String> access;

	public DatabaseLanguageManager(@Nonnull DatabaseAccess<String> access) {
		this.access = access;
	}

	public DatabaseLanguageManager(@Nonnull Database database, @Nonnull String table, @Nonnull String keyField, @Nonnull String valueField) {
		this(CachedDatabaseAccess.newStringAccess(database, new DatabaseAccessConfig(table, keyField, valueField)));
	}

	@Nonnull
	@Override
	public Language getLanguage(@Nonnull Guild guild) {
		try {
			return getLanguageName(guild).map(this::getLanguageByIdentifier).orElseGet(this::getDefaultLanguage);
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
		access.setValue(guild.getId(), language.getIdentifier());
	}

}
