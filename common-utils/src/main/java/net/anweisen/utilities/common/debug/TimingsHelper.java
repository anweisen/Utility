package net.anweisen.utilities.common.debug;

import net.anweisen.utilities.common.collection.NumberFormatter;
import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.common.misc.ReflectionUtils;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.4
 */
public final class TimingsHelper {

	public static final ILogger LOGGER = ILogger.forThisClass();
	private static final Map<String, Long> timings = new ConcurrentHashMap<>();

	private TimingsHelper() {}

	public static void start(@Nonnull String id) {
		timings.put(id, System.currentTimeMillis());
	}

	public static void stop(@Nonnull String id) {
		Long start = timings.remove(id);
		if (start == null) {
			LOGGER.warn("Stopped timing {} which was not started before", id);
			return;
		}

		long time = System.currentTimeMillis() - start;
		LOGGER.debug("Finished timings '{}' within {}ms ({}s)", id, time, NumberFormatter.DOUBLE_FLOATING_POINT.format(time / 1000d));
	}

	public static void restart(@Nonnull String id) {
		stop(id);
		start(id);
	}

	public static void start() {
		start(ReflectionUtils.getCallerName());
	}

	public static void stop() {
		stop(ReflectionUtils.getCallerName());
	}

	public static void restart() {
		restart(ReflectionUtils.getCallerName());
	}

}
