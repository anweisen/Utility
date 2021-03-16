package net.anweisen.utilities.commons.logging;

import net.anweisen.utilities.commons.misc.ReflectionUtils;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ILogger {

	void log(@Nonnull LogLevel level, @Nonnull String message, @Nonnull Object... args);
	void log(@Nonnull LogLevel level, @Nullable Object message, @Nonnull Object... args);

	void error(@Nonnull String message, @Nonnull Object... args);
	void error(@Nullable Object message, @Nonnull Object... args);

	void warn(@Nonnull String message, @Nonnull Object... args);
	void warn(@Nullable Object message, @Nonnull Object... args);

	void info(@Nonnull String message, @Nonnull Object... args);
	void info(@Nullable Object message, @Nonnull Object... args);

	void status(@Nonnull String message, @Nonnull Object... args);
	void status(@Nullable Object message, @Nonnull Object... args);

	void debug(@Nonnull String message, @Nonnull Object... args);
	void debug(@Nullable Object message, @Nonnull Object... args);

	@Nonnull
	LogLevel getMinLevel();

	@Nonnull
	ILogger setMinLevel(@Nonnull LogLevel level);

	@Nullable
	String getName();

	@CheckReturnValue
	boolean shouldShowMessage(@Nonnull LogLevel level);

	@Nonnull
	@CheckReturnValue
	static ILogger forName(@Nullable String name) {
		return DefaultLoggerFactory.getOrCreateLogger(name);
	}

	@Nonnull
	@CheckReturnValue
	static ILogger forClass(@Nullable Class<?> clazz) {
		return DefaultLoggerFactory.getOrCreateLogger(clazz);
	}

	@Nonnull
	@CheckReturnValue
	static ILogger forThisClass() {
		return forClass(ReflectionUtils.getCaller());
	}

}
