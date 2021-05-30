package net.anweisen.utilities.commons.logging;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.logging.Logger;

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
