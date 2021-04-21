package net.anweisen.utilities.commons.config.document;

import com.google.gson.*;
import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.gson.ClassTypeAdapter;
import net.anweisen.utilities.commons.config.document.gson.GsonDocumentTypeAdapter;
import net.anweisen.utilities.commons.config.document.gson.GsonTypeAdapter;
import net.anweisen.utilities.commons.config.document.gson.SerializableTypeAdapter;
import net.anweisen.utilities.commons.misc.FileUtils;
import net.anweisen.utilities.commons.misc.GsonUtils;
import net.anweisen.utilities.commons.misc.SerializationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class GsonDocument extends AbstractDocument {

	public static final Gson GSON = new GsonBuilder()
			.disableHtmlEscaping()
			.setPrettyPrinting()
			.registerTypeAdapterFactory(GsonTypeAdapter.newPredictableFactory(SerializationUtils::isSerializable, new SerializableTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(GsonDocument.class, new GsonDocumentTypeAdapter()))
			.registerTypeAdapterFactory(GsonTypeAdapter.newTypeHierarchyFactory(Class.class, new ClassTypeAdapter()))
			.create();

	protected JsonObject jsonObject;

	public GsonDocument(@Nonnull File file) throws IOException {
		this(FileUtils.newBufferedReader(file));
	}

	public GsonDocument(@Nonnull Reader reader) {
		this(GSON.fromJson(reader, JsonObject.class));
	}

	public GsonDocument(@Nonnull String json) {
		this(GSON.fromJson(json, JsonObject.class));
	}

	public GsonDocument(@Nonnull String json, @Nonnull Document root, @Nullable Document parent) {
		this(GSON.fromJson(json, JsonObject.class), root, parent);
	}

	public GsonDocument(@Nullable JsonObject jsonObject) {
		super();
		this.jsonObject = jsonObject == null ? new JsonObject() : jsonObject;
	}

	public GsonDocument(@Nullable JsonObject jsonObject, @Nonnull Document root, @Nullable Document parent) {
		super(root, parent);
		this.jsonObject = jsonObject == null ? new JsonObject() : jsonObject;
	}

	public GsonDocument(@Nonnull Map<String, Object> values) {
		this();
		GsonUtils.setDocumentProperties(GSON, jsonObject, values);
	}

	public GsonDocument() {
		this(new JsonObject());
	}

	@Nullable
	@Override
	public String getString(@Nonnull String path) {
		JsonElement element = getElement(path).orElse(null);
		return GsonUtils.convertJsonElementToString(element);
	}

	@Nullable
	@Override
	public Object getObject(@Nonnull String path) {
		JsonElement element = getElement(path).orElse(null);
		return GsonUtils.unpackJsonElement(element);
	}

	@Nullable
	public <T> T get(@Nonnull String path, @Nonnull Class<T> classOfType) {
		JsonElement element = getElement(path).orElse(null);
		return GSON.fromJson(element, classOfType);
	}

	@Nullable
	@Override
	public <T> T getSerializable(@Nonnull String path, @Nonnull Class<T> classOfT) {
		return get(path, classOfT);
	}

	@Nonnull
	@Override
	public Document getDocument0(@Nonnull String path, @Nonnull Document root, @Nullable Document parent) {
		JsonElement element = getElement(path).orElse(null);
		if (element == null || !element.isJsonObject()) setElement(path, element = new JsonObject());
		return new GsonDocument(element.getAsJsonObject(), root, parent);
	}

	@Nonnull
	@Override
	public List<Document> getDocumentList(@Nonnull String path) {
		JsonElement element = getElement(path).orElse(new JsonArray());
		if (element.isJsonNull()) return new ArrayList<>();
		JsonArray array = element.getAsJsonArray();
		List<Document> documents = new ArrayList<>(array.size());
		for (JsonElement current : array) {
			if (current == null || current.isJsonNull()) documents.add(new GsonDocument((JsonObject) null, root, this));
			else if (current.isJsonObject()) documents.add(new GsonDocument(current.getAsJsonObject(), root, this));
		}
		return documents;
	}

	@Override
	public char getChar(@Nonnull String path) {
		return getChar(path, (char) 0);
	}

	@Override
	public char getChar(@Nonnull String path, char def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsCharacter();
	}

	@Override
	public long getLong(@Nonnull String path, long def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsLong();
	}

	@Override
	public int getInt(@Nonnull String path, int def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsInt();
	}

	@Override
	public short getShort(@Nonnull String path, short def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsShort();
	}

	@Override
	public byte getByte(@Nonnull String path, byte def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsByte();
	}

	@Override
	public double getDouble(@Nonnull String path, double def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsDouble();
	}

	@Override
	public float getFloat(@Nonnull String path, float def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsFloat();
	}

	@Override
	public boolean getBoolean(@Nonnull String path, boolean def) {
		return getElement(path).orElse(new JsonPrimitive(def)).getAsBoolean();
	}

	@Nonnull
	@Override
	public List<String> getStringList(@Nonnull String path) {
		JsonElement array = jsonObject.get(path);
		if (array == null || array.isJsonNull()) return new ArrayList<>();
		return GsonUtils.convertJsonArrayToStringList(array.getAsJsonArray());
	}

	@Nullable
	@Override
	public UUID getUUID(@Nonnull String path) {
		return get(path, UUID.class);
	}

	@Nullable
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull Class<E> classOfEnum) {
		return get(path, classOfEnum);
	}

	@Override
	public boolean isList(@Nonnull String path) {
		return checkElement(path, JsonElement::isJsonArray);
	}

	@Override
	public boolean isDocument(@Nonnull String path) {
		return checkElement(path, JsonElement::isJsonObject);
	}

	@Override
	public boolean isObject(@Nonnull String path) {
		return checkElement(path, JsonElement::isJsonPrimitive);
	}

	private boolean checkElement(@Nonnull String path, @Nonnull Function<? super JsonElement, Boolean> check) {
		return getElement(path).map(check).orElse(false);
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return getElement(path).isPresent();
	}

	@Override
	public int size() {
		return jsonObject.size();
	}

	@Override
	public void set0(@Nonnull String path, @Nullable Object value) {
		setElement(path, value);
	}

	@Override
	public void clear0() {
		jsonObject = new JsonObject();
	}

	@Override
	public void remove0(@Nonnull String path) {
		setElement(path, null);
	}

	@Nonnull
	@Override
	public Map<String, Object> values() {
		return GsonUtils.convertJsonObjectToMap(jsonObject);
	}

	@Override
	public void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
		values().forEach(action);
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		Collection<String> keys = new ArrayList<>(jsonObject.size());
		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			keys.add(entry.getKey());
		}
		return keys;
	}

	@Nonnull
	private Optional<JsonElement> getElement(@Nonnull String path) {
		return getElement(path, jsonObject);
	}

	@Nonnull
	private Optional<JsonElement> getElement(@Nonnull String path, @Nonnull JsonObject object) {

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

	private void setElement(@Nonnull String path, @Nullable Object value) {

		LinkedList<String> paths = determinePath(path);
		JsonObject object = jsonObject;

		for (int i = 0; i < paths.size() - 1; i++) {

			String current = paths.get(i);
			JsonElement element = object.get(current);
			if (element == null || element.isJsonNull()) {
				if (value == null) return; // There's noting to remove
				object.add(current, element = new JsonObject());
			}

			if (!element.isJsonObject()) throw new IllegalArgumentException("Cannot replace '" + current + "' on '" + path + "'; It's not an object");
			object = element.getAsJsonObject();

		}

		String lastPath = paths.getLast();
		JsonElement jsonValue = value instanceof JsonElement ? (JsonElement) value : GSON.toJsonTree(value);
		object.add(lastPath, jsonValue);

	}

	@Nonnull
	private LinkedList<String> determinePath(@Nonnull String path) {

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
	@Override
	public String toJson() {
		return jsonObject.toString();
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GsonDocument that = (GsonDocument) o;
		return jsonObject.equals(that.jsonObject);
	}

	@Override
	public int hashCode() {
		return jsonObject.hashCode();
	}

	@Override
	public void write(@Nonnull Writer writer) throws IOException {
		cleanup();
		GSON.toJson(jsonObject, writer);
	}

	@Nonnull
	public JsonObject getJsonObject() {
		return jsonObject;
	}

	@Override
	public boolean isReadonly() {
		return false;
	}

	public void cleanup() {
		cleanup(jsonObject);
	}

	public void cleanup(@Nonnull JsonObject jsonObject) {
		Iterator<Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, JsonElement> entry = iterator.next();
			JsonElement value = entry.getValue();
			if (value.isJsonNull()) iterator.remove();
			if (value.isJsonObject()) cleanup(value.getAsJsonObject());
			if (value.isJsonObject() && value.getAsJsonObject().size() == 0) iterator.remove();
			if (value.isJsonArray() && value.getAsJsonArray().size() == 0) iterator.remove();
		}
	}

}
