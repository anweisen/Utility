package net.anweisen.utilities.jda.commandmanager.impl.entities;

import net.anweisen.utilities.jda.commandmanager.CommandArguments;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CommandArgumentsImpl implements CommandArguments {

	private final Class<?>[] types;
	private final Object[] args;

	public CommandArgumentsImpl(@Nonnull Class<?>[] types, @Nonnull Object[] args) {
		this.types = types;
		this.args = args;
	}

	@Nonnull
	@Override
	public Object[] getAll() {
		return args;
	}

	@Nonnull
	@Override
	public <T> T get(int index) {
		return (T) args[index];
	}

	@Override
	public long getLong(int index) {
		return get(index);
	}

	@Override
	public int getInt(int index) {
		return get(index);
	}

	@Override
	public short getShort(int index) {
		return get(index);
	}

	@Override
	public byte getByte(int index) {
		return get(index);
	}

	@Override
	public float getFloat(int index) {
		return get(index);
	}

	@Override
	public double getDouble(int index) {
		return get(index);
	}

	@Override
	public char getChar(int index) {
		return get(index);
	}

	@Nonnull
	@Override
	public Class<?> getType(int index) {
		return types[index];
	}

	@Nonnull
	@Override
	public Class<?>[] getTypes() {
		return types;
	}

	@Override
	public int size() {
		return args.length;
	}

}
