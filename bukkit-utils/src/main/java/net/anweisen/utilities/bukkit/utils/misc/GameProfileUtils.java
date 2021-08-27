package net.anweisen.utilities.bukkit.utils.misc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.anweisen.utilities.common.collection.WrappedException;
import net.anweisen.utilities.common.logging.ILogger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.1
 */
public final class GameProfileUtils {

	protected static final ILogger logger = ILogger.forThisClass();

	private GameProfileUtils() {}

	@Nonnull
	public static GameProfile getGameProfile(@Nonnull Player player) {
		try {

			Class<?> classOfPlayer = player.getClass();

			Method getProfileMethod = classOfPlayer.getMethod("getProfile");
			getProfileMethod.setAccessible(true);
			return (GameProfile) getProfileMethod.invoke(player);

		} catch (Exception ex) {
			throw new WrappedException(ex);
		}
	}

	public static void applyTextures(@Nonnull SkullMeta meta, @Nullable UUID uuid, @Nullable String name, @Nullable String texture) {
		applyTextures(meta, uuid, name, texture, null);
	}

	public static void applyTextures(@Nonnull SkullMeta meta, @Nullable UUID uuid, @Nullable String name, @Nullable String texture, @Nullable String signature) {
		if (texture == null || texture.isEmpty()) return;

		GameProfile profile = new GameProfile(uuid == null ? UUID.randomUUID() : uuid, name);
		profile.getProperties().put("textures", new Property("textures", texture, signature));

		Class<?> classOfMeta = meta.getClass();
		try {
			Method setProfileMethod = classOfMeta.getDeclaredMethod("setProfile", GameProfile.class);
			setProfileMethod.setAccessible(true);
			setProfileMethod.invoke(meta, profile);
			return;
		} catch (Exception ex) {
		}

		try {
			Field field = classOfMeta.getDeclaredField("profile");
			field.setAccessible(true);
			field.set(meta, profile);

			// This field is not implemented in every version
			try {
				field = classOfMeta.getDeclaredField("serializedProfile");
				field.setAccessible(true);
				field.set(meta, profile);
			} catch (Exception ex) {
			}

			return;
		} catch (Exception ex) {
		}

		logger.warn("Unable to apply textures to item");

	}

	@Nonnull
	public static GameProfile getTextures(@Nonnull SkullMeta meta) {

		Class<?> classOfMeta = meta.getClass();
		try {
			Field field = classOfMeta.getDeclaredField("profile");
			field.setAccessible(true);
			return (GameProfile) field.get(meta);
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}

	}

}
