package net.anweisen.utilities.common.logging.internal;

import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.common.logging.LogLevel;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public class RedirectedOutputStream extends OutputStream {

	private final StringBuilder builder = new StringBuilder();
	private final LogLevel level;
	private final ILogger logger;

	public RedirectedOutputStream(@Nonnull LogLevel level, @Nonnull ILogger logger) {
		this.level = level;
		this.logger = logger;
	}

	@Override
	public void write(int b) {
		char c = (char) b;
		if (c == '\n') {
			if (builder.length() > 0) {
				logger.log(level, builder);
				builder.setLength(0);
			}
		} else {
			builder.append(c);
		}
	}

}
