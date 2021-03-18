package net.anweisen.utilities.commons.config.document.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.anweisen.utilities.commons.misc.GsonUtils;
import net.anweisen.utilities.commons.misc.SerializationUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class SerializableTypeAdapter implements GsonTypeAdapter<Object> {

	public static final String KEY = "classOfType";

	@Override
	public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull Object object) throws IOException {

		Map<String, Object> map = SerializationUtils.serializeObject(object);
		if (map == null) return;

		JsonObject json = new JsonObject();
		GsonUtils.setDocumentProperties(gson, json, map);
		json.addProperty(KEY, object.getClass().getName());
		TypeAdapters.JSON_ELEMENT.write(writer, json);

	}

	@Override
	public Object read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {

		JsonElement element = TypeAdapters.JSON_ELEMENT.read(reader);
		if (element == null || !element.isJsonObject()) return null;

		JsonObject json = element.getAsJsonObject();
		String classOfType = json.get(KEY).getAsString();

		Map<String, Object> map = GsonUtils.convertJsonObjectToMap(json);

		Class<?> clazz;
		try {
			clazz = Class.forName(classOfType);
		} catch (ClassNotFoundException | NullPointerException ex) {
			return null;
		}

		return SerializationUtils.deserializeObject(map, clazz);

	}

}
