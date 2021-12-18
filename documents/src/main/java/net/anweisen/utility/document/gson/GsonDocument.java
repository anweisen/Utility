package net.anweisen.utility.document.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.anweisen.utility.common.misc.CollectionUtils;
import net.anweisen.utility.common.misc.GsonUtils;
import net.anweisen.utility.document.Bundle;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.IEntry;
import net.anweisen.utility.document.abstraction.AbstractDocument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class GsonDocument extends AbstractDocument {

	protected JsonObject object;

	public GsonDocument() {
		this(new JsonObject());
	}

	public GsonDocument(@Nonnull Reader reader) {
		this(GsonHelper.DEFAULT_GSON.fromJson(reader, JsonObject.class));
	}

	public GsonDocument(@Nonnull JsonObject object) {
		this(object, true);
	}

	public GsonDocument(@Nullable Object value) {
		this(GsonHelper.DEFAULT_GSON.toJsonTree(value).getAsJsonObject());
	}

	public GsonDocument(@Nonnull String json) {
		this(GsonHelper.DEFAULT_GSON.fromJson(json, JsonObject.class));
	}

	public GsonDocument(@Nonnull Map<String, Object> values) {
		this();
		GsonUtils.setDocumentProperties(GsonHelper.DEFAULT_GSON, object, values);
	}

	public GsonDocument(@Nonnull JsonObject object, boolean editable) {
		super(editable);
		this.object = object;
	}

	@Nonnull
	@Override
	public Map<String, Object> toMap() {
		return CollectionUtils.convertEntries(object.entrySet(), Function.identity(), element -> new GsonEntry(element).toObject());
	}

	@Nonnull
	@Override
	public Map<String, IEntry> toEntryMap() {
		return CollectionUtils.convertEntries(object.entrySet(), Function.identity(), GsonEntry::new);
	}

	@Override
	public <T> T toInstance(@Nonnull Class<T> classOfT) {
		return GsonHelper.DEFAULT_GSON.fromJson(object, classOfT);
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return object.keySet();
	}

	@Override
	public int size() {
		return object.size();
	}

	@Nonnull
	@Override
	public String toJson() {
		return GsonHelper.DEFAULT_GSON.toJson(object);
	}

	@Nonnull
	@Override
	public String toPrettyJson() {
		return GsonHelper.PRETTY_GSON.toJson(object);
	}

	@Override
	protected void set0(@Nonnull String path, @Nullable Object value) {
		setElement(path, value);
	}

	@Override
	protected void remove0(@Nonnull String path) {
		setElement(path, null);
	}

	@Override
	protected void clear0() {
		object = new JsonObject();
	}

	@Override
	public boolean contains(@Nonnull String path) {
		Optional<JsonElement> element = getElement(path);
		return element.isPresent() && !element.get().isJsonArray();
	}

	@Nonnull
	@Override
	public IEntry getEntry(@Nonnull String path) {
		JsonElement element = getElement(path).orElse(null);
		return new GsonEntry(element);
	}

	@Nonnull
	@Override
	public Document getDocument(@Nonnull String path) {
		JsonElement element = getElement(path).orElse(null);
		if (element == null || element.isJsonNull()) setElement(path, element = new JsonObject());
		if (!element.isJsonObject()) throw new IllegalStateException("Element at " + path + " " + element.getClass().getSimpleName() + " cannot be converted to a JsonObject");
		return new GsonDocument(element.getAsJsonObject());
	}

	@Nonnull
	@Override
	public Bundle getBundle(@Nonnull String path) {
		JsonElement element = getElement(path).orElse(null);
		if (element == null || element.isJsonNull()) setElement(path, element = new JsonArray());
		if (!element.isJsonArray()) throw new IllegalStateException("Element at " + path + " " + element.getClass().getSimpleName() + " cannot be converted to a JsonArray");
		return new GsonBundle(element.getAsJsonArray());
	}

	@Nonnull
	protected Optional<JsonElement> getElement(@Nonnull String path) {
		return getElement(path, object);
	}

	@Nonnull
	protected Optional<JsonElement> getElement(@Nonnull String path, @Nonnull JsonObject object) {
		JsonElement fullPathElement = object.get(path);
		if (fullPathElement != null) return Optional.of(fullPathElement);

		int index = path.indexOf('.');
		if (index == -1) return Optional.empty();

		String child = path.substring(0, index);
		String newPath = path.substring(index + 1);

		JsonElement element = object.get(child);
		if (element == null || element.isJsonNull()) return Optional.empty();

		return getElement(newPath, element.getAsJsonObject());
	}

	protected void setElement(@Nonnull String path, @Nullable Object value) {

		LinkedList<String> paths = determinePath(path);
		JsonObject object = this.object;

		for (int i = 0; i < paths.size() - 1; i++) {

			String current = paths.get(i);
			JsonElement element = object.get(current);
			if (element == null || element.isJsonNull()) {
				if (value == null) return; // There's noting to remove
				object.add(current, element = new JsonObject());
			}

			if (!element.isJsonObject()) {
				object = this.object;
				break;
			}
			object = element.getAsJsonObject();

		}

		String usePath = object == this.object ? path : paths.getLast();
		object.add(usePath, GsonHelper.toJsonElement(value));
	}

	@Nonnull
	protected LinkedList<String> determinePath(@Nonnull String path) {
		LinkedList<String> paths = new LinkedList<>();
		String pathCopy = path;
		int index;
		while ((index = pathCopy.indexOf('.')) != -1) {
			String child = pathCopy.substring(0, index);
			pathCopy = pathCopy.substring(index + 1);
			paths.add(child);
		}
		paths.add(pathCopy);
		return paths;
	}

	@Nonnull
	public JsonObject getJsonObject() {
		return object;
	}

	@Override
	public void write(@Nonnull Writer writer) {
		GsonHelper.PRETTY_GSON.toJson(object, writer);
	}
}
