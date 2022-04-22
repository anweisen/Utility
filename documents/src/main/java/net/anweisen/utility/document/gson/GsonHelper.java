package net.anweisen.utility.document.gson;

import com.google.gson.*;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.anweisen.utility.common.collection.pair.Pair;
import net.anweisen.utility.common.misc.BukkitReflectionSerializationUtils;
import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.gson.adapter.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class GsonHelper {

	public static final Gson DEFAULT_GSON, PRETTY_GSON;
	public static final TypeAdapter<Number> NUMBER_ADAPTER;

	static {
		GsonBuilder builder = new GsonBuilder()
			.disableHtmlEscaping()
			.registerTypeAdapterFactory(GsonTypeAdapter.newPredictableFactory(CreatorTypeAdapter::check, new CreatorTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newPredictableFactory(BukkitReflectionSerializationUtils::isSerializable, new BukkitReflectionSerializableTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(Document.class, new DocumentTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(Bundle.class, new BundleTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(Pair.class, new PairTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(Class.class, new ClassTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(Color.class, new ColorTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(OffsetDateTime.class, new OffsetDateTimeTypeAdapter()));

		DEFAULT_GSON = builder.create();
		PRETTY_GSON = builder.setPrettyPrinting().create();
		NUMBER_ADAPTER = new GsonTypeAdapter<Number>() {
			@Override
			public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull Number object) throws IOException {
				writer.value(object);
			}

			@Override
			public Number read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {
				if (reader.peek() == JsonToken.NULL) {
					reader.nextNull();
					return null;
				}
				return reader.nextDouble();
			}
		}.toTypeAdapter(DEFAULT_GSON);
	}


	@Nonnull
	public static GsonEntry toGsonEntry(@Nullable Object value) {
		return new GsonEntry(toJsonElement(value));
	}

	@Nonnull
	public static JsonElement toJsonElement(@Nullable Object value) {
		return value instanceof JsonElement ? (JsonElement) value
			: value == null ? JsonNull.INSTANCE
			: value instanceof Number ? NUMBER_ADAPTER.toJsonTree((Number) value)
			: value instanceof Boolean ? TypeAdapters.BOOLEAN.toJsonTree((boolean) value)
			: value instanceof Character ? TypeAdapters.CHARACTER.toJsonTree((char) value)
			: DEFAULT_GSON.toJsonTree(value);
	}

	private GsonHelper() {
	}
}
