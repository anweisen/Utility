package net.anweisen.utilitites.commons.config.document;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.document.AbstractDocument;
import net.anweisen.utilities.commons.config.document.GsonDocument;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class YamlDocument extends AbstractDocument {

	protected final ConfigurationSection config;

	public YamlDocument() {
		this.config = new YamlConfiguration();
	}

	public YamlDocument(@Nonnull ConfigurationSection config) {
		this.config = config;
	}

	public YamlDocument(@Nonnull ConfigurationSection config, @Nonnull Document root, @Nullable Document parent) {
		super(root, parent);
		this.config = config;
	}

	public YamlDocument(@Nonnull File file) {
		this(YamlConfiguration.loadConfiguration(file));
	}

	@Nullable
	@Override
	public String getString(@Nonnull String path) {
		return config.getString(path);
	}

	@Nonnull
	@Override
	public String getString(@Nonnull String path, @Nonnull String def) {
		String string = config.getString(path, def);
		return string == null ? def : string;
	}

	@Nullable
	@Override
	public Object getObject(@Nonnull String path) {
		return config.get(path);
	}

	@Nonnull
	@Override
	public Object getObject(@Nonnull String path, @Nonnull Object def) {
		Object value = config.get(path, def);
		return value == null ? def : value;
	}

	@Nonnull
	@Override
	public Document getDocument0(@Nonnull String path, @Nonnull Document root, @Nullable Document parent) {
		ConfigurationSection section = config.getConfigurationSection(path);
		if (section == null) section = config.createSection(path);
		return new YamlDocument(section, root, parent);
	}

	@Override
	public long getLong(@Nonnull String path) {
		return config.getLong(path);
	}

	@Override
	public long getLong(@Nonnull String path, long def) {
		return config.getLong(path, def);
	}

	@Override
	public int getInt(@Nonnull String path) {
		return config.getInt(path);
	}

	@Override
	public int getInt(@Nonnull String path, int def) {
		return config.getInt(path, def);
	}

	@Override
	public short getShort(@Nonnull String path) {
		return (short) config.getInt(path);
	}

	@Override
	public short getShort(@Nonnull String path, short def) {
		return (short) config.getInt(path, def);
	}

	@Override
	public byte getByte(@Nonnull String path) {
		return (byte) config.getInt(path);
	}

	@Override
	public byte getByte(@Nonnull String path, byte def) {
		return (byte) config.getInt(path, def);
	}

	@Override
	public char getChar(@Nonnull String path) {
		return getChar(path, (char) 0);
	}

	@Override
	public char getChar(@Nonnull String path, char def) {
		try {
			return getString(path).charAt(0);
		} catch (NullPointerException | StringIndexOutOfBoundsException ex) {
			return def;
		}
	}

	@Override
	public double getDouble(@Nonnull String path) {
		return config.getDouble(path);
	}

	@Override
	public double getDouble(@Nonnull String path, double def) {
		return config.getDouble(path, def);
	}

	@Override
	public float getFloat(@Nonnull String path) {
		return (float) config.getDouble(path);
	}

	@Override
	public float getFloat(@Nonnull String path, float def) {
		return (float) config.getDouble(path, def);
	}

	@Override
	public boolean getBoolean(@Nonnull String path) {
		return config.getBoolean(path);
	}

	@Override
	public boolean getBoolean(@Nonnull String path, boolean def) {
		return config.getBoolean(path, def);
	}

	@Nonnull
	@Override
	public List<String> getStringList(@Nonnull String path) {
		return config.getStringList(path);
	}

	@Nullable
	@Override
	public UUID getUUID(@Nonnull String path) {
		try {
			return UUID.fromString(getString(path));
		} catch (Exception ex) {
			return null;
		}
	}

	@Nonnull
	@Override
	public UUID getUUID(@Nonnull String path, @Nonnull UUID def) {
		UUID value = getUUID(path);
		return value == null ? def : value;
	}

	@Nullable
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull Class<E> classOfEnum) {
		try {
			String name = getString(path);
			if (name == null) return null;
			return Enum.valueOf(classOfEnum, name);
		} catch (Throwable ex) {
			return null;
		}
	}

	@Nonnull
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull E def) {
		E value = getEnum(path, (Class<E>) def.getClass());
		return value == null ? def : value;
	}

	@Override
	public boolean contains(@Nonnull String path) {
		return config.contains(path, true);
	}

	@Override
	public boolean isEmpty() {
		return config.getValues(false).isEmpty();
	}

	@Override
	public int size() {
		return config.getValues(false).size();
	}

	@Override
	public void set0(@Nonnull String path, @Nullable Object value) {
		if (value instanceof Enum<?>) {
			Enum<?> enun = (Enum<?>) value;
			value = enun.name();
		}
		config.set(path, value);
	}

	@Override
	public void remove0(@Nonnull String path) {
		config.set(path, null);
	}

	@Override
	public void clear0() {
		for (String key : config.getKeys(true)) {
			config.set(key, null);
		}
	}

	@Nonnull
	@Override
	public Map<String, Object> values() {
		return config.getValues(true);
	}

	@Nonnull
	@Override
	public Collection<String> keys() {
		return config.getKeys(false);
	}

	@Override
	public void forEach(@Nonnull BiConsumer<? super String, ? super Object> action) {
		values().forEach(action);
	}

	@Override
	public String toString() {

		DumperOptions yamlOptions = new DumperOptions();
		LoaderOptions loaderOptions = new LoaderOptions();
		Representer yamlRepresenter = new YamlRepresenter();
		Yaml yaml = new Yaml(new YamlConstructor(), yamlRepresenter, yamlOptions, loaderOptions);

		yamlOptions.setIndent(2);
		yamlOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		yamlRepresenter.setDefaultFlowStyle(FlowStyle.BLOCK);
		String dump = yaml.dump(config.getValues(false));
		if (dump.equals("{}\n")) {
			dump = "";
		}

		return dump;

	}

	@Override
	public void write(@Nonnull Writer writer) throws IOException {
		writer.write(toString());
	}

	@Nonnull
	@Override
	public String toJson() {
		return new GsonDocument(values()).toJson();
	}

	@Override
	public boolean isReadonly() {
		return false;
	}

}
