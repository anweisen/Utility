package net.anweisen.utility.jda.manager.process;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum CommandProcessResult {

	SELF_REACTION,
	PREFIX_NOT_USED,
	EMPTY_COMMAND,
	UNKNOWN_COMMAND(true),
	EDITS_UNSUPPORTED,
	SLASH_COMMANDS_UNSUPPORTED,
	INVALID_AUTHOR,
	INVALID_MESSAGE,
	INVALID_SCOPE(true),
	INCORRECT_ARGUMENTS(true),
	COOLDOWN(true),
	MISSING_TEAM_ROLE(true),
	MISSING_PERMISSION(true),
	ERROR(true),
	SUCCESS;

	private final boolean user;

	CommandProcessResult(boolean user) {
		this.user = user;
	}

	CommandProcessResult() {
		this(false);
	}

	public boolean isUserMistake() {
		return user;
	}

}
