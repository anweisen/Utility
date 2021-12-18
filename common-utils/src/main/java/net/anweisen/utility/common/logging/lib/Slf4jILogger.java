package net.anweisen.utility.common.logging.lib;

import net.anweisen.utility.common.logging.ILogger;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public interface Slf4jILogger extends ILogger, Logger {

	@Override
	boolean isTraceEnabled();

	@Override
	boolean isDebugEnabled();

	@Override
	boolean isInfoEnabled();

	@Override
	boolean isWarnEnabled();

	@Override
	boolean isErrorEnabled();

	@Override
	void trace(@Nullable String message, @Nonnull Object... args);

	@Override
	void debug(@Nullable String message, @Nonnull Object... args);

	@Override
	void info(@Nullable String message, @Nonnull Object... args);

	@Override
	void warn(@Nullable String message, @Nonnull Object... args);

	@Override
	void error(@Nullable String message, @Nonnull Object... args);

}