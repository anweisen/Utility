package net.anweisen.utility.jda.manager.utils;

import net.anweisen.utility.common.collection.Colors;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.6
 */
public final class Embeds {

	private Embeds() {}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull String author, @Nullable String suffix, @Nullable String icon, @Nullable String url) {
		return new EmbedBuilder().setAuthor(title(icon, author, suffix), url, icon);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull String author, @Nullable String suffix, @Nullable String icon) {
		return construct(author, suffix, icon, null);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull String author, @Nullable String suffix) {
		return construct(author, suffix, null, null);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull String author) {
		return construct(author, null, null, null);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull Guild guild) {
		return construct(guild, null, null);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull Guild guild, @Nullable String suffix) {
		return construct(guild, null, suffix);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull Guild guild, @Nullable String authorURL, @Nullable String suffix) {
		return new EmbedBuilder()
				.setColor(ColorHelper.getMemberColorNonnull(guild.getSelfMember()))
				.setAuthor(title(guild, suffix), authorURL, guild.getIconUrl());
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull Member member) {
		return construct(member, null, null);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull Member member, @Nullable String suffix) {
		return construct(member, null, suffix);
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull Member member, @Nullable String authorUrl, @Nullable String suffix) {
		return new EmbedBuilder().setColor(ColorHelper.getMemberColorNonnull(member.getGuild().getSelfMember()))
				.setAuthor(title(member, suffix), authorUrl, member.getUser().getEffectiveAvatarUrl());
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder construct(@Nonnull CommandEvent event) {
		return new EmbedBuilder().setColor(event.isPrivate() ? Colors.EMBED : ColorHelper.getMemberColorNonnull(event.getSelfMember()));
	}

	@Nonnull
	@CheckReturnValue
	public static EmbedBuilder empty(@Nonnull Guild guild) {
		return new EmbedBuilder().setColor(ColorHelper.getMemberColorNonnull(guild.getSelfMember()));
	}

	@Nonnull
	@CheckReturnValue
	public static String title(@Nullable String icon, @Nullable String content, @Nullable String suffix) {
		if (content == null) content = "";
		if (suffix == null) suffix = "";
		if (!suffix.isEmpty()) suffix = " • " + suffix;
		return (icon != null ? "» " : "") + content + suffix;
	}

	@Nonnull
	@CheckReturnValue
	public static String title(@Nonnull Guild guild, @Nullable String suffix) {
		return title(guild.getIconUrl(), guild.getName(), suffix);
	}

	@Nonnull
	@CheckReturnValue
	public static String title(@Nonnull Member member, @Nullable String suffix) {
		return title(member.getUser(), suffix);
	}

	@Nonnull
	@CheckReturnValue
	public static String title(@Nonnull User user, @Nullable String suffix) {
		return title(user.getEffectiveAvatarUrl(), user.getName(), suffix);
	}

}
