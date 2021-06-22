package net.anweisen.utilities.jda.manager.impl.slashcommands.build;

import net.anweisen.utilities.common.collection.WrappedException;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public class ICommandData implements IBaseCommandData<ICommandData, CommandData> {

	private final String name;

	private final List<ISubcommandData> subcommands = new ArrayList<>();
	private final List<ISubcommandGroupData> subcommandGroups = new ArrayList<>();

	private final List<OptionData> options = new ArrayList<>();

	public ICommandData(@Nonnull String name) {
		this.name = name;
	}

	public void addSubcommand(@Nonnull ISubcommandData subcommand) {
		subcommands.add(subcommand);
	}

	public void addSubcommandGroup(@Nonnull ISubcommandGroupData subcommandGroup) {
		subcommandGroups.add(subcommandGroup);
	}

	@Override
	public void addOption(@Nonnull OptionData data) {
		options.add(data);
	}

	@Nonnull
	public List<OptionData> getOptions() {
		return options;
	}

	public String getName() {
		return name;
	}

	@Nonnull
	public List<ISubcommandData> getSubcommands() {
		return subcommands;
	}

	@Nonnull
	public List<ISubcommandGroupData> getSubcommandGroups() {
		return subcommandGroups;
	}

	@Override
	public boolean hasOptions() {
		return !options.isEmpty();
	}

	@Override
	public boolean hasSubCommands() {
		return !subcommands.isEmpty() || !subcommandGroups.isEmpty();
	}

	@Override
	public void clearSubCommands() {
		subcommandGroups.clear();
		subcommands.clear();
	}

	@Override
	public void clearOptions() {
		options.clear();
	}

	@Nonnull
	public CommandData convert() {
		CommandData data = new CommandData(name, " ");

		try {
			options.forEach(option -> data.addOptions(option));
			subcommands.forEach(subcommand -> data.addSubcommands(subcommand.convert()));
			subcommandGroups.forEach(group -> data.addSubcommandGroups(group.convert()));
		} catch (IllegalArgumentException ex) {
			throw new WrappedException("Cannot convert '" + name + "'", ex);
		}

		return data;
	}

}
