package net.anweisen.utility.jda.manager.arguments;

import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @param <T> The type this parser parses
 * @param <I> An extra information object parsed and used by this parser
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ArgumentParser<T, I> {

	/**
	 * Returning {@code null} (if the options dont permit nullable values) or throwing an exception will signal that the input is incorrect and does not match the syntax/usage.
	 *
	 * @param event The event this argument is needed in for additional information
	 * @param info  The info container parsed by {@link #parseInfoContainer(String)}, or {@code null} if no extra info was given in the command usage
	 * @param input The input given, if multi words were used they were merged using the {@link ParserOptions#getMultiWordSeparator() separator}
	 * @return The object parsed from the string, must be instance {@code <T>}
	 * @throws Exception When the input is incorrect or something goes wrong internally.
	 *                   Try catches are unnecessary inside of parsers when you would return {@code null} then.
	 */
	@Nullable
	T parse(@Nonnull CommandEvent event, @Nullable I info, @Nonnull String input) throws Exception;

	@Nonnull
	default OptionType asSlashCommandType() {
		return OptionType.STRING;
	}

	default boolean validateInfoContainer(@Nonnull I info, @Nonnull T context) {
		return true;
	}

	@Nullable
	default I parseInfoContainer(@Nonnull String input) throws Exception {
		throw new IllegalArgumentException(this.getClass().getSimpleName() + " does not support extra information");
	}

	@Nullable
	default Tuple<String, Object[]> getErrorMessage(@Nullable I info, @Nullable T context) {
		return null;
	}

	@Nonnull
	default ParserOptions options() {
		return new ParserOptions();
	}

	//
	// The following methods should not be overwritten.
	// Changing their behaviour can result in issues with the command management and execution.
	//

	@Nullable
	@SuppressWarnings("unchecked")
	default T parseCasted(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		return parse(event, (I) info, input);
	}

	@Nullable
	@SuppressWarnings("unchecked")
	default Tuple<String, Object[]> getErrorMessageCasted(@Nullable Object info, @Nullable Object context) {
		return getErrorMessage((I) info, (T) context);
	}

	@SuppressWarnings("unchecked")
	default boolean validateInfoContainerCasted(@Nonnull Object info, @Nonnull Object context) {
		return validateInfoContainer((I) info, (T) context);
	}

}
