package net.anweisen.utilities.database.action.hierarchy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface SetAction {

	@Nonnull
	SetAction set(@Nonnull String field, @Nullable Object value);

}
