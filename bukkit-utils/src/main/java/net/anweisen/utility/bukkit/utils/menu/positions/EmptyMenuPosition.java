package net.anweisen.utility.bukkit.utils.menu.positions;

import net.anweisen.utility.bukkit.utils.animation.SoundSample;
import net.anweisen.utility.bukkit.utils.menu.MenuClickInfo;
import net.anweisen.utility.bukkit.utils.menu.MenuPosition;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.7
 */
public class EmptyMenuPosition implements MenuPosition {

	@Override
	public void handleClick(@Nonnull MenuClickInfo info) {
		SoundSample.CLICK.play(info.getPlayer());
	}

}
