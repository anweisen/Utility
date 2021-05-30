package net.anweisen.utilities.commons.cache;

import net.anweisen.utilities.commons.common.RunnableTimerTask;
import net.anweisen.utilities.commons.common.Tuple;
import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.misc.SimpleCollectionUtils;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public class CleanAndWriteAccessCache<K, V> implements AccessCache<K, V> {

	protected final Map<K, Tuple<Long, V>> cache = new ConcurrentHashMap<>();
	protected final Predicate<? super V> check;
	protected final Function<? super K, ? extends V> query;
	protected final Function<? super K, ? extends V> fallback;
	protected final BiConsumer<? super K, ? super V> writer;
	protected final long unusedTimeBeforeClean;
	protected final long cleanAndWriteInterval;
	protected final ILogger logger;

	public CleanAndWriteAccessCache(@Nonnull ILogger logger, @Nonnegative long unusedTimeBeforeClean, @Nonnegative long cleanAndWriteInterval, @Nonnull String taskName,
	                                @Nonnull Predicate<? super V> check, @Nonnull Function<? super K, ? extends V> fallback,
	                                @Nonnull Function<? super K, ? extends V> query, @Nonnull BiConsumer<? super K, ? super V> writer) {
		this.logger = logger;
		this.unusedTimeBeforeClean = unusedTimeBeforeClean;
		this.cleanAndWriteInterval = cleanAndWriteInterval;
		this.check = check;
		this.query = query;
		this.fallback = fallback;
		this.writer = writer;

		new Timer(taskName).schedule(new RunnableTimerTask(this::writeCache), cleanAndWriteInterval, cleanAndWriteInterval);
		Runtime.getRuntime().addShutdownHook(new Thread(this::writeCache));
	}

	public void writeCache() {
		logger.debug("Writing & Cleaning cache");
		cleanAndWrite(cache, unusedTimeBeforeClean, logger, check, writer);
	}

	@Nonnull
	@Override
	public V getData(@Nonnull K key) {
		Tuple<Long, V> cached = cache.get(key);
		if (cached != null) {
			cached.setFirst(System.currentTimeMillis());
			return cached.getSecond();
		}

		try {
			V data = query.apply(key);
			logger.trace("Queried data {} for {}", data, key);
			cache.put(key, new Tuple<>(System.currentTimeMillis(), data));
			return data;
		} catch (Exception ex) {
			logger.error("Could not get data for {}", key, ex);
			return fallback.apply(key);
		}
	}

	@Override
	public boolean contains(@Nonnull K key) {
		return cache.containsKey(key);
	}

	@Override
	public int size() {
		return cache.size();
	}

	@Override
	public void clear() {
		cache.clear();
	}

	public static <K, V> void cleanAndWrite(@Nonnull Map<K, Tuple<Long, V>> cache, @Nonnegative long unusedTimeBeforeClean, @Nonnull ILogger logger,
	                                        @Nonnull Predicate<? super V> check, @Nonnull BiConsumer<? super K, ? super V> writer) {
		long now = System.currentTimeMillis();
		Collection<K> remove = new ArrayList<>();
		cache.forEach((key, pair) -> {
			try {
				if (now - pair.getFirst() > unusedTimeBeforeClean) {
					logger.trace("Removing {} from cache, last usage was {}s ago", key, (now - pair.getFirst()) / 1000);
					remove.add(key);
				}

				V value = pair.getSecond();
				if (!check.test(value)) return;

				logger.trace("Writing {}", value);
				writer.accept(key, value);
			} catch (Exception ex) {
				logger.error("Unable to write cache for {}", key, ex);
			}
		});
		remove.forEach(cache::remove);
	}

	@Nonnull
	@Override
	public Map<K, V> values() {
		return Collections.unmodifiableMap(SimpleCollectionUtils.convertMap(cache, k -> k, Tuple::getSecond));
	}

}
