package net.anweisen.utilities.commons.logging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class DefaultLoggerFactory {

	private DefaultLoggerFactory() {
	}

	private static final Map<String, ILogger> registry = new ConcurrentHashMap<>();

	public static ILogger getOrCreateLogger(@Nullable Class<?> clazz) {
		return getOrCreateLogger(clazz == null ? null : clazz.getSimpleName());
	}

	public static ILogger getOrCreateLogger(@Nullable String name) {
		if (name == null) name = "anonymous";
		ILogger logger = registry.getOrDefault(name, new SimpleLogger(name));
		registry.put(name, logger);
		return logger;
	}

}
