package net.anweisen.utility.common.logging.internal.factory;

import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.common.logging.ILoggerFactory;
import net.anweisen.utility.common.logging.LogLevel;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public class Slf4jLoggerFactory implements ILoggerFactory {

	@Nonnull
	@Override
	public ILogger forName(@Nullable String name) {
		return ILogger.forSlf4jLogger(
			LoggerFactory.getLogger(name == null ? "Logger" : name)
		);
	}

	@Override
	public void setDefaultLevel(@Nonnull LogLevel level) {
	}

}
