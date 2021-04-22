package net.anweisen.utilities.jda.commandmanager.registered;

import net.anweisen.utilities.jda.commandmanager.Command;
import net.anweisen.utilities.jda.commandmanager.CommandField;
import net.anweisen.utilities.jda.commandmanager.CoolDownScope;
import net.anweisen.utilities.jda.commandmanager.InterfacedCommand;
import net.dv8tion.jda.api.Permission;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class CommandOptions {

	private String[] name = {};
	private String usage = "";
	private CommandField field = CommandField.GENERAL;
	private Permission permission = Permission.UNKNOWN;
	private boolean team = false;
	private boolean async = true;
	private boolean allowBots = false;
	private boolean allowWebHooks = false;
	private boolean allowEdits = true;
	private boolean disableMentions = true;
	private CoolDownScope cooldownScope = CoolDownScope.USER;
	private double cooldownSeconds = 0;

	public CommandOptions() {
	}

	public CommandOptions(@Nonnull String[] name, @Nonnull String usage) {
		this.name = name;
		this.usage = usage;
	}

	public CommandOptions(@Nonnull String[] name, @Nonnull String usage, @Nonnull CommandField field, @Nonnull Permission permission,
	                      boolean team, boolean async, boolean allowBots, boolean allowWebHooks, boolean allowEdits, boolean disableMentions,
	                      @Nonnull CoolDownScope cooldownScope, @Nonnegative double cooldownSeconds) {
		this.name = name;
		this.usage = usage;
		this.field = field;
		this.permission = permission;
		this.team = team;
		this.async = async;
		this.allowBots = allowBots;
		this.allowWebHooks = allowWebHooks;
		this.allowEdits = allowEdits;
		this.disableMentions = disableMentions;
		this.cooldownScope = cooldownScope;
		this.cooldownSeconds = cooldownSeconds;
	}

	public CommandOptions(@Nonnull Command command) {
		this(command.name(), command.usage(), command.field(), command.permission(),
			 command.team(), command.async(), command.allowBots(), command.allowWebHooks(), command.allowEdits(), command.disableMentions(),
			 command.cooldownScope(), command.cooldownSeconds());
	}

	@Nonnull
	public CommandOptions name(@Nonnull String... name) {
		this.name = name;
		return this;
	}

	@Nonnull
	public CommandOptions usage(@Nonnull String usage) {
		this.usage = usage;
		return this;
	}

	@Nonnull
	public CommandOptions field(@Nonnull CommandField field) {
		this.field = field;
		return this;
	}

	@Nonnull
	public CommandOptions permission(@Nonnull Permission permission) {
		this.permission = permission;
		return this;
	}

	@Nonnull
	public CommandOptions team(boolean teamCommand) {
		this.team = teamCommand;
		return this;
	}

	@Nonnull
	public CommandOptions async(boolean executeAsync) {
		this.async = executeAsync;
		return this;
	}

	@Nonnull
	public CommandOptions allowBots(boolean allowBots) {
		this.allowBots = allowBots;
		return this;
	}

	@Nonnull
	public CommandOptions allowWebHooks(boolean allowWebHooks) {
		this.allowWebHooks = allowWebHooks;
		return this;
	}

	@Nonnull
	public CommandOptions allowEdits(boolean allowEdits) {
		this.allowEdits = allowEdits;
		return this;
	}

	@Nonnull
	public CommandOptions disableMentions(boolean disable) {
		this.disableMentions = disable;
		return this;
	}

	@Nonnull
	public CommandOptions cooldown(@Nonnull CoolDownScope scope) {
		this.cooldownScope = scope;
		return this;
	}

	@Nonnull
	public CommandOptions cooldown(@Nonnegative double seconds) {
		this.cooldownSeconds = seconds;
		return this;
	}

	@Nonnull
	public CommandField getField() {
		return field;
	}

	@Nonnull
	public CoolDownScope getCoolDownScope() {
		return cooldownScope;
	}

	@Nonnegative
	public double getCoolDownSeconds() {
		return cooldownSeconds;
	}

	@Nonnull
	public Permission getPermission() {
		return permission;
	}

	@Nonnull
	public Permission getPermissionOrAdmin() {
		return permission == Permission.UNKNOWN ? Permission.ADMINISTRATOR : permission;
	}

	@Nonnull
	public String getUsage() {
		return usage;
	}

	@Nonnull
	public String[] getName() {
		return name;
	}

	@Nonnull
	public String getFirstName() {
		if (name.length == 0) return "test";
		return name[0];
	}

	public boolean getAllowBots() {
		return allowBots;
	}

	public boolean getAllowEdits() {
		return allowEdits;
	}

	public boolean getAllowWebHooks() {
		return allowWebHooks;
	}

	public boolean isAsync() {
		return async;
	}

	public boolean getDisableMentions() {
		return disableMentions;
	}

	public boolean getTeam() {
		return team;
	}

	@Override
	public String toString() {
		return "CommandOptions{" +
				"name=" + Arrays.toString(name) +
				", usage='" + usage + '\'' +
				", field=" + field +
				", permission=" + permission +
				", team=" + team +
				", async=" + async +
				", allowBots=" + allowBots +
				", allowWebHooks=" + allowWebHooks +
				", allowEdits=" + allowEdits +
				", disableMentions=" + disableMentions +
				", cooldownScope=" + cooldownScope +
				", cooldownSeconds=" + cooldownSeconds +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CommandOptions that = (CommandOptions) o;
		return team == that.team && async == that.async && allowBots == that.allowBots && allowWebHooks == that.allowWebHooks && allowEdits == that.allowEdits && disableMentions == that.disableMentions && Double.compare(that.cooldownSeconds, cooldownSeconds) == 0 && Arrays.equals(name, that.name) && Objects.equals(usage, that.usage) && field == that.field && permission == that.permission && cooldownScope == that.cooldownScope;
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(usage, field, permission, team, async, allowBots, allowWebHooks, allowEdits, disableMentions, cooldownScope, cooldownSeconds);
		result = 31 * result + Arrays.hashCode(name);
		return result;
	}
}
