package net.anweisen.utilities.jda.commandmanager.arguments;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class InvalidArgumentParseValueException extends Exception {

	public InvalidArgumentParseValueException(@Nonnull String message) {
		super(message);
	}

}
