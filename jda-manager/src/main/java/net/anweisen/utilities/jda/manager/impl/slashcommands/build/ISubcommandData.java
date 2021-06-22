package net.anweisen.utilities.jda.manager.impl.slashcommands.build;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public class ISubcommandData implements IBaseCommandData<ISubcommandData, SubcommandData> {

	private final String name;

	private final List<OptionData> options = new ArrayList<>();

	public ISubcommandData(@Nonnull String name) {
		this.name = name;
	}

	@Override
	public void addOption(@Nonnull OptionData data) {
		options.add(data);
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Override
	public boolean hasOptions() {
		return !options.isEmpty();
	}

	@Override
	public boolean hasSubCommands() {
		return false;
	}

	@Override
	public void clearOptions() {
		options.clear();
	}

	@Override
	public void clearSubCommands() {
	}

	@Nonnull
	public SubcommandData convert() {
		SubcommandData data = new SubcommandData(name, " ");
		options.forEach(data::addOptions);
		return data;
	}

}
