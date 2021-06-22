package net.anweisen.utilities.common.config.document.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.anweisen.utilities.common.config.document.GsonDocument;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class GsonDocumentTypeAdapter implements GsonTypeAdapter<GsonDocument> {

	@Override
	public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull GsonDocument document) throws IOException {
		TypeAdapters.JSON_ELEMENT.write(writer, document.getJsonObject());
	}

	@Override
	public GsonDocument read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {
		JsonElement jsonElement = TypeAdapters.JSON_ELEMENT.read(reader);
		if (jsonElement != null && jsonElement.isJsonObject()) {
			return new GsonDocument(jsonElement.getAsJsonObject());
		} else {
			return null;
		}
	}

}
