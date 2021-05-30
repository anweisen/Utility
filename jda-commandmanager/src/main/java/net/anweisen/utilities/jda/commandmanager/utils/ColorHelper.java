package net.anweisen.utilities.jda.commandmanager.utils;

import net.anweisen.utilities.commons.common.Colors;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ColorHelper {

	private ColorHelper() {}


	@Nonnull
	public static Color getMemberColorNonnull(@Nullable Member member) {
		if (member == null) return Colors.EMBED;
		Color color = member.getColor();
		return color != null ? color : Colors.EMBED;
	}

	@Nonnull
	public static Color getGuildColorNonnull(@Nullable Guild guild) {
		if (guild == null) return Colors.EMBED;
		Color color = guild.getSelfMember().getColor();
		return color != null ? color : Colors.EMBED;
	}

	@Nonnull
	@CheckReturnValue
	public static Color getColorForStatus(@Nonnull OnlineStatus status) {
		switch (status) {
			case ONLINE:            return Colors.ONLINE;
			case DO_NOT_DISTURB:    return Colors.DO_NOT_DISTURB;
			case IDLE:              return Colors.IDLE;
			default:                return Colors.OFFLINE;
		}
	}
}
