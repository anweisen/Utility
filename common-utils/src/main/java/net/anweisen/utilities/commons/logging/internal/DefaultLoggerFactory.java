package net.anweisen.utilities.commons.logging.internal;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.ILoggerFactory;
import net.anweisen.utilities.commons.logging.JavaILogger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultLoggerFactory implements ILoggerFactory {

	protected final Map<String, ILogger> registry = new ConcurrentHashMap<>();

	@Nonnull
	@Override
	@CheckReturnValue
	public ILogger forClass(@Nullable Class<?> clazz) {
		return forName(clazz == null ? null : clazz.getSimpleName());
	}

	@Nonnull
	@Override
	@CheckReturnValue
	public ILogger forName(@Nullable String name) {
		String key = name == null ? "anonymous" : name;
		ILogger logger = registry.getOrDefault(key, new SimpleLogger(name));
		registry.put(key, logger);
		return logger;
	}

	@Nonnull
	@Override
	@CheckReturnValue
	public JavaILogger forJavaLogger(@Nonnull Logger logger) {
		return new JavaLoggerWrapper(logger);
	}

}
