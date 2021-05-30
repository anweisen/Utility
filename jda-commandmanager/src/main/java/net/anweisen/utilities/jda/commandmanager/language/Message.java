package net.anweisen.utilities.jda.commandmanager.language;

import net.anweisen.utilities.commons.common.IRandom;
import net.anweisen.utilities.commons.logging.ILogger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Message {

	ILogger LOGGER = ILogger.forThisClass();
	Collection<String> SEEN_UNKNOWN = new ArrayList<>();

	@Nonnull
	String asString(@Nonnull Object... args);

	@Nonnull
	String[] asArray(@Nonnull Object... args);

	@Nonnull
	String asRandomString(@Nonnull Random random, @Nonnull Object... args);

	@Nonnull
	String asRandomString(@Nonnull IRandom random, @Nonnull Object... args);

	@Nonnull
	String asRandomString(@Nonnull Object... args);

	void setValue(@Nullable String value);
	void setValue(@Nullable String[] value);

	boolean isEmpty();

	@Nonnull
	String getName();

	static String unknown(@Nonnull String name) {
		if (!SEEN_UNKNOWN.contains(name)) {
			SEEN_UNKNOWN.add(name);
			LOGGER.warn("Tried accessing unknown message '{}'", name);
		}

		return "Message(\"" + name + "\")";
	}

}
