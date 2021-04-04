package net.anweisen.utilities.commons.logging;

import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum LogLevel {

	DEBUG(Level.FINE),
	STATUS(Level.CONFIG),
	INFO(Level.INFO),
	WARN(Level.WARNING),
	ERROR(Level.SEVERE);

	LogLevel(@Nonnull Level level) {
		this.level = level;
	}

	private final Level level;

	@Nonnull
	public Level getJavaUtilLevel() {
		return level;
	}

	public boolean showAtLoggerLevel(@Nonnull LogLevel loggerLevel) {
		return this.ordinal() >= loggerLevel.ordinal();
	}

	@Nonnull
	public static LogLevel fromJavaUtilLevel(@Nonnull Level level) {
		for (LogLevel logLevel : values()) {
			if (logLevel.getJavaUtilLevel().intValue() == level.intValue())
				return logLevel;
		}
		return INFO;
	}

}
