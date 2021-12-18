package net.anweisen.utility.common.logging.handler;

import net.anweisen.utility.common.collection.NamedThreadFactory;
import net.anweisen.utility.common.logging.LogLevel;

import javax.annotation.Nonnull;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class HandledAsyncLogger extends HandledLogger {

	protected final Executor executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("AsyncLogTask"));

	public HandledAsyncLogger(@Nonnull LogLevel initialLevel) {
		super(initialLevel);
	}

	@Override
	protected void log0(@Nonnull LogEntry entry) {
		executor.execute(() -> logNow(entry));
	}

}
