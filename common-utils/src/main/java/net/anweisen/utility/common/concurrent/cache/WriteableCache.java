package net.anweisen.utility.common.concurrent.cache;

import net.anweisen.utility.common.annotations.ReplaceWith;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
@Deprecated
@ReplaceWith("com.google.common.cache.Cache")
public interface WriteableCache<K, V> extends ICache<K, V> {

	@Nullable
	V getData(@Nonnull K key);

	void setData(@Nonnull K key, @Nullable V value);

}
