package net.anweisen.utilities.common.config.document.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.anweisen.utilities.common.misc.GsonUtils;
import net.anweisen.utilities.common.misc.SerializationUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class SerializableTypeAdapter implements GsonTypeAdapter<Object> {

	public static final String ALTERNATE_KEY = "classOfType", KEY = "==";

	@Override
	public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull Object object) throws IOException {

		Map<String, Object> map = SerializationUtils.serializeObject(object);
		if (map == null) return;

		JsonObject json = new JsonObject();
		json.addProperty(KEY, SerializationUtils.getSerializationName(object.getClass()));
		GsonUtils.setDocumentProperties(gson, json, map);
		TypeAdapters.JSON_ELEMENT.write(writer, json);

	}

	@Override
	public Object read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {

		JsonElement element = TypeAdapters.JSON_ELEMENT.read(reader);
		if (element == null || !element.isJsonObject()) return null;

		JsonObject json = element.getAsJsonObject();
		String classOfType = Optional.ofNullable(findClassContainer(json)).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsString).orElse(null);

		Class<?> clazz = null;
		try {
			clazz = Class.forName(classOfType);
		} catch (ClassNotFoundException | NullPointerException ex) {
		}

		Map<String, Object> map = GsonUtils.convertJsonObjectToMap(json);
		return SerializationUtils.deserializeObject(map, clazz);

	}

	private JsonElement findClassContainer(@Nonnull JsonObject json) {
		if (json.has(ALTERNATE_KEY))
			return json.get(ALTERNATE_KEY);
		return json.get(KEY);
	}

}
