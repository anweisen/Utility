package net.anweisen.utilities.jda.manager.impl.language;

import net.anweisen.utilities.common.config.Document;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class FallbackLanguage extends LanguageImpl {

	public FallbackLanguage() {
		super("fallback");
		names = new String[] { "Fallback (English)" };

		getMessage("error-error").setValue("Something went wrong");
		getMessage("error-incorrect-arguments").setValue("Please use `{0}`");
		getMessage("error-incorrect-arguments-multiple").setValue("Please use:");
		getMessage("error-incorrect-arguments-multiple-entry").setValue("• `{0}`");
		getMessage("error-cooldown").setValue("Please wait `{0}`");
		getMessage("error-invalid-scope-private").setValue("You can only use this command in private chat");
		getMessage("error-invalid-scope-guild").setValue("You can only use this command in a guild chat");
		getMessage("error-missing-permission").setValue("You do not have enough permissions to execute that command");
		getMessage("error-missing-team-role").setValue("You do not have enough permissions to execute that command");
		getMessage("error-invalid-number-positive").setValue("The given number must be positive");
		getMessage("error-invalid-number-negative").setValue("The given number must be negative");
		getMessage("error-invalid-number-range").setValue("The given number must be between **{0}** and **{1}**");
		getMessage("error-invalid-number-smaller").setValue("The given number must be smaller than **{0}**");
		getMessage("error-invalid-number-greater").setValue("The given number must be greater than **{0}**");
		getMessage("error-invalid-member").setValue("This member does not exist");
		getMessage("error-invalid-member-bot").setValue("Please choose a bot");
		getMessage("error-invalid-member-user").setValue("You cannot choose a bot");

		getMessage("command-set-team-role").setValue("The team role was set to {0}");
		getMessage("command-remove-team-role").setValue("The team role was removed");
		getMessage("command-set-prefix-success").setValue("The Prefix was set to `{0}`");
		getMessage("command-set-prefix-max-length").setValue("The Prefix cannot be longer than **{0}** characters");
		getMessage("command-set-language-invalid").setValue("This language does not exist");
		getMessage("command-set-language-success").setValue("The language was set to **{0}**");
	}

	@Override
	public void read(@Nonnull Document document) {
		throw new UnsupportedOperationException("FallbackLanguage does not support reading");
	}

}
