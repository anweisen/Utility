package net.anweisen.utilities.commons.logging;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ILoggerFactory {

	@Nonnull
	@CheckReturnValue
	ILogger forName(@Nullable String name);

	@Nonnull
	@CheckReturnValue
	ILogger forClass(@Nullable Class<?> clazz);

	@Nonnull
	@CheckReturnValue
	JavaILogger forJavaLogger(@Nonnull Logger logger);

}
