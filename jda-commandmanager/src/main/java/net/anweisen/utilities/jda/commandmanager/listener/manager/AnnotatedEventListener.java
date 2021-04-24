package net.anweisen.utilities.jda.commandmanager.listener.manager;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.utils.ClassWalker;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class AnnotatedEventListener implements EventListener {

	private final Map<Class<?>, List<Method>> listeners = new HashMap<>();
	private final Object holder;

	public AnnotatedEventListener(@Nonnull Object holder) {
		this.holder = holder;
		updateMethods();
	}

	private void updateMethods() {
		boolean isClass = holder instanceof Class;
		Class<?> clazz = isClass ? (Class<?>) holder : holder.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.isAnnotationPresent(SubscribeEvent.class) || (isClass && !Modifier.isStatic(method.getModifiers()))) continue;
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length == 1 && GenericEvent.class.isAssignableFrom(parameters[0])) {
				List<Method> methods = listeners.computeIfAbsent(parameters[0], key -> new ArrayList<>(1));
				methods.add(method);
			}
		}
	}

	@Override
	public void onEvent(@Nonnull GenericEvent event) {
		for (Class<?> classOfEvent : ClassWalker.walk(event.getClass())) {
			List<Method> methods = listeners.get(classOfEvent);
			if (methods == null) continue;
			methods.forEach(method -> {
				try {
					method.setAccessible(true);
					method.invoke(holder instanceof Class ? null : holder, event);
				} catch (IllegalAccessException | InvocationTargetException ex) {
					JDAImpl.LOG.error("Couldn't access annotated EventListener method", ex);
				} catch (Throwable ex) {
					JDAImpl.LOG.error("One of the EventListeners had an uncaught exception", ex);
					if (ex instanceof Error)
						throw (Error) ex;
				}
			});
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnnotatedEventListener that = (AnnotatedEventListener) o;
		return this.holder.getClass() == that.holder.getClass();
	}

	@Override
	public int hashCode() {
		return Objects.hash(holder.getClass().hashCode());
	}
}
