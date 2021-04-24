package net.anweisen.utilities.jda.commandmanager.language;

import net.anweisen.utilities.commons.config.Document;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Language {

	void read(@Nonnull Document document);

	@Nonnull
	Message getMessage(@Nonnull String name);

	@Nonnull
	String getIdentifier();

	/**
	 * @return the first {@link #getNames() name} or the {@link #getIdentifier() identifier}
	 */
	@Nonnull
	default String getName() {
		String[] names = getNames();
		if (names.length == 0) return getIdentifier();
		return names[0];
	}

	@Nonnull
	String[] getNames();

}
