package net.anweisen.utilities.common.config;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Config extends Propertyable {

	/**
	 * Setting a value to {@code null} has the same effect as {@link #remove(String) removing} it.
	 * {@code config.set(path, null)} is equivalent with {@code config.remove(path)}
	 *
	 * @param value The value to change to, {@code null} to remove
	 * @return {@code this} for chaining
	 *
	 * @throws net.anweisen.utilities.common.config.exceptions.ConfigReadOnlyException
	 *         If this is {@link #isReadonly() readonly}
	 */
	@Nonnull
	Config set(@Nonnull String path, @Nullable Object value);

	/**
	 * @throws net.anweisen.utilities.common.config.exceptions.ConfigReadOnlyException
	 *         If this is {@link #isReadonly() readonly}
	 */
	@Nonnull
	Config clear();

	/**
	 * Removing a value has the same effect as {@link #set(String, Object) setting} it to {@code null}
	 * {@code config.set(path, null)} is equivalent with {@code config.remove(path)}
	 *
	 * @return {@code this} for chaining
	 *
	 * @throws net.anweisen.utilities.common.config.exceptions.ConfigReadOnlyException
	 *         If this is {@link #isReadonly() readonly}
	 */
	@Nonnull
	Config remove(@Nonnull String path);

	boolean isReadonly();

	/**
	 * @return A new config which is readonly, or {@code this} if already {@link #isReadonly() readonly}
	 */
	@Nonnull
	@CheckReturnValue
	Config readonly();

}
