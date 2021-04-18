package net.anweisen.utilities.bukkit.utils.misc;

import net.anweisen.utilities.commons.logging.ILogger;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * This class is intended to give access to api functions which are not implemented in some versions of bukkit or spigot.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.1
 */
public final class BukkitReflectionUtils {

	protected static final ILogger logger = ILogger.forThisClass();

	private BukkitReflectionUtils() {}

	public static double getAbsorptionAmount(@Nonnull Player player) {
		Class<?> classOfPlayer = player.getClass();

		try {
			return player.getAbsorptionAmount();
		} catch (Throwable ex) {
		}

		try {
			Method getHandleMethod = classOfPlayer.getMethod("getHandle");
			getHandleMethod.setAccessible(true);

			Object handle = getHandleMethod.invoke(player);
			Class<?> classOfHandle = handle.getClass();

			Method getAbsorptionMethod = classOfHandle.getMethod("getAbsorptionHearts");
			getAbsorptionMethod.setAccessible(true);
			return (double) (float) getAbsorptionMethod.invoke(handle);
		} catch (Throwable ex) {
		}

		logger.warn("Could not get absorption amount for player of class {}", classOfPlayer.getName());
		return 0;
	}

	public static boolean isAir(@Nonnull Material material) {
		try {
			return material.isAir();
		} catch (Throwable ex) {
		}

		switch (material.name()) {
			case "AIR":
			case "VOID_AIR":
			case "CAVE_AIR":
			case "LEGACY_AIR":
				return true;
			default:
				return false;
		}
	}

	public static int getMinHeight(@Nonnull World world) {
		try {
			world.getMinHeight();
		} catch (Throwable ex) {
		}

		return 0;
	}

	/**
	 * @deprecated not implemented in all forks of bukkit
	 * @return if the entity is in water, {@code false} if not implemented
	 */
	@Deprecated
	public static boolean isInWater(@Nonnull Entity entity) {
		try {
			return entity.isInWater();
		} catch (Throwable ex) {
		}

		return false;
	}

}