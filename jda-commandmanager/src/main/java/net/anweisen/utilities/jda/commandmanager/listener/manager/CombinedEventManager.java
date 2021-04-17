package net.anweisen.utilities.jda.commandmanager.listener.manager;

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
 * @author anweisen | https://github.com/anweisen
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
