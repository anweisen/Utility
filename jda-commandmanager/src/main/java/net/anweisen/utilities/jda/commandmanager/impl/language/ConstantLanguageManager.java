package net.anweisen.utilities.jda.commandmanager.impl.language;

import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ConstantLanguageManager extends AbstractLanguageManager {

	public ConstantLanguageManager() {
	}

	public ConstantLanguageManager(@Nonnull Language language) {
		register(language);
	}


	@Nonnull
	@Override
	public Language getLanguage(@Nonnull Guild guild) {
		return getDefaultLanguage();
	}

}
