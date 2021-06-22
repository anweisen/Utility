package net.anweisen.utilities.common.concurrent.cache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public interface WriteableCache<K, V> extends ICache<K, V> {

	@Nullable
	V getData(@Nonnull K key);

	void setData(@Nonnull K key, @Nullable V value);

}
