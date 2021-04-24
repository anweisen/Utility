package net.anweisen.utilities.jda.commandmanager.arguments;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ArgumentParser<T> {

	/**
	 * Returning {@code null} or throwing an exception will signal that the input is incorrect and does not match the syntax/usage.
	 *
	 * @param event The event this argument is needed in for additional information
	 * @param input The input given, if multi words were used they were merged using the {@link ParserOptions#getMultiWordSeparator() separator}
	 * @return The object parsed from the string, must be instance {@code <T>}
	 *
	 * @throws Exception
	 *         When the input is incorrect or something goes wrong internally.
	 *         Try catches are unnecessary inside of parsers when you would return {@code null} then.
	 */
	@Nullable
	T parse(@Nonnull CommandEvent event, @Nonnull String input) throws Exception;

	@Nonnull
	default ParserOptions options() {
		return new ParserOptions();
	}

}
