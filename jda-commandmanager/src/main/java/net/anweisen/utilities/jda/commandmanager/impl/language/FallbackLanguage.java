package net.anweisen.utilities.jda.commandmanager.impl.language;

import net.anweisen.utilities.commons.config.Document;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class FallbackLanguage extends LanguageImpl {

	public FallbackLanguage() {
		super("fallback");
		names = new String[] { "English", "Fallback" };

		getMessage("error-error").setValue("Something went wrong");
		getMessage("error-incorrect-arguments").setValue("Please use `{0}`");
		getMessage("error-cooldown").setValue("Please wait `{0}`s");
		getMessage("error-invalid-scope-private").setValue("You can only use this command in private chat");
		getMessage("error-invalid-scope-guild").setValue("You can only use this command in a guild chat");
		getMessage("error-missing-permission").setValue("You do not have enough permissions to execute that command");
		getMessage("error-missing-team-role").setValue("You do not have enough permissions to execute that command");
	}

	@Override
	public void read(@Nonnull Document document) {
		throw new UnsupportedOperationException("FallbackLanguage does not support reading");
	}

}
