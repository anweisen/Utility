package net.anweisen.utilities.jda.manager.impl.language;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.misc.FileUtils;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.language.Language;
import net.anweisen.utilities.jda.manager.language.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
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
	public LanguageManager setDefaultLanguage(@Nonnull String identifier) {
		Language language = getLanguageByIdentifier(identifier);
		if (language == null) throw new IllegalArgumentException("No such language '" + identifier + "'");
		defaultLanguage = language;
		return this;
	}

	@Nullable
	@Override
	public Language getLanguageByIdentifier(@Nonnull String identifier) {
		return languages.get(identifier);
	}

	@Nullable
	@Override
	public Language getLanguageByName(@Nonnull String name) {
		for (Language language : languages.values()) {
			if (language.getIdentifier().equalsIgnoreCase(name))
				return language;
			for (String current : language.getNames()) {
				if (current.equalsIgnoreCase(name))
					return language;
			}
		}
		return null;
	}

	@Nonnull
	@Override
	public Language getLanguage(@Nonnull CommandEvent event) {
		return event.isGuild() ? getLanguage(event.getGuild()) : getDefaultLanguage();
	}

	@Override
	public void setLanguage(@Nonnull Guild guild, @Nonnull Language language) throws DatabaseException {
		throw new UnsupportedOperationException(this.getClass().getSimpleName() + " does not support custom guild languages");
	}

	@Nonnull
	@Override
	public LanguageManager readFolder(@Nonnull String folder) throws IOException {
		return readFile(new File(folder));
	}

	@Nonnull
	@Override
	public LanguageManager readFolder(@Nonnull File folder) throws IOException {
		for (File file : folder.listFiles()) {
			readFile(file);
		}
		return this;
	}

	@Nonnull
	@Override
	public LanguageManager readFile(@Nonnull String filename) throws IOException {
		return readFile(new File(filename));
	}

	@Nonnull
	@Override
	public LanguageManager readFile(@Nonnull File file) throws IOException {
		Document document = Document.readJsonFile(file);
		Language language = languages.computeIfAbsent(FileUtils.getFileName(file), LanguageImpl::new);
		if (defaultLanguage == null) defaultLanguage = language;
		language.read(document);
		return this;
	}

	@Nonnull
	@Override
	public LanguageManager readResource(@Nonnull String filename) throws IOException {
		InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
		if (input == null) throw new FileNotFoundException("No such resource \"" + filename + '"');
		Document document = Document.parseJson(new InputStreamReader(input));
		Language language = languages.computeIfAbsent(FileUtils.getFileName(filename), LanguageImpl::new);
		if (defaultLanguage == null) defaultLanguage = language;
		language.read(document);
		return this;
	}

	@Nonnull
	@Override
	public LanguageManager register(@Nonnull Language language) {
		languages.put(language.getIdentifier(), language);
		if (defaultLanguage == null) defaultLanguage = language;
		return this;
	}

}
