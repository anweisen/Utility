package net.anweisen.utilities.commons.logging.internal;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.ILoggerFactory;
import net.anweisen.utilities.commons.logging.JavaILogger;

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

	@Nonnull
	@Override
	public ILogger forClass(@Nullable Class<?> clazz) {
		return logger;
	}

	@Nonnull
	@Override
	public JavaILogger forJavaLogger(@Nonnull Logger javaLogger) {
		return logger;
	}

}
