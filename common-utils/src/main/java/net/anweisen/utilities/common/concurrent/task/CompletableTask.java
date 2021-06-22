package net.anweisen.utilities.common.concurrent.task;

import net.anweisen.utilities.common.function.ExceptionallyFunction;
import net.anweisen.utilities.common.function.ExceptionallySupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CompletableTask<V> implements Task<V> {

	private static final ExecutorService SERVICE = Executors.newCachedThreadPool();

	private final Collection<TaskListener<V>> listeners = new ArrayList<>();
	private final CompletableFuture<V> future;

	private Throwable failure;

	public CompletableTask() {
		this(new CompletableFuture<>());
	}

	private CompletableTask(@Nonnull CompletableFuture<V> future) {
		this.future = future;
		this.future.exceptionally(ex -> {
			this.failure = ex;
			return null;
		});
	}

	public static <V> Task<V> callAsync(@Nonnull Callable<V> callable) {
		CompletableTask<V> task = new CompletableTask<>();
		SERVICE.execute(() -> {
			try {
				task.complete(callable.call());
			} catch (Throwable ex) {
				task.fail(ex);
			}
		});
		return task;
	}

	@Nonnull
	@Override
	public Task<V> addListener(@Nonnull TaskListener<V> listener) {
		if (this.future.isDone()) {
			if (this.future.isCancelled()) {
				listener.onCancelled(this);
			} else if (this.failure != null) {
				listener.onFailure(this, this.failure);
			} else {
				listener.onComplete(this, this.future.getNow(null));
			}
			return this;
		}

		this.listeners.add(listener);
		return this;
	}

	@Nonnull
	@Override
	public Task<V> clearListeners() {
		this.listeners.clear();
		return this;
	}

	@Override
	public V get(long time, @Nonnull TimeUnit timeUnit, V def) {
		try {
			return this.get(time, timeUnit);
		} catch (InterruptedException | ExecutionException | TimeoutException exception) {
			return def;
		}
	}

	public void fail(Throwable throwable) {
		this.failure = throwable;
		this.future.completeExceptionally(throwable);
		for (TaskListener<V> listener : this.listeners) {
			listener.onFailure(this, throwable);
		}
	}

	@Override
	public V call() {
		if (this.future.isDone()) {
			return this.future.getNow(null);
		}
		throw new UnsupportedOperationException("Use #complete in the CompletableTask");
	}

	public void complete(V value) {
		this.future.complete(value);
		for (TaskListener<V> listener : this.listeners) {
			listener.onComplete(this, value);
		}
	}

	@Override
	public boolean cancel(boolean b) {
		if (this.future.isCancelled()) {
			return false;
		}

		if (this.future.cancel(b)) {
			for (TaskListener<V> listener : this.listeners) {
				listener.onCancelled(this);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isCancelled() {
		return this.future.isCancelled();
	}

	@Override
	public boolean isDone() {
		return this.future.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return this.future.get();
	}

	@Override
	public V get(long l, @Nonnull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return this.future.get(l, unit);
	}

	@Nonnull
	@Override
	public <R> Task<R> map(@Nullable Function<? super V, ? extends R> mapper) {
		CompletableTask<R> task = new CompletableTask<>();
		this.future.thenAccept(v -> {
			try {
				task.complete(mapper == null ? null : mapper.apply(v));
			} catch (Throwable ex) {
				task.fail(ex);
			}
		});
		this.onFailure(task.future::completeExceptionally);
		this.onCancelled(otherTask -> task.cancel(true));
		return task;
	}

}
