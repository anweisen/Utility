package net.anweisen.utilities.commons.logging;

import net.anweisen.utilities.commons.logging.internal.DefaultLoggerFactory;
import net.anweisen.utilities.commons.logging.internal.JavaLoggerWrapper;
import net.anweisen.utilities.commons.misc.ReflectionUtils;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ILogger {

	class Holder {

		private static ILoggerFactory factory = new DefaultLoggerFactory();

		private Holder() {}

	}

	void log(@Nonnull LogLevel level, @Nullable String message, @Nonnull Object... args);
	void log(@Nonnull LogLevel level, @Nullable Object message, @Nonnull Object... args);

	void error(@Nullable String message, @Nonnull Object... args);
	void error(@Nullable Object message, @Nonnull Object... args);

	void warn(@Nullable String message, @Nonnull Object... args);
	void warn(@Nullable Object message, @Nonnull Object... args);

	void info(@Nullable String message, @Nonnull Object... args);
	void info(@Nullable Object message, @Nonnull Object... args);

	void status(@Nullable String message, @Nonnull Object... args);
	void status(@Nullable Object message, @Nonnull Object... args);

	void debug(@Nullable String message, @Nonnull Object... args);
	void debug(@Nullable Object message, @Nonnull Object... args);

	@Nonnull
	LogLevel getMinLevel();

	@Nonnull
	ILogger setMinLevel(@Nonnull LogLevel level);

	@Nullable
	String getName();

	@Nonnull
	@CheckReturnValue
	static ILogger forName(@Nullable String name) {
		return Holder.factory.forName(name);
	}

	@Nonnull
	@CheckReturnValue
	static ILogger forClass(@Nullable Class<?> clazz) {
		return Holder.factory.forClass(clazz);
	}

	@Nonnull
	@CheckReturnValue
	static ILogger forThisClass() {
		return forClass(ReflectionUtils.getCaller());
	}

	@Nonnull
	@CheckReturnValue
	static JavaILogger forJavaLogger(@Nonnull Logger logger) {
		return Holder.factory.forJavaLogger(logger);
	}

	static void setFactory(@Nonnull ILoggerFactory factory) {
		Holder.factory = factory;
	}

}
