package net.anweisen.utilities.common.logging.handler;

import net.anweisen.utilities.common.logging.LogLevel;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class HandledSyncLogger extends HandledLogger {

	public HandledSyncLogger(@Nonnull LogLevel initialLevel) {
		super(initialLevel);
	}

	@Override
	protected void log0(@Nonnull LogEntry entry) {
		logNow(entry);
	}
}
