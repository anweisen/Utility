package net.anweisen.utilities.commons.config.document;

import net.anweisen.utilities.commons.config.Config;
import net.anweisen.utilities.commons.config.Propertyable;
import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.misc.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractConfig implements Config {

	protected static final ILogger logger = ILogger.forThisClass();

	@Nonnull
	@Override
	public Object getObject(@Nonnull String path, @Nonnull Object def) {
		Object value = getObject(path);
		return value == null ? def : value;
	}

	@Nonnull
	@Override
	public <T> Optional<T> getOptional(@Nonnull String key, @Nonnull BiFunction<? super Propertyable, ? super String, ? extends T> extractor) {
		return Optional.ofNullable(extractor.apply(this, key));
	}

	@Nonnull
	@Override
	public String getString(@Nonnull String path, @Nonnull String def) {
		String value = getString(path);
		return value == null ? def : value;
	}

	@Override
	public char getChar(@Nonnull String path) {
		return getChar(path, (char) 0);
	}

	@Override
	public char getChar(@Nonnull String path, char def) {
		try {
			return getString(path).charAt(0);
		} catch (NullPointerException | IndexOutOfBoundsException ex) {
			return def;
		}
	}

	@Override
	public double getDouble(@Nonnull String path) {
		return getDouble(path, 0);
	}

	@Override
	public float getFloat(@Nonnull String path) {
		return getFloat(path, 0);
	}

	@Override
	public long getLong(@Nonnull String path) {
		return getLong(path, 0);
	}

	@Override
	public int getInt(@Nonnull String path) {
		return getInt(path, 0);
	}

	@Override
	public short getShort(@Nonnull String path) {
		return getShort(path, (short) 0);
	}

	@Override
	public byte getByte(@Nonnull String path) {
		return getByte(path, (byte) 0);
	}

	@Override
	public boolean getBoolean(@Nonnull String path) {
		return getBoolean(path, false);
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
		return ReflectionUtils.getEnumOrNull(getString(path), classOfEnum);
	}

	@Nonnull
	@Override
	public <E extends Enum<E>> E getEnum(@Nonnull String path, @Nonnull E def) {
		E value = getEnum(path, (Class<E>) def.getClass());
		return value == null ? def : value;
	}

	@Nonnull
	@Override
	public <T> T getSerializable(@Nonnull String path, @Nonnull T def) {
		T value = getSerializable(path, (Class<T>) def.getClass());
		return value == null ? def : value;
	}

	@Nullable
	@Override
	public Class<?> getClass(@Nonnull String path) {
		try {
			return Class.forName(getString(path));
		} catch (Exception ex) {
			return null;
		}
	}

	@Nonnull
	@Override
	public Class<?> getClass(@Nonnull String path, @Nonnull Class<?> def) {
		Class<?> value = getClass(path);
		return value == null ? def : value;
	}

	@Nonnull
	@Override
	public <E extends Enum<E>> List<E> getEnumList(@Nonnull String path, @Nonnull Class<E> classOfEnum) {
		return getList(path, name -> ReflectionUtils.getEnumOrNull(name, classOfEnum));
	}

	@Nonnull
	@Override
	public List<Character> getCharacterList(@Nonnull String path) {
		return getList(path, string -> string == null || string.length() == 0 ? (char) 0 : string.charAt(0));
	}

	@Nonnull
	@Override
	public List<UUID> getUUIDList(@Nonnull String path) {
		return getList(path, UUID::fromString);
	}

	@Nonnull
	@Override
	public List<Byte> getByteList(@Nonnull String path) {
		return getList(path, Byte::parseByte);
	}

	@Nonnull
	@Override
	public List<Short> getShortList(@Nonnull String path) {
		return getList(path, Short::parseShort);
	}

	@Nonnull
	@Override
	public List<Integer> getIntegerList(@Nonnull String path) {
		return getList(path, Integer::parseInt);
	}

	@Nonnull
	@Override
	public List<Long> getLongList(@Nonnull String path) {
		return getList(path, Long::parseLong);
	}

	@Nonnull
	@Override
	public List<Float> getFloatList(@Nonnull String path) {
		return getList(path, Float::parseFloat);
	}

	@Nonnull
	@Override
	public List<Double> getDoubleList(@Nonnull String path) {
		return getList(path, Double::parseDouble);
	}

	@Nonnull
	@Override
	public <T> List<T> getList(@Nonnull String path, @Nonnull Function<String, ? extends T> mapper) {
		List<String> list = getStringList(path);
		List<T> result = new ArrayList<>(list.size());
		for (String string : list) {
			try {
				result.add(mapper.apply(string));
			} catch (Exception ex) {
				logger.error("Could not map '{}'", string, ex);
			}
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

}
