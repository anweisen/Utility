package net.anweisen.utilities.common.logging;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum LogLevel {

	TRACE   (1, "TRACE",  "trace",  Level.FINEST),
	DEBUG   (2, "DEBUG",  "debug",  Level.FINE),
	STATUS  (3, "STATUS", "status", Level.CONFIG),
	INFO    (4, "INFO",   "info",   Level.INFO),
	WARN    (5, "WARN",   "warn",   Level.WARNING),
	ERROR   (6, "ERROR",  "error",  Level.SEVERE);

	private final String uppercaseName, lowercaseName;
	private final Level level;
	private final int value;

	LogLevel(int value, @Nonnull String uppercaseName, @Nonnull String lowercaseName, @Nonnull Level level) {
		this.uppercaseName = uppercaseName;
		this.lowercaseName = lowercaseName;
		this.level = level;
		this.value = value;
	}

	@Nonnull
	public Level getJavaUtilLevel() {
		return level;
	}

	@CheckReturnValue
	public boolean isShownAtLoggerLevel(@Nonnull LogLevel loggerLevel) {
		return this.getValue() >= loggerLevel.getValue();
	}

	public int getValue() {
		return value;
	}

	@Nonnull
	public String getLowerCaseName() {
		return lowercaseName;
	}

	@Nonnull
	public String getUpperCaseName() {
		return uppercaseName;
	}

	@Nonnull
	public static LogLevel fromJavaLevel(@Nonnull Level level) {
		for (LogLevel logLevel : values()) {
			if (logLevel.getJavaUtilLevel().intValue() == level.intValue())
				return logLevel;
		}
		return INFO;
	}

	@Nonnull
	public static LogLevel fromValue(int value) {
		for (LogLevel level : values()) {
			if (level.getValue() == value)
				return level;
		}
		return INFO;
	}

	@Nonnull
	public static LogLevel fromName(@Nonnull String name) {
		for (LogLevel level : values()) {
			if (level.getUpperCaseName().equalsIgnoreCase(name))
				return level;
		}
		return INFO;
	}
}
