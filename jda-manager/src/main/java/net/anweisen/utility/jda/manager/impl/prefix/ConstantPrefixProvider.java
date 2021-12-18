package net.anweisen.utility.jda.manager.impl.prefix;

import net.anweisen.utility.jda.manager.PrefixProvider;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ConstantPrefixProvider implements PrefixProvider {

	private final String prefix;

	public ConstantPrefixProvider(@Nonnull String prefix) {
		this.prefix = prefix;
	}

	@Nonnull
	@Override
	public String getGuildPrefix(@Nonnull Guild guild) {
		return prefix;
	}

	@Nonnull
	@Override
	public String getPrivatePrefix() {
		return prefix;
	}

	@Override
	public void setGuildPrefix(@Nonnull Guild guild, @Nonnull String prefix) {
		throw new UnsupportedOperationException("ConstantPrefixProvider does not support custom guild prefixes");
	}

}
