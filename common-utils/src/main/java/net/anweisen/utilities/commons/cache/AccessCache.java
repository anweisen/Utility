package net.anweisen.utilities.commons.cache;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public interface AccessCache<K, V> extends ICache<K, V> {

	@Nonnull
	V getData(@Nonnull K key);

}
