package net.anweisen.utilities.commons.logging.internal;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.ILoggerFactory;
import net.anweisen.utilities.commons.logging.JavaILogger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class JavaLoggerWrapperFactory implements ILoggerFactory {

	protected final Logger logger;

	public JavaLoggerWrapperFactory(@Nonnull Logger logger) {
		this.logger = logger;
	}

	@Nonnull
	@Override
	@CheckReturnValue
	public ILogger forName(@Nullable String name) {
		return new JavaLoggerWrapper(logger);
	}

	@Nonnull
	@Override
	@CheckReturnValue
	public ILogger forClass(@Nullable Class<?> clazz) {
		return new JavaLoggerWrapper(logger);
	}

	@Nonnull
	@Override
	public JavaILogger forJavaLogger(@Nonnull Logger logger) {
		return new JavaLoggerWrapper(logger);
	}

}
