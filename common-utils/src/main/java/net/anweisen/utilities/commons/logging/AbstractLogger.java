package net.anweisen.utilities.commons.logging;

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
	public void error(@Nonnull String message, @Nonnull Object... args) {
		log(LogLevel.ERROR, message, args);
	}

	@Override
	public void error(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.ERROR, message, args);
	}

	@Override
	public void warn(@Nonnull String message, @Nonnull Object... args) {
		log(LogLevel.WARN, message, args);
	}

	@Override
	public void warn(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.WARN, message, args);
	}

	@Override
	public void info(@Nonnull String message, @Nonnull Object... args) {
		log(LogLevel.INFO, message, args);
	}

	@Override
	public void info(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.INFO, message, args);
	}

	@Override
	public void status(@Nonnull String message, @Nonnull Object... args) {
		log(LogLevel.STATUS, message, args);
	}

	@Override
	public void status(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.STATUS, message, args);
	}

	@Override
	public void debug(@Nonnull String message, @Nonnull Object... args) {
		log(LogLevel.DEBUG, message, args);
	}

	@Override
	public void debug(@Nullable Object message, @Nonnull Object... args) {
		log(LogLevel.DEBUG, message, args);
	}

	@Override
	public boolean shouldShowMessage(@Nonnull LogLevel level) {
		return level.showAtLoggerLevel(getMinLevel());
	}

}
