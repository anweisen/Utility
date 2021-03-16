package net.anweisen.utilities.commons.logging;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class SimpleLogger extends AbstractLogger {

	protected final PrintStream stream = System.err;
	protected final String name;

	protected LogLevel level = LogLevel.INFO;

	public SimpleLogger(@Nullable String name) {
		this.name = name;
	}

	public SimpleLogger() {
		this.name = null;
	}

	@Override
	public void log(@Nonnull LogLevel level, @Nullable String message, @Nonnull Object... args) {
		if (!shouldShowMessage(level)) return;
		stream.println(getLogMessage(level, formatMessage(message, args), name));
		for (Object arg : args) {
			if (!(arg instanceof Throwable)) continue;
			((Throwable)arg).printStackTrace(stream);
		}
	}

	@Nonnull
	@Override
	public ILogger setMinLevel(@Nonnull LogLevel level) {
		this.level = level;
		return this;
	}

	@Nonnull
	@Override
	public LogLevel getMinLevel() {
		return level;
	}

	@Nullable
	@Override
	public String getName() {
		return name;
	}

	@Nonnull
	@CheckReturnValue
	public static String getLogMessage(@Nonnull LogLevel level, @Nonnull String message, @Nullable String name) {
		Thread thread = Thread.currentThread();
		String threadName = thread.getName();
		String time = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
		return name == null ?
			   String.format("[%s: %s/%s]: %s", time, threadName, level.name(), message) :
			   String.format("[%s: %s/%s] %s: %s", time, threadName, level.name(), name, message);
	}

	@Nonnull
	@CheckReturnValue
	public static String formatMessage(@Nullable Object messageObject, @Nonnull Object... args) {
		StringBuilder message = new StringBuilder(String.valueOf(messageObject));
		for (Object arg : args) {
			if (arg instanceof Throwable) continue;
			int index = message.indexOf("{}");
			if (index == -1) break;
			message.replace(index, index+2, String.valueOf(arg));
		}
		return message.toString();
	}

}
