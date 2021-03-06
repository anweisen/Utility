package net.anweisen.utilities.jda.manager;

import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface TeamRoleManager {

	ILogger LOGGER = ILogger.forThisClass();

	boolean isTeamRoleSet(@Nonnull Guild guild);

	boolean hasTeamRole(@Nonnull Member member);

	@Nullable
	Role getTeamRole(@Nonnull Guild guild);

	void setTeamRole(@Nonnull Guild guild, @Nullable Role role) throws DatabaseException;

}
