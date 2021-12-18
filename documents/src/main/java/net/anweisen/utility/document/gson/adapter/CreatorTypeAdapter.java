package net.anweisen.utility.document.gson.adapter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.anweisen.utility.document.creator.JsonCreator;
import net.anweisen.utility.document.creator.JsonName;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CreatorTypeAdapter implements GsonTypeAdapter<Object> {

	public static boolean check(@Nonnull Class<?> clazz) {
		return findConstructor(clazz) != null;
	}

	public static Constructor<?> findConstructor(@Nonnull Class<?> clazz) {
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (constructor.isAnnotationPresent(JsonCreator.class))
				return constructor;
		}
		return null;
	}

	@Nonnull
	public static String fieldNaming(@Nonnull Field field, @Nonnull Gson gson) {
		return field.isAnnotationPresent(JsonName.class) ? field.getAnnotation(JsonName.class).value() : gson.fieldNamingStrategy().translateName(field);
	}

	@Override
	public void write(@Nonnull Gson gson, @Nonnull JsonWriter writer, @Nonnull Object object) throws IOException {
	}

	@Override
	public Object read(@Nonnull Gson gson, @Nonnull JsonReader reader) throws IOException {
		return null;
	}
}
