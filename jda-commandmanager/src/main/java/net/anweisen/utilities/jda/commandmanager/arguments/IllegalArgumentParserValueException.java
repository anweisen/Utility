package net.anweisen.utilities.jda.commandmanager.arguments;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class IllegalArgumentParserValueException extends Exception {

	public IllegalArgumentParserValueException(@Nonnull String message) {
		super(message);
	}

}
