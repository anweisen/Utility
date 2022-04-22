package net.anweisen.utility.database.action.hierarchy;

import net.anweisen.utility.database.Order;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Database actions implementing this interface are capable of being ordered.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface OrderedAction {

	@Nullable
	OrderedAction order(@Nonnull String field, @Nonnull Order order);

}
