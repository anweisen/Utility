package net.anweisen.utilities.common.logging.internal.factory;

import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.common.logging.ILoggerFactory;
import net.anweisen.utilities.common.logging.lib.JavaILogger;
import net.anweisen.utilities.common.logging.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
