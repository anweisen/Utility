package net.anweisen.utilities.commons.logging;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class JavaILogger extends Logger implements ILogger {

	protected JavaILogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}

	@Nonnull
	@Override
	public abstract JavaILogger setMinLevel(@Nonnull LogLevel level);

}
