package net.anweisen.utilities.commons.logging.internal.factory;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.ILoggerFactory;
import net.anweisen.utilities.commons.logging.JavaILogger;
import net.anweisen.utilities.commons.logging.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class ConstantLoggerFactory implements ILoggerFactory {

	protected final JavaILogger logger;

	public ConstantLoggerFactory(@Nonnull JavaILogger logger) {
		this.logger = logger;
	}

	@Nonnull
	@Override
	public ILogger forName(@Nullable String name) {
		return logger;
	}

	@Override
	public void setDefaultLevel(@Nonnull LogLevel level) {
		logger.setMinLevel(level);
	}

}
