package net.anweisen.utilities.jda.commandmanager.process;

import net.anweisen.utilities.jda.commandmanager.registered.RegisteredCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class CommandResultInfo {

	private final CommandProcessResult result;
	private final RegisteredCommand command;
	private final List<RegisteredCommand> matchingName;
	private final String commandName;
	private final String prefix;
	private final double cooldown;

	public CommandResultInfo(@Nonnull CommandProcessResult result) {
		this(result, null, null);
	}

	public CommandResultInfo(@Nonnull CommandProcessResult result, @Nullable String commandName, @Nullable String prefix) {
		this(result, (RegisteredCommand) null, commandName, prefix);
	}

	public CommandResultInfo(@Nonnull CommandProcessResult result, @Nullable RegisteredCommand command, @Nullable String commandName, @Nullable String prefix) {
		this(result, command, new ArrayList<>(), commandName, prefix);
	}

	public CommandResultInfo(@Nonnull CommandProcessResult result, @Nonnull List<RegisteredCommand> matchingName, @Nullable String commandName, @Nullable String prefix) {
		this(result, null, matchingName, commandName, prefix);
	}

	public CommandResultInfo(@Nonnull CommandProcessResult result, @Nullable RegisteredCommand command, @Nonnull List<RegisteredCommand> matchingName, @Nullable String commandName, @Nullable String prefix) {
		this(result, command, matchingName, commandName, prefix, 0);
	}

	public CommandResultInfo(@Nonnull CommandProcessResult result, @Nonnull RegisteredCommand command, @Nonnull String commandName, @Nonnull String prefix, double cooldown) {
		this(result, command, new ArrayList<>(), commandName, prefix, cooldown);
	}

	public CommandResultInfo(@Nonnull CommandProcessResult result, @Nullable RegisteredCommand command, @Nonnull List<RegisteredCommand> matchingName, @Nullable String commandName, @Nullable String prefix, double cooldown) {
		this.result = result;
		this.command = command;
		this.matchingName = matchingName;
		this.commandName = commandName;
		this.prefix = prefix;
		this.cooldown = cooldown;
	}

	@Nonnull
	public CommandProcessResult getType() {
		return result;
	}

	public RegisteredCommand getCommand() {
		return command;
	}

	@Nonnull
	public List<RegisteredCommand> getCommandsMatchingName() {
		return matchingName;
	}

	@Nullable
	public String getCommandName() {
		return commandName;
	}

	@Nonnull
	public String getPrefix() {
		return prefix == null ? "" : prefix;
	}

	@Nonnull
	public String getCorrectSyntax() {
		return getPrefix() + getCommandName() + " " + (getCommand() == null ? new ArrayList<>(getCommandsMatchingName()).get(0) : getCommand()).getSyntax();
	}

	public double getCoolDown() {
		return cooldown;
	}

	@Override
	public String toString() {
		return "CommandResultInfo{" +
				"result=" + result +
				", command=" + command +
				", matchingName=" + matchingName +
				", commandName='" + commandName + '\'' +
				", prefix='" + prefix + '\'' +
				", cooldown=" + cooldown +
				'}';
	}

}
