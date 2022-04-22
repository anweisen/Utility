package net.anweisen.utility.jda.manager.hooks;

import net.anweisen.utility.common.annotations.ReplaceWith;
import net.anweisen.utility.jda.manager.hooks.option.CommandOptions;
import net.anweisen.utility.jda.manager.hooks.option.CommandScope;
import net.anweisen.utility.jda.manager.hooks.option.CoolDownScope;
import net.dv8tion.jda.api.Permission;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class CommandAdapter implements InterfacedCommand {

	private final CommandOptions options = new CommandOptions();

	public CommandAdapter() {
	}

	public CommandAdapter(@Nonnull String... names) {
		setName(names);
	}

	public CommandAdapter(@Nonnull Permission permission, @Nonnull String... names) {
		setPermission(permission);
		setName(names);
	}

	public CommandAdapter(@Nonnull CommandScope scope, @Nonnull String... names) {
		setScope(scope);
		setName(names);
	}

	public CommandAdapter(@Nonnull Permission permission, boolean async, @Nonnull String... names) {
		setPermission(permission);
		setAsync(async);
		setName(names);
	}

	public CommandAdapter(@Nonnull CommandScope scope, boolean async, @Nonnull String... names) {
		setScope(scope);
		setAsync(async);
		setName(names);
	}

	@Deprecated
	@ReplaceWith("CommandAdapter(Permission, boolean, String...)")
	public CommandAdapter(@Nonnull String name, @Nonnull Permission permission, boolean async, @Nonnull String... alias) {
		setName(name);
		setPermission(permission);
		setAsync(async);
		setAlias(alias);
	}

	@Deprecated
	@ReplaceWith("CommandAdapter(CommandScope, boolean, String...)")
	public CommandAdapter(@Nonnull String name, @Nonnull CommandScope scope, boolean async, @Nonnull String... alias) {
		setName(name);
		setScope(scope);
		setAsync(async);
		setAlias(alias);
	}

	@Deprecated
	@ReplaceWith("CommandAdapter(Permission, String...)")
	public CommandAdapter(@Nonnull String name, @Nonnull Permission permission, @Nonnull String... alias) {
		setName(name);
		setPermission(permission);
		setAlias(alias);
	}

	protected final void setName(@Nonnull String name) {
		setName(new String[]{name});
	}

	protected final void setName(@Nonnull String... names) {
		options.name(names);
	}

	@Deprecated
	@ReplaceWith("setName(String...)")
	protected final void setAlias(@Nonnull String... alias) {
		String[] originalNames = options.getName();
		String[] names = new String[originalNames.length + alias.length];

		System.arraycopy(originalNames, 0, names, 0, originalNames.length);
		System.arraycopy(alias, 0, names, originalNames.length, alias.length);

		setName(names);
	}

	protected final void setPermission(@Nullable Permission permission) {
		if (permission != null && permission != Permission.UNKNOWN) setScope(CommandScope.GUILD);
		options.permission(permission == null ? Permission.UNKNOWN : permission);
	}

	protected final void setScope(@Nonnull CommandScope scope) {
		options.scope(scope);
	}

	@Deprecated
	@ReplaceWith("setScope(CommandScope)")
	protected final void setType(@Nonnull CommandScope type) {
		setScope(type);
	}

	protected final void setAsync(boolean async) {
		options.async(async);
	}

	@Deprecated
	protected final void setReactToMentionPrefix(boolean react) {
		throw new UnsupportedOperationException("setReactToMentionPrefix");
	}

	protected final void setReactToWebhooks(boolean react) {
		options.allowWebHooks(react);
	}

	protected final void setReactToBots(boolean react) {
		options.allowBots(react);
	}

	protected final void setDisableMentions(boolean disable) {
		options.disableMentions(disable);
	}

	protected final void setExecuteOnUpdate(boolean execute) {
		options.allowEdits(execute);
	}

	protected final void setTeamCommand(boolean teamCommand) {
		options.team(teamCommand);
	}

	protected final void setUsage(@Nonnull String usage) {
		options.usage(usage);
	}

	protected final void setCoolDown(@Nonnull CoolDownScope scope, @Nonnegative double seconds) {
		options.cooldown(scope, seconds);
	}

	protected final void setSendTyping(boolean send) {
		options.sendTyping(send);
	}

	protected final void setSlashCommands(boolean allowed) {
		options.allowSlashCommands(allowed);
	}

	@Nonnull
	@Override
	public final CommandOptions options() {
		return options;
	}

}
