package net.anweisen.utilities.commons.config.document.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.misc.GsonUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class SerializableTypeAdapter implements GsonTypeAdapter<Object> {

	public static final String KEY = "classOfType",
							   SERIALIZE_METHOD = "serialize",
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

	@Override
	public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull Object object) throws IOException {

		Class<?> classOfObject = object.getClass();
		JsonObject json = new JsonObject();

		try {

			Method method = classOfObject.getMethod(SERIALIZE_METHOD);
			method.setAccessible(true);
			Object serialized = method.invoke(object);

			if (!(serialized instanceof Map)) throw new IllegalArgumentException(method + " does not return a Map");

			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) serialized;

			GsonUtils.setDocumentProperties(gson, json, map);
			json.addProperty(KEY, classOfObject.getName());
			TypeAdapters.JSON_ELEMENT.write(writer, json);

		} catch (Throwable ex) {
			logger.error("Could not serialize object of type {}", classOfObject, ex);
		}
	}

	@Override
	public Object read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {

		JsonElement element = TypeAdapters.JSON_ELEMENT.read(reader);
		if (element == null || !element.isJsonObject()) return null;

		JsonObject json = element.getAsJsonObject();
		String classOfType = json.get(KEY).getAsString();

		Map<String, Object> map = GsonUtils.convertJsonObjectToMap(json);

		try {
			Class<?> clazz = Class.forName(classOfType);

			Method method = clazz.getMethod(DESERIALIZE_METHOD, Map.class);
			method.setAccessible(true);
			return method.invoke(null, map);
		} catch (Throwable ex) {
			logger.error("Could not deserialize object of type {}", classOfType);
			return null;
		}
	}

}
