package net.anweisen.utility.bukkit.utils.menu.positions;

import net.anweisen.utility.bukkit.utils.animation.SoundSample;
import net.anweisen.utility.bukkit.utils.menu.MenuClickInfo;
import net.anweisen.utility.bukkit.utils.menu.MenuPosition;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.7
 */
public class SlottedMenuPosition implements MenuPosition {

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
