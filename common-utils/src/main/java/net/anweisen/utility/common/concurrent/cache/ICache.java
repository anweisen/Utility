package net.anweisen.utility.common.concurrent.cache;

import net.anweisen.utility.common.annotations.ReplaceWith;
import net.anweisen.utility.common.collection.NamedThreadFactory;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @see com.google.common.cache.Cache
 * @since 1.0
 * @deprecated Use {@link com.google.common.cache.Cache} instead
 */
@Deprecated
@ReplaceWith("com.google.common.cache.Cache")
public interface ICache<K, V> {

	ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(2, new NamedThreadFactory(threadId -> String.format("CacheTask-%s", threadId)));

	boolean contains(@Nonnull K key);

	int size();

	default boolean isEmpty() {
		return size() == 0;
	}

	void clear();

	@Nonnull
	Map<K, V> values();

	default void forEach(@Nonnull BiConsumer<? super K, ? super V> action) {
		values().forEach(action);
	}

}
