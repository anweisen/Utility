package net.anweisen.utilities.commons.common;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.3
 */
public class NamedThreadFactory implements ThreadFactory {

	private static final AtomicInteger poolNumber = new AtomicInteger(1);

	protected final int id = poolNumber.getAndIncrement();
	protected final IntFunction<String> naming;
	protected final ThreadGroup group;
	protected final AtomicInteger threadNumber = new AtomicInteger(1);

	public NamedThreadFactory(@Nonnull IntFunction<String> naming) {
		SecurityManager securityManager = System.getSecurityManager();
		group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.naming = naming;
	}

	public NamedThreadFactory(@Nonnull String prefix) {
		this(id -> String.format("%s-Thread-%s", prefix, id));
	}

	public Thread newThread(@Nonnull Runnable task) {
		Thread thread = new Thread(group, task, naming.apply(threadNumber.getAndIncrement()));
		if (thread.isDaemon())
			thread.setDaemon(false);
		if (thread.getPriority() != Thread.NORM_PRIORITY)
			thread.setPriority(Thread.NORM_PRIORITY);
		return thread;
	}

}
