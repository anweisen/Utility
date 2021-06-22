package net.anweisen.utilities.bukkit.utils.logging;

import net.anweisen.utilities.bukkit.core.BukkitModule;
import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.common.misc.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.8
 */
public final class Logger {

	private Logger() {}

	@Nonnull
	public static ILogger getInstance() {
		return BukkitModule.getProvidingModule(ReflectionUtils.getCaller()).getLogger();
	}

	public static void error(@Nullable Object message, @Nonnull Object... args) {
		getInstance().error(message, args);
	}

	public static void warn(@Nullable Object message, @Nonnull Object... args) {
		getInstance().warn(message, args);
	}

	public static void info(@Nullable Object message, @Nonnull Object... args) {
		getInstance().info(message, args);
	}

	public static void debug(@Nullable Object message, @Nonnull Object... args) {
		getInstance().debug(message, args);
	}

	public static void trace(@Nullable Object message, @Nonnull Object... args) {
		getInstance().trace(message, args);
	}

}
