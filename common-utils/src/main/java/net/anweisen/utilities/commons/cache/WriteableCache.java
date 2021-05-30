package net.anweisen.utilities.commons.cache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public interface WriteableCache<K, V> extends ICache<K, V> {

	@Nullable
	V getData(@Nonnull K key);

	void setData(@Nonnull K key, @Nullable V value);

}
