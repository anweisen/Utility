package net.anweisen.utilities.jda.manager.utils;

import net.dv8tion.jda.api.requests.RestAction;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ActionHelper {

	private ActionHelper() {}

	public static void tryAction(@Nonnull Supplier<? extends RestAction<?>> action) {
		try {
			action.get().queue(success -> {}, failure -> {});
		} catch (Exception ex) {
		}
	}

}
