package net.anweisen.utilities.jda.manager.impl.entities.action;

import net.anweisen.utilities.jda.manager.hooks.action.MessageResponse;
import net.anweisen.utilities.jda.manager.hooks.action.ReplyMessageAction;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public abstract class AbstractReplyMessageAction<T extends RestAction<R>, R> implements ReplyMessageAction {

	protected final T action;

	public AbstractReplyMessageAction(@Nonnull T action) {
		this.action = action;
	}

	@Nonnull
	public abstract Function<R, MessageResponse> getResponseMapper();

	@Nonnull
	@Override
	public JDA getJDA() {
		return action.getJDA();
	}

	@Override
	public void queue(@Nullable Consumer<? super MessageResponse> success, @Nullable Consumer<? super Throwable> failure) {
		action.queue(success == null ? null : result -> success.accept(getResponseMapper().apply(result)), failure);
	}

	@Override
	public MessageResponse complete(boolean shouldQueue) throws RateLimitedException {
		R result = action.complete(shouldQueue);
		return result == null ? null : getResponseMapper().apply(result);
	}

	@Nonnull
	@Override
	public CompletableFuture<MessageResponse> submit(boolean shouldQueue) {
		CompletableFuture<MessageResponse> future = new CompletableFuture<>();
		action.submit(shouldQueue).whenComplete((result, failure) -> {
			if (failure != null) {
				future.completeExceptionally(failure);
			} else {
				future.complete(result == null ? null : getResponseMapper().apply(result));
			}
		});
		return future;
	}
}
