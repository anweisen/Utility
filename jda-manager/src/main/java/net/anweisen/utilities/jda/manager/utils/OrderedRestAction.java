package net.anweisen.utilities.jda.manager.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.requests.RestAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class OrderedRestAction<T> implements RestAction<T> {

	protected final JDA api;
	protected final List<? extends Supplier<? extends RestAction<?>>> actions;

	public OrderedRestAction(@Nonnull JDA api, @Nonnull List<? extends Supplier<? extends RestAction<?>>> actions) {
		this.api = api;
		this.actions = actions;
	}

	@SafeVarargs
	public OrderedRestAction(@Nonnull JDA api, @Nonnull Supplier<? extends RestAction<?>>... actions) {
		this.api = api;
		this.actions = Arrays.asList(actions);
	}

	@Nonnull
	@Override
	public JDA getJDA() {
		return api;
	}

	@Nonnull
	@Override
	public RestAction<T> setCheck(@Nullable BooleanSupplier checks) {
		throw new UnsupportedOperationException("Checks are unsupported in OrderedRestAction");
	}

	@Override
	public void queue(@Nullable Consumer<? super T> success, @Nullable Consumer<? super Throwable> failure) {
		if (success == null) success = RestAction.getDefaultSuccess();
		if (failure == null) failure = RestAction.getDefaultFailure();
		next(new AtomicInteger(), success, failure);
	}

	@Override
	public T complete(boolean shouldQueue) throws RateLimitedException {
		try {
			return submit(shouldQueue).join();
		} catch (CompletionException ex) {
			if (ex.getCause() != null) {
				Throwable cause = ex.getCause();
				if (cause instanceof ErrorResponseException)
					throw (ErrorResponseException) cause.fillInStackTrace(); // this method will update the stacktrace to the current thread stack
				if (cause instanceof RateLimitedException)
					throw (RateLimitedException) cause.fillInStackTrace();
				if (cause instanceof RuntimeException)
					throw (RuntimeException) cause;
				if (cause instanceof Error)
					throw (Error) cause;
			}
			throw ex;
		}
	}

	@Nonnull
	@Override
	public CompletableFuture<T> submit(boolean shouldQueue) {
		CompletableFuture<T> future = new CompletableFuture<>();
		next(new AtomicInteger(), future::complete, future::completeExceptionally);
		return future;
	}

	protected void next(@Nonnull AtomicInteger index, @Nonnull Consumer<? super T> success, @Nonnull Consumer<? super Throwable> failure) {
		if (index.get() >= actions.size()) {
			success.accept(null);
			return;
		}

		RestAction<?> action = actions.get(index.get()).get();
		if (action == null) {
			index.getAndIncrement();
			next(index, success, failure);
			return;
		}

		action.queue(result -> {
			if (index.incrementAndGet() >= actions.size()) {
				success.accept((T) result);
				return;
			}

			next(index, success, failure);
		}, failure);
	}

}
