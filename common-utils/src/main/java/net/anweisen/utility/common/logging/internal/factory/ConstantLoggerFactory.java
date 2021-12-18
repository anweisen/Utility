package net.anweisen.utility.common.logging.internal.factory;

import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.common.logging.ILoggerFactory;
import net.anweisen.utility.common.logging.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public class ConstantLoggerFactory implements ILoggerFactory {

	protected final ILogger logger;

	public ConstantLoggerFactory(@Nonnull ILogger logger) {
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
