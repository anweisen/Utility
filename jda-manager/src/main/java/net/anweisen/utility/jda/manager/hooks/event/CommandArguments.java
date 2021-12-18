package net.anweisen.utility.jda.manager.hooks.event;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface CommandArguments {

	@Nonnull
	Object[] getAll();

	/**
	 * @return might only be {@code null} if the parser permits null values, this is normally enabled via extra info in the usage
	 */
	<T> T get(int index) throws ClassCastException;

	@Nonnull
	Class<?> getType(int index);

	/**
	 * Returns an array containing the classes of the types of the arguments which is the same length as {@link #size()}.
	 * The classes dont have to be the classes of the arguments, but the argument has to be an instance of the class or a subclass of the class.
	 *
	 * @return an array containing the classes of the types
	 */
	@Nonnull
	Class<?>[] getTypes();

	@Nonnegative
	int size();

}
