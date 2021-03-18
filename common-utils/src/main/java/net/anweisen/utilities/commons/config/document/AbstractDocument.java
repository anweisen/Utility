package net.anweisen.utilities.commons.config.document;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.misc.FileUtils;
import net.anweisen.utilities.commons.misc.SerializationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractDocument implements Document {

	@Nonnull
	@Override
	public Object getObject(@Nonnull String path, @Nonnull Object def) {
		Object value = getObject(path);
		return value == null ? def : value;
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

	@Nullable
	@Override
	public <T> T getSerializable(@Nonnull String path, @Nonnull Class<T> classOfT) {
		if (!contains(path)) return null;
		return SerializationUtils.deserializeObject(getDocument(path).values(), classOfT);
	}

	@Nonnull
	@Override
	public <T> T getSerializable(@Nonnull String path, @Nonnull T def) {
		T value = getSerializable(path, (Class<T>) def.getClass());
		return value == null ? def : value;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void save(@Nonnull File file) throws IOException {
		Writer writer = FileUtils.newBufferedWriter(file);
		write(writer);
		writer.flush();
		writer.close();
	}

}
