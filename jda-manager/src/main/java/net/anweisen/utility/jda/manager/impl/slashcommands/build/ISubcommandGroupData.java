package net.anweisen.utility.jda.manager.impl.slashcommands.build;

import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public class ISubcommandGroupData {

	private final String name;

	private final List<ISubcommandData> subcommands = new ArrayList<>();

	public ISubcommandGroupData(@Nonnull String name) {
		this.name = name;
	}

	public void addSubcommand(@Nonnull ISubcommandData subcommand) {
		subcommands.add(subcommand);
	}

	@Nonnull
	public List<ISubcommandData> getSubcommands() {
		return subcommands;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public SubcommandGroupData convert() {
		SubcommandGroupData data = new SubcommandGroupData(name, " ");
		subcommands.forEach(subcommand -> data.addSubcommands(subcommand.convert()));
		return data;
	}

}
