package net.anweisen.utilities.common.logging.lib;

import net.anweisen.utilities.common.logging.ILogger;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public abstract class Slf4jILogger implements ILogger, Logger {

	@Override
	public abstract boolean isTraceEnabled();

	@Override
	public abstract boolean isDebugEnabled();

	@Override
	public abstract boolean isInfoEnabled();

	@Override
	public abstract boolean isWarnEnabled();

	@Override
	public abstract boolean isErrorEnabled();

	@Override
	public abstract void trace(@Nullable Object message, @Nonnull Object... args);

	@Override
	public abstract void trace(@Nullable String message, @Nonnull Object... args);

	@Override
	public abstract void debug(@Nullable Object message, @Nonnull Object... args);

	@Override
	public abstract void debug(@Nullable String message, @Nonnull Object... args);

	@Override
	public abstract void info(@Nullable Object message, @Nonnull Object... args);

	@Override
	public abstract void info(@Nullable String message, @Nonnull Object... args);

	@Override
	public abstract void warn(@Nullable Object message, @Nonnull Object... args);

	@Override
	public abstract void warn(@Nullable String message, @Nonnull Object... args);

	@Override
	public abstract void error(@Nullable Object message, @Nonnull Object... args);

	@Override
	public abstract void error(@Nullable String message, @Nonnull Object... args);

}