package net.anweisen.utilities.commons.cache;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ICache<K, V> {

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
