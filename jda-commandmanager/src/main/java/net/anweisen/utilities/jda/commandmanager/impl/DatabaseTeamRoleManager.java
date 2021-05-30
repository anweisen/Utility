package net.anweisen.utilities.jda.commandmanager.impl;

import net.anweisen.utilities.database.Database;
import net.anweisen.utilities.database.access.CachedDatabaseAccess;
import net.anweisen.utilities.database.access.DatabaseAccess;
import net.anweisen.utilities.database.access.DatabaseAccessConfig;
import net.anweisen.utilities.database.exceptions.DatabaseException;
import net.anweisen.utilities.jda.commandmanager.TeamRoleManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DatabaseTeamRoleManager implements TeamRoleManager {

	private final DatabaseAccess<String> access;

	public DatabaseTeamRoleManager(@Nonnull DatabaseAccess<String> access) {
		this.access = access;
	}

	public DatabaseTeamRoleManager(@Nonnull Database database, @Nonnull String table, @Nonnull String keyField, @Nonnull String valueField) {
		this(CachedDatabaseAccess.newStringAccess(database, new DatabaseAccessConfig(table, keyField, valueField)));
	}

	@Override
	public boolean isTeamRoleSet(@Nonnull Guild guild) {
		return getTeamRole(guild) != null;
	}

	@Override
	public boolean hasTeamRole(@Nonnull Member member) {
		return member.getRoles().contains(getTeamRole(member.getGuild()));
	}

	@Nullable
	@Override
	public Role getTeamRole(@Nonnull Guild guild) {
		try {
			return access.getValueOptional(guild.getId()).map(guild::getRoleById).orElse(null);
		} catch (DatabaseException ex) {
			LOGGER.error("Unable to get team role for guild {}", guild, ex);
			return null;
		}
	}

	@Override
	public void setTeamRole(@Nonnull Guild guild, @Nullable Role role) throws DatabaseException {
		access.setValue(guild.getId(), role == null ? null : role.getId());
	}

}
