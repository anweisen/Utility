package net.anweisen.utility.jda.manager.listener.manager;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.internal.JDAImpl;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This {@link IEventManager EventManager} combines the functionality
 * of the {@link net.dv8tion.jda.api.hooks.InterfacedEventManager}
 * and {@link net.dv8tion.jda.api.hooks.AnnotatedEventManager}.
 * <p>
 * Event listeners can be created via the {@link net.dv8tion.jda.api.hooks.InterfacedEventManager}
 * by implementing {@link EventListener} or {@link net.dv8tion.jda.api.hooks.ListenerAdapter}, which is more advanced.
 * By annotating a method with {@link net.dv8tion.jda.api.hooks.SubscribeEvent} a event listener can be created via the {@link net.dv8tion.jda.api.hooks.AnnotatedEventManager}.
 *
 * @author anweisen | https://github.com/anweisen
 * @see net.dv8tion.jda.api.hooks.SubscribeEvent
 * @see net.dv8tion.jda.api.hooks.EventListener
 * @see net.dv8tion.jda.api.hooks.InterfacedEventManager
 * @see net.dv8tion.jda.api.hooks.AnnotatedEventManager
 * @since 1.0
 */
public class CombinedEventManager implements IEventManager {

	private final List<EventListener> listeners = new CopyOnWriteArrayList<>();
	private final Set<Object> listenerHolders = ConcurrentHashMap.newKeySet();

	@Override
	public void register(@Nonnull Object listener) {
		listenerHolders.add(listener);

		if (listener instanceof EventListener) {
			listeners.add((EventListener) listener);
		} else {
			listeners.add(new AnnotatedEventListener(listener));
		}
	}

	@Override
	public void unregister(@Nonnull Object listener) {
		listenerHolders.remove(listener);

		if (listener instanceof EventListener) {
			listeners.remove(listener);
		} else {
			listeners.remove(new AnnotatedEventListener(listener));
		}
	}

	@Override
	public void handle(@Nonnull GenericEvent event) {
		for (EventListener listener : listeners) {
			try {
				listener.onEvent(event);
			} catch (Throwable ex) {
				JDAImpl.LOG.error("One of the EventListeners had an uncaught exception", ex);
				if (ex instanceof Error)
					throw (Error) ex;
			}
		}
	}

	@Nonnull
	@Override
	public List<Object> getRegisteredListeners() {
		return Collections.unmodifiableList(new ArrayList<>(listenerHolders));
	}

}
