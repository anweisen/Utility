package net.anweisen.utilities.jda.manager;

import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface PrefixProvider {

	ILogger LOGGER = ILogger.forThisClass();

	@Nonnull
	String getGuildPrefix(@Nonnull Guild guild);

	@Nonnull
	String getPrivatePrefix();

	void setGuildPrefix(@Nonnull Guild guild, @Nonnull String prefix) throws DatabaseException;

}
