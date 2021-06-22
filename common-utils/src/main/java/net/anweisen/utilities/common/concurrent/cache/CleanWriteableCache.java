package net.anweisen.utilities.common.concurrent.cache;

import net.anweisen.utilities.common.collection.RunnableTimerTask;
import net.anweisen.utilities.common.collection.Tuple;
import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.common.misc.SimpleCollectionUtils;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public class CleanWriteableCache<K, V> implements WriteableCache<K, V> {

	protected final Map<K, Tuple<Long, V>> cache = new ConcurrentHashMap<>();
	protected final ILogger logger;
	protected final long cleanInterval;
	protected final long unusedTimeBeforeClean;

	public CleanWriteableCache(@Nullable ILogger logger, @Nonnegative long unusedTimeBeforeClean, @Nonnegative long cleanInterval, @Nonnull String taskName) {
		this.logger = logger;
		this.cleanInterval = cleanInterval;
		this.unusedTimeBeforeClean = unusedTimeBeforeClean;

		new Timer(taskName).schedule(new RunnableTimerTask(this::cleanCache), unusedTimeBeforeClean, unusedTimeBeforeClean);
	}

	public void cleanCache() {
		if (logger != null ) logger.debug("Cleaning cache");
		long now = System.currentTimeMillis();
		Collection<K> remove = new ArrayList<>();
		cache.forEach((key, pair) -> {
			if (now - pair.getFirst() > unusedTimeBeforeClean) {
				if (logger != null ) logger.trace("Removing {} from cache, last usage was {}s ago", key, (now - pair.getFirst()) / 1000);
				remove.add(key);
			}
		});
		remove.forEach(cache::remove);
	}

	@Nullable
	@Override
	public V getData(@Nonnull K key) {
		Tuple<Long, V> pair = cache.get(key);
		if (pair == null) return null;
		pair.setFirst(System.currentTimeMillis());
		return pair.getSecond();
	}

	@Override
	public void setData(@Nonnull K key, @Nullable V value) {
		cache.put(key, new Tuple<>(System.currentTimeMillis(), value));
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

	@Nonnull
	@Override
	public Map<K, V> values() {
		return Collections.unmodifiableMap(SimpleCollectionUtils.convertMap(cache, k -> k, Tuple::getSecond));
	}

}
