package net.anweisen.utilities.common.logging;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ILoggerFactory {

	@Nonnull
	@CheckReturnValue
	ILogger forName(@Nullable String name);

	void setDefaultLevel(@Nonnull LogLevel level);

}
