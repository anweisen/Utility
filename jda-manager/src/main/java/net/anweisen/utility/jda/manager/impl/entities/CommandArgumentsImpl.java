package net.anweisen.utility.jda.manager.impl.entities;

import net.anweisen.utility.jda.manager.hooks.event.CommandArguments;

import javax.annotation.Nonnull;
import java.util.Arrays;

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
	@SuppressWarnings("unchecked")
	public <T> T get(int index) throws ClassCastException {
		return (T) args[index];
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

	@Override
	public String toString() {
		return "CommandArguments[" + size() + ": " + Arrays.toString(args) + ']';
	}
}
