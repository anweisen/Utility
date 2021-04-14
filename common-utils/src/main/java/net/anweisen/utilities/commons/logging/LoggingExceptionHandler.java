package net.anweisen.utilities.commons.logging;

import javax.annotation.Nonnull;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class LoggingExceptionHandler implements UncaughtExceptionHandler {

	private final ILogger logger;

	public LoggingExceptionHandler(@Nonnull ILogger logger) {
		this.logger = logger;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		logger.error("An error occurred in thread {}", thread.getName(), ex);
	}

}
