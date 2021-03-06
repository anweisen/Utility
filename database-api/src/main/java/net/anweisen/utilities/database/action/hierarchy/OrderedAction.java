package net.anweisen.utilities.database.action.hierarchy;

import net.anweisen.utilities.database.Order;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface OrderedAction {

	@Nullable
	OrderedAction orderBy(@Nonnull String field, @Nonnull Order order);

}
