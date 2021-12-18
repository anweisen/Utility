package net.anweisen.utility.document.gson.adapter;

import com.google.gson.Gson;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class OffsetDateTimeTypeAdapter implements GsonTypeAdapter<OffsetDateTime> {

	@Override
	public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull OffsetDateTime object) throws IOException {
		TypeAdapters.STRING.write(writer, object.toString());
	}

	@Override
	public OffsetDateTime read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {
		try {
			return OffsetDateTime.parse(TypeAdapters.STRING.read(reader));
		} catch (Exception ex) {
			return null;
		}
	}
}
