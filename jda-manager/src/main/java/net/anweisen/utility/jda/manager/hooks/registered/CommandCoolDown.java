package net.anweisen.utility.jda.manager.hooks.registered;

import net.anweisen.utility.jda.manager.hooks.option.CoolDownScope;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class CommandCoolDown {

	private final CoolDownScope scope;
	private final double cooldown;

	private final Map<User, Long> userCoolDowns = new ConcurrentHashMap<>();
	private final Map<Guild, Long> guildCoolDowns = new ConcurrentHashMap<>();
	private final AtomicLong globalCoolDown = new AtomicLong();

	public CommandCoolDown(@Nonnull CoolDownScope scope, @Nonnegative double cooldown) {
		this.scope = scope;
		this.cooldown = cooldown;
	}

	public boolean isOnCoolDown(@Nonnull User user, @Nullable Guild guild) {
		return getCoolDown(user, guild) > 0;
	}

	public double getCoolDown(@Nonnull User user, @Nullable Guild guild) {
		if (cooldown == 0) return 0;
		switch (scope) {
			case USER:      return Math.max(userCoolDowns.getOrDefault(user, 0L) - System.currentTimeMillis(), 0) / 1000d;
			case GUILD:     return Math.max(guildCoolDowns.getOrDefault(guild, 0L) - System.currentTimeMillis(), 0) / 1000d;
			case GLOBAL:    return Math.max(globalCoolDown.get() - System.currentTimeMillis(), 0) / 1000d;
			default:        throw new IllegalStateException("Unsupported cooldown scope " + scope);
		}
	}

	public void renewCoolDown(@Nonnull User user, @Nullable Guild guild) {
		if (cooldown == 0) return;
		switch (scope) {
			case USER:
				userCoolDowns.put(user, System.currentTimeMillis() + (long) cooldown * 1000);
				return;
			case GUILD:
				guildCoolDowns.put(guild, System.currentTimeMillis() + (long) cooldown * 1000);
				return;
			case GLOBAL:
				globalCoolDown.set(System.currentTimeMillis() + (long) cooldown * 1000);
				return;
			default: throw new IllegalStateException("Unsupported cooldown scope " + scope);
		}
	}

}
