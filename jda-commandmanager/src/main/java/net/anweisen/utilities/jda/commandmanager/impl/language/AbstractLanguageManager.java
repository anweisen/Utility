package net.anweisen.utilities.jda.commandmanager.impl.language;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.GsonDocument;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.jda.commandmanager.message.MessageInfo;
import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.anweisen.utilities.jda.commandmanager.language.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractLanguageManager implements LanguageManager {

	protected final Map<String, Language> languages = new HashMap<>();
	protected Language defaultLanguage;

	@Nonnull
	@Override
	public Language getDefaultLanguage() {
		if (defaultLanguage == null) throw new IllegalStateException("No default language set");
		return defaultLanguage;
	}

	@Nonnull
	@Override
	public LanguageManager setDefaultLanguage(@Nonnull String name) {
		Language language = getLanguage(name);
		if (language == null) throw new IllegalArgumentException("No such language '" + name + "'");
		defaultLanguage = language;
		return this;
	}

	@Nullable
	@Override
	public Language getLanguage(@Nonnull String name) {
		return languages.get(name);
	}

	@Nonnull
	@Override
	public Language getLanguage(@Nonnull MessageInfo info) {
		return info.isGuild() ? getLanguage(info.getGuild()) : getDefaultLanguage();
	}

	@Override
	public void setLanguage(@Nonnull Guild guild, @Nonnull Language language) throws DatabaseException {
		throw new UnsupportedOperationException(this.getClass().getSimpleName() + " does not support custom guild languages");
	}

	@Nonnull
	@Override
	public LanguageManager readLanguages(@Nonnull String folder) throws IOException {
		return readLanguage(new File(folder));
	}

	@Nonnull
	@Override
	public LanguageManager readLanguages(@Nonnull File folder) throws IOException {
		for (File file : folder.listFiles()) {
			readLanguage(file);
		}
		return this;
	}

	@Nonnull
	@Override
	public LanguageManager readLanguage(@Nonnull String filename) throws IOException {
		return readLanguage(new File(filename));
	}

	@Nonnull
	@Override
	public LanguageManager readLanguage(@Nonnull File file) throws IOException {
		Document document = new GsonDocument(file);
		Language language = languages.computeIfAbsent(file.toPath().getFileName().toString(), LanguageImpl::new);
		if (defaultLanguage == null) defaultLanguage = language;
		language.read(document);
		return this;
	}

}
