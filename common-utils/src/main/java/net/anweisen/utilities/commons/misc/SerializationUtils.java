package net.anweisen.utilities.commons.misc;

import net.anweisen.utilities.commons.logging.ILogger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class SerializationUtils {

	private SerializationUtils() {}

	public static final String SERIALIZE_METHOD = "serialize",
							   DESERIALIZE_METHOD = "deserialize";
	protected static final ILogger logger = ILogger.forThisClass();

	public static boolean isSerializable(@Nonnull Class<?> clazz) {
		try {
			clazz.getMethod(SERIALIZE_METHOD);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	@Nullable
	public static Map<String, Object> serializeObject(@Nonnull Object object) {
		Class<?> classOfObject = object.getClass();
		try {

			Method method = classOfObject.getMethod(SERIALIZE_METHOD);
			method.setAccessible(true);
			Object serialized = method.invoke(object);

			if (!(serialized instanceof Map)) throw new IllegalArgumentException(method + " does not return a Map");

			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) serialized;
			return map;

		} catch (Throwable ex) {
			logger.error("Could not serialize object of type {}", classOfObject, ex);
			return null;
		}
	}

	@Nullable
	public static <T> T deserializeObject(@Nonnull Map<String, Object> map, @Nonnull Class<T> classOfT) {
		try {
			Method method = classOfT.getMethod(DESERIALIZE_METHOD, Map.class);
			method.setAccessible(true);
			Object object = method.invoke(null, map);
			if (!classOfT.isInstance(object)) throw new IllegalStateException("Deserialization of " + classOfT.getName() + " failed: returned " + (object == null ? null : object.getClass().getName()));
			return classOfT.cast(object);
		} catch (Throwable ex) {
			logger.error("Could not deserialize object of type {}", classOfT, ex);
			return null;
		}
	}

}
