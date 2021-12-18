package net.anweisen.utility.document.gson.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.gson.GsonBundle;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class BundleTypeAdapter implements GsonTypeAdapter<Bundle> {

	@Override
	public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull Bundle object) throws IOException {
		if (object instanceof GsonBundle) {
			GsonBundle gsonBundle = (GsonBundle) object;
			TypeAdapters.JSON_ELEMENT.write(writer, gsonBundle.getJsonArray());
			return;
		}

		GsonBundle gsonBundle = new GsonBundle(object.toList());
		TypeAdapters.JSON_ELEMENT.write(writer, gsonBundle.getJsonArray());
	}

	@Override
	public Bundle read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {
		JsonElement jsonElement = TypeAdapters.JSON_ELEMENT.read(reader);
		if (jsonElement != null && jsonElement.isJsonArray()) {
			return new GsonBundle(jsonElement.getAsJsonArray());
		} else {
			return null;
		}
	}
}
