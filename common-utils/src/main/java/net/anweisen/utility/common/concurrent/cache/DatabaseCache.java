package net.anweisen.utility.common.concurrent.cache;

import net.anweisen.utility.common.annotations.ReplaceWith;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 *
 * @deprecated Use {@link com.google.common.cache.LoadingCache} instead
 *
 * @see com.google.common.cache.LoadingCache
 */
@Deprecated
@ReplaceWith("com.google.common.cache.LoadingCache")
public interface DatabaseCache<K, V> extends ICache<K, V> {

	@Nonnull
	V getData(@Nonnull K key);

}
