package net.anweisen.utilities.commons.logging.internal;

import javax.annotation.Nonnull;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Because you can't change the level of a plugin logger properly, we create a wrapper for the plugin logger
 * and map all levels below {@link Level#INFO} to {@link Level#INFO}, if they are loggable.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class BukkitLoggerWrapper extends JavaLoggerWrapper {

	public BukkitLoggerWrapper(@Nonnull Logger logger) {
		super(logger);
	}

	@Nonnull
	@Override
	protected Level mapLevel(@Nonnull Level level) {
		if (isLoggable(level) && level.intValue() < Level.INFO.intValue())
			return Level.INFO;
		return level;
	}

}
