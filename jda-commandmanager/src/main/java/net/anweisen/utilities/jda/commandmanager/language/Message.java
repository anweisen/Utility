package net.anweisen.utilities.jda.commandmanager.language;

import net.anweisen.utilities.commons.logging.ILogger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Message {

	ILogger LOGGER = ILogger.forThisClass();

	@Nonnull
	String asString(@Nonnull Object... args);

	@Nonnull
	String[] asArray(@Nonnull Object... args);

	@Nonnull
	String asRandomString(@Nonnull Random random, @Nonnull Object... args);

	@Nonnull
	String asRandomString(@Nonnull Object... args);

	void setValue(@Nullable String value);
	void setValue(@Nullable String[] value);

	boolean isEmpty();

	@Nonnull
	String getName();

	static String unknown(@Nonnull String name) {
		return "Message(\"" + name + "\")";
	}

}
