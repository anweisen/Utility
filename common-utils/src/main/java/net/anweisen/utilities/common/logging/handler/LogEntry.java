package net.anweisen.utilities.common.logging.handler;

import net.anweisen.utilities.common.logging.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class LogEntry {

	private Instant timestamp;
	private String threadName;
	private String message;
	private LogLevel level;
	private Throwable exception;

	public LogEntry(@Nonnull Instant timestamp, @Nonnull String threadName, @Nonnull String message, @Nonnull LogLevel level, @Nullable Throwable exception) {
		this.timestamp = timestamp;
		this.threadName = threadName;
		this.message = message;
		this.level = level;
		this.exception = exception;
	}

	@Nonnull
	public Instant getTimestamp() {
		return timestamp;
	}

	@Nonnull
	public String getThreadName() {
		return threadName;
	}

	@Nonnull
	public String getMessage() {
		return message;
	}

	@Nonnull
	public LogLevel getLevel() {
		return level;
	}

	@Nullable
	public Throwable getException() {
		return exception;
	}
}
