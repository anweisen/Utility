package net.anweisen.utility.database.action.hierarchy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Database actions which implement this interface are capable of settings values.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface SetAction {

	@Nonnull
	SetAction set(@Nonnull String field, @Nullable Object value);

}
