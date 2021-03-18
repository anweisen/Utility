package net.anweisen.utilities.commons.logging.internal;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractLogger implements ILogger {

	@Override
	public void log(@Nonnull LogLevel level, @Nullable Object message, @Nonnull Object... args) {
		log(level, String.valueOf(message), args);
	}

	@Override
	public void error(@Nullable String message, @Nonnull Object... args) {
		log(LogLevel.ERROR, message, args);
	}

	@Override
	public void error(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.ERROR, message, args);
	}

	@Override
	public void warn(@Nullable String message, @Nonnull Object... args) {
		log(LogLevel.WARN, message, args);
	}

	@Override
	public void warn(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.WARN, message, args);
	}

	@Override
	public void info(@Nullable String message, @Nonnull Object... args) {
		log(LogLevel.INFO, message, args);
	}

	@Override
	public void info(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.INFO, message, args);
	}

	@Override
	public void status(@Nullable String message, @Nonnull Object... args) {
		log(LogLevel.STATUS, message, args);
	}

	@Override
	public void status(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.STATUS, message, args);
	}

	@Override
	public void debug(@Nullable String message, @Nonnull Object... args) {
		log(LogLevel.DEBUG, message, args);
	}

	@Override
	public void debug(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.DEBUG, message, args);
	}

	public boolean shouldShowMessage(@Nonnull LogLevel level) {
		return level.showAtLoggerLevel(getMinLevel());
	}

}
