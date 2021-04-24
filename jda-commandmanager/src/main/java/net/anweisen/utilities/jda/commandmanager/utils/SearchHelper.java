package net.anweisen.utilities.jda.commandmanager.utils;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.dv8tion.jda.api.entities.*;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class SearchHelper {

	private SearchHelper() {}

	@Nullable
	@CheckReturnValue
	public static Role findRole(@Nonnull CommandEvent event, String search) {
		return findRole(event.getGuild(), search);
	}

	@Nullable
	@CheckReturnValue
	public static Role findRole(@Nonnull Guild guild, String search) {
		search = search.trim();
		try {
			String id = search.substring(3).substring(0, 18);
			Role role = guild.getRoleById(id);
			if (role != null)
				return role;
		} catch (Exception ex) {}
		try {
			String id = search.substring(2).substring(0, 18);
			Role role = guild.getRoleById(id);
			if (role != null)
				return role;
		} catch (Exception ex) {}
		try {
			Role role = guild.getRoleById(search);
			if (role != null)
				return role;
		} catch (Exception ex) {}
		try {
			Role role = guild.getRolesByName(search, true).get(0);
			if (role != null)
				return role;
		} catch (Exception ex) {}
		return null;
	}

	@Nullable
	@CheckReturnValue
	public static TextChannel findTextChannel(@Nonnull CommandEvent event, String search) {
		return findTextChannel(event.getGuild(), search);
	}

	@Nullable
	@CheckReturnValue
	public static TextChannel findTextChannel(@Nonnull Guild guild, String search) {
		search = search.trim();
		try {
			String id = search.substring(3).substring(0, 18);
			TextChannel channel = guild.getTextChannelById(id);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		try {
			String id = search.substring(2).substring(0, 18);
			TextChannel channel = guild.getTextChannelById(id);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		try {
			TextChannel channel = guild.getTextChannelById(search);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		try {
			TextChannel channel = guild.getTextChannelsByName(search, true).get(0);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		return null;
	}

	@Nullable
	@CheckReturnValue
	public static VoiceChannel findVoiceChannel(@Nonnull CommandEvent event, String search) {
		return findVoiceChannel(event.getGuild(), search);
	}

	@Nullable
	@CheckReturnValue
	public static VoiceChannel findVoiceChannel(@Nonnull Guild guild, String search) {
		search = search.trim();
		try {
			VoiceChannel channel = guild.getVoiceChannelById(search);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		try {
			VoiceChannel channel = guild.getVoiceChannelsByName(search, true).get(0);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		return null;
	}

	@Nullable
	@CheckReturnValue
	public static Category findCategory(@Nonnull CommandEvent event, String search) {
		return findCategory(event.getGuild(), search);
	}

	@Nullable
	@CheckReturnValue
	public static Category findCategory(@Nonnull Guild guild, String search) {
		search = search.trim();
		try {
			return guild.getCategoryById(search);
		} catch (Exception ex) {}
		try {
			return guild.getCategoriesByName(search, true).get(0);
		} catch (Exception ex) {}
		return null;
	}

	@Nullable
	@CheckReturnValue
	public static Member findMember(@Nonnull CommandEvent event, String search) {
		return findMember(event.getGuild(), search);
	}

	@Nullable
	@CheckReturnValue
	public static Member findMember(@Nonnull Guild guild, String search) {
		search = search.trim();
		try {
			String id = search.substring(3).substring(0, 18);
			return guild.getMemberById(id);
		} catch (Exception ignored) {}
		try {
			String id = search.substring(2).substring(0, 18);
			return guild.getMemberById(id);
		} catch (Exception ex) {}
		try {
			return guild.getMemberById(search);
		} catch (Exception ex) {}
		try {
			return guild.getMemberByTag(search);
		} catch (Exception ex) {}
		try {
			return guild.getMembersByName(search, true).get(0);
		} catch (Exception ex) {}
		try {
			return guild.getMembersByEffectiveName(search, true).get(0);
		} catch (Exception ex) {}
		return null;
	}

	@Nullable
	@CheckReturnValue
	public static Message findMessage(@Nonnull MessageChannel channel, @Nonnull String search) {
		try {
			return channel.retrieveMessageById(search).complete();
		} catch (Exception ex) {}
		return null;
	}

	@Nullable
	@CheckReturnValue
	public static GuildChannel findGuildChannel(@Nonnull Guild guild, String search) {
		search = search.trim();
		try {
			String id = search.substring(3).substring(0, 18);
			GuildChannel channel = guild.getGuildChannelById(id);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		try {
			String id = search.substring(2).substring(0, 18);
			GuildChannel channel = guild.getGuildChannelById(id);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		try {
			GuildChannel channel = guild.getGuildChannelById(search);
			if (channel != null)
				return channel;
		} catch (Exception ex) {}
		try {
			for (GuildChannel channel : guild.getChannels()) {
				if (channel.getName().equalsIgnoreCase(search))
					return channel;
			}
		} catch (Exception ex) {}
		return null;
	}

	@Nullable
	@CheckReturnValue
	public static GuildChannel findGuildChannel(@Nonnull CommandEvent event, String search) {
		return findGuildChannel(event.getGuild(), search);
	}

}
