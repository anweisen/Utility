package net.anweisen.utilities.commons.misc;

import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.LogLevel;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2
 */
public final class SimpleMapUtils {

	@Deprecated
	public static final String REGEX_1 = ",",
							   REGEX_2 = "=";
	protected static final ILogger logger = ILogger.forThisClass();

	private SimpleMapUtils() {}

	/**
	 * You should not use this to serialize maps due to it's unsafe because of strings which may contain the regex chars = or ,
	 * Use better and safer serialization strategies like json.
	 *
	 * @deprecated Unsafe because of strings containing , or =
	 */
	@Nonnull
	@Deprecated
	@CheckReturnValue
	public static <K, V> String convertMapToString(@Nonnull Map<K, V> map, @Nonnull Function<K, String> key, @Nonnull Function<V, String> value) {
		StringBuilder builder = new StringBuilder();
		for (Entry<K, V> entry : map.entrySet()) {
			if (builder.length() != 0) builder.append(REGEX_1);
			builder.append(key.apply(entry.getKey()));
			builder.append(REGEX_2);
			builder.append(value.apply(entry.getValue()));
		}
		return builder.toString();
	}

	/**
	 * You should not use this to serialize maps due to it's unsafe because of strings which may contain the regex chars = or ,
	 * Use better and safer serialization strategies like json.
	 *
	 * @deprecated Unsafe because of strings containing , or =
	 */
	@Nonnull
	@Deprecated
	@CheckReturnValue
	public static <K, V> Map<K, V> convertStringToMap(@Nullable String string, @Nonnull Function<String, K> key, @Nonnull Function<String, V> value) {

		Map<K, V> map = new HashMap<>();
		if (string == null) return map;

		String[] args = string.split(REGEX_1);
		for (String arg : args) {
			try {

				String[] elements = arg.split(REGEX_2);
				K keyElement = key.apply(elements[0]);
				V valueElement = value.apply(elements[1]);

				if (keyElement == null || valueElement == null)
					throw new NullPointerException();

				map.put(keyElement, valueElement);

			} catch (Exception ex) {
				logger.error("Cannot generate key/value: " + ex.getClass().getName() + ": " + ex.getMessage());
			}
		}

		return map;

	}

	@Nonnull
	@CheckReturnValue
	public static <FromK, FromV, ToK, ToV> Map<ToK, ToV> map(@Nonnull Map<FromK, FromV> map,
	                                                         @Nonnull Function<? super FromK, ? extends ToK> keyMapper,
	                                                         @Nonnull Function<? super FromV, ? extends ToV> valueMapper) {
		Map<ToK, ToV> result = new HashMap<>();
		map.forEach((key, value) -> {
			try {
				result.put(keyMapper.apply(key), valueMapper.apply(value));
			} catch (Exception ex) {
				logger.error("Unable to map '{}'='{}'", key, value, ex);
			}
		});
		return result;
	}

}
