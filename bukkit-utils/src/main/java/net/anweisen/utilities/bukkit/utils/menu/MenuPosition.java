package net.anweisen.utilities.bukkit.utils.menu;

import net.anweisen.utilities.bukkit.utils.animation.SoundSample;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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

	static void setEmpty(@Nonnull Player player) {
		set(player, new EmptyMenuPosition());
	}

	static void resetPosition(@Nonnull Player player) {
		Holder.positions.remove(player);
	}

	@Nullable
	static MenuPosition get(@Nonnull Player player) {
		return Holder.positions.get(player);
	}

	void handleClick(@Nonnull MenuClickInfo info);

	class EmptyMenuPosition implements MenuPosition {

		@Override
		public void handleClick(@Nonnull MenuClickInfo info) {
			SoundSample.CLICK.play(info.getPlayer());
		}

	}

	class SlottedMenuPosition implements MenuPosition {

		protected final Map<Integer, Consumer<? super MenuClickInfo>> actions = new HashMap<>();
		protected boolean emptySound = true;

		@Override
		public void handleClick(@Nonnull MenuClickInfo info) {
			Consumer<? super MenuClickInfo> action = actions.get(info.getSlot());
			if (action == null) {
				if (emptySound) SoundSample.CLICK.play(info.getPlayer());
				return;
			}

			action.accept(info);
		}

		@Nonnull
		public SlottedMenuPosition setAction(int slot, @Nonnull Consumer<? super MenuClickInfo> action) {
			actions.put(slot, action);
			return this;
		}

		@Nonnull
		public SlottedMenuPosition setPlayerAction(int slot, @Nonnull Consumer<? super Player> action) {
			return setAction(slot, info -> action.accept(info.getPlayer()));
		}

		@Nonnull
		public SlottedMenuPosition setAction(int slot, @Nonnull Runnable action) {
			return setAction(slot, info -> action.run());
		}

		public SlottedMenuPosition setEmptySound(boolean playSound) {
			this.emptySound = playSound;
			return this;
		}

	}

}
