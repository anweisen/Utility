package net.anweisen.utilities.common.concurrent.cache;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public interface DatabaseCache<K, V> extends ICache<K, V> {

	@Nonnull
	V getData(@Nonnull K key);

}
