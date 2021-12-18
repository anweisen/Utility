package net.anweisen.utility.bukkit.utils.menu;

import net.anweisen.utility.bukkit.utils.menu.positions.EmptyMenuPosition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface MenuPosition {

	final class Holder {

		private Holder() {}

		private static final Map<Player, MenuPosition> positions = new ConcurrentHashMap<>();

	}

	InventoryHolder HOLDER = new MenuPositionHolder();

	static void set(@Nonnull Player player, @Nullable MenuPosition position) {
		Holder.positions.put(player, position);
	}

	static void remove(@Nonnull Player player) {
		Holder.positions.remove(player);
	}

	@Nullable
	static MenuPosition get(@Nonnull Player player) {
		return Holder.positions.get(player);
	}

	static void setEmpty(@Nonnull Player player) {
		set(player, new EmptyMenuPosition());
	}

	void handleClick(@Nonnull MenuClickInfo info);

}
