package net.anweisen.utility.common.logging.internal;

import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.common.logging.LogLevel;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.5
 */
public class FallbackLogger implements ILogger {

	protected final PrintStream stream = System.err;
	protected final String name;

	protected LogLevel level = LogLevel.INFO;

	public FallbackLogger(@Nullable String name) {
		this.name = name;
	}

	public FallbackLogger() {
		this(null);
	}

	@Override
	public void log(@Nonnull LogLevel level, @Nullable String message, @Nonnull Object... args) {
		if (!isLevelEnabled(level)) return;
		stream.println(getLogMessage(level, ILogger.formatMessage(message, args), name));
		for (Object arg : args) {
			if (!(arg instanceof Throwable)) continue;
			((Throwable) arg).printStackTrace(stream);
		}
	}

	@Nonnull
	@Override
	public FallbackLogger setMinLevel(@Nonnull LogLevel level) {
		this.level = level;
		return this;
	}

	@Nonnull
	@Override
	public LogLevel getMinLevel() {
		return level;
	}

	@Nonnull
	@CheckReturnValue
	public static String getLogMessage(@Nonnull LogLevel level, @Nonnull String message, @Nullable String name) {
		Thread thread = Thread.currentThread();
		String threadName = thread.getName();
		String time = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
		return name == null ?
			String.format("[%s: %s/%s]: %s", time, threadName, level.getUpperCaseName(), message) :
			String.format("[%s: %s/%s] %s: %s", time, threadName, level.getUpperCaseName(), name, message);
	}

}
