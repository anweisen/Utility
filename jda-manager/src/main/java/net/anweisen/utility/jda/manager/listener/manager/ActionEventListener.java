package net.anweisen.utility.jda.manager.listener.manager;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ActionEventListener<E extends GenericEvent> implements EventListener {

	private final Class<E> eventClass;
	private final Consumer<? super E> action;

	public ActionEventListener(@Nonnull Class<E> eventClass, @Nonnull Consumer<? super E> action) {
		this.eventClass = eventClass;
		this.action = action;
	}

	@Override
	public void onEvent(@Nonnull GenericEvent event) {
		if (eventClass.isInstance(event)) return;
		action.accept(eventClass.cast(event));
	}
}
