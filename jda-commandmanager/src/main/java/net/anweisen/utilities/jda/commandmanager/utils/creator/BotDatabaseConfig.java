package net.anweisen.utilities.jda.commandmanager.utils.creator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class BotDatabaseConfig {

	private final String guildTable;
	private final String guildKeyColumn, teamRoleColumn, languageColumn, prefixColumn;

	public BotDatabaseConfig(@Nonnull String guildTable, @Nonnull String guildKeyColumn,
	                         @Nullable String teamRoleColumn, @Nullable String languageColumn, @Nullable String prefixColumn) {
		this.guildTable = guildTable;
		this.guildKeyColumn = guildKeyColumn;
		this.teamRoleColumn = teamRoleColumn;
		this.languageColumn = languageColumn;
		this.prefixColumn = prefixColumn;
	}

	@Nonnull
	public String getGuildTable() {
		return guildTable;
	}

	@Nonnull
	public String getGuildKeyColumn() {
		return guildKeyColumn;
	}

	@Nullable
	public String getLanguageColumn() {
		return languageColumn;
	}

	@Nullable
	public String getPrefixColumn() {
		return prefixColumn;
	}

	@Nullable
	public String getTeamRoleColumn() {
		return teamRoleColumn;
	}

	@Override
	public String toString() {
		return "BotDatabaseConfig{" +
				"guildTable='" + guildTable + '\'' +
				", guildKeyColumn='" + guildKeyColumn + '\'' +
				", teamRoleColumn='" + teamRoleColumn + '\'' +
				", languageColumn='" + languageColumn + '\'' +
				", prefixColumn='" + prefixColumn + '\'' +
				'}';
	}

}
