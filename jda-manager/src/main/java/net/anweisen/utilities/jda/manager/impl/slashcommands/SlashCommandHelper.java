package net.anweisen.utilities.jda.manager.impl.slashcommands;

import net.anweisen.utilities.common.collection.StringBuilderPrintWriter;
import net.anweisen.utilities.common.collection.WrappedException;
import net.anweisen.utilities.jda.manager.CommandManager;
import net.anweisen.utilities.jda.manager.hooks.registered.RegisteredCommand;
import net.anweisen.utilities.jda.manager.hooks.registered.RequiredArgument;
import net.anweisen.utilities.jda.manager.impl.slashcommands.build.IBaseCommandData;
import net.anweisen.utilities.jda.manager.impl.slashcommands.build.ICommandData;
import net.anweisen.utilities.jda.manager.impl.slashcommands.build.ISubcommandData;
import net.anweisen.utilities.jda.manager.impl.slashcommands.build.ISubcommandGroupData;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public final class SlashCommandHelper {

	private final Map<String, ICommandData> slashCommands = new HashMap<>();
	private final Collection<RegisteredCommand> commands;
	private final JDA jda;

	private Collection<CommandData> convertedCommands;

	public SlashCommandHelper(@Nonnull JDA jda, @Nonnull Collection<RegisteredCommand> commands) {
		this.jda = jda;
		this.commands = commands;
	}

	public void execute() { // pretty ugly code
		sortSlashCommands();

		StringBuilderPrintWriter writer = new StringBuilderPrintWriter();
		dump(writer);
		String dump = writer.toString();

		try {
			convertSlashCommands();
		} catch (WrappedException ex) {
			CommandManager.LOGGER.error("Failed to register command hierarchy" + dump);
			throw ex;
		}

		registerSlashCommands();
	}

	private void sortSlashCommands() {
		for (RegisteredCommand command : commands) {
			if (!command.getOptions().getAllowSlashCommands()) continue;

			IBaseCommandData<?, ?> data = acquireCommand(command);

			if (data.hasOptions() && command.getArguments().length > 0)
				continue;

			for (RequiredArgument argument : command.getArguments())
				data.addOption(new OptionData(argument.getParser().asSlashCommandType(), argument.getName(), " "));
		}
	}

	private void convertSlashCommands() {
		convertedCommands = new ArrayList<>(slashCommands.size());
		slashCommands.forEach((name, data) -> convertedCommands.add(data.convert()));
	}

	private void registerSlashCommands() {
		jda.updateCommands().addCommands(convertedCommands).queue();
	}

	@Nonnull
	private IBaseCommandData<?, ?> acquireCommand(@Nonnull RegisteredCommand command) {

		String name = command.getOptions().getFirstName();
		List<String> subcommandNames = new ArrayList<>(Arrays.asList(name.split(" ")));
		String parentName = subcommandNames.get(0);

		ICommandData parentRootCommand = slashCommands.computeIfAbsent(parentName, key -> new ICommandData(parentName));

		switch (subcommandNames.size()) {
			default:
				throw new IllegalArgumentException("Discord only supports 3 chained command names for subcommands in slashcommands (got " + subcommandNames.size() + ": '" + name + "')");
			case 1:
				return parentRootCommand;
			case 2: {
				String commandName = subcommandNames.get(1);
				ISubcommandData commandData = new ISubcommandData(commandName);
				parentRootCommand.addSubcommand(commandData);
				return commandData;
			}
			case 3: {
				String subCommandName = subcommandNames.get(1);
				ISubcommandGroupData subcommandGroup = parentRootCommand.getSubcommandGroups()
					.stream().filter(data -> data.getName().equals(subCommandName))
					.findFirst().orElseGet(() -> {
						ISubcommandGroupData result = new ISubcommandGroupData(subCommandName);
						parentRootCommand.addSubcommandGroup(result);
						return result;
					});

				String commandName = subcommandNames.get(2);
				ISubcommandData commandData = new ISubcommandData(commandName);
				subcommandGroup.addSubcommand(commandData);
				return commandData;
			}
		}
	}

	private void dump(@Nonnull PrintWriter out) {
		slashCommands.forEach((__, command) -> {
			out.printf("> %s (%s | %s):%n", command.getName(), command.getSubcommands().size(), command.getSubcommandGroups().size());
			command.getOptions().forEach(option -> {
				out.printf("  * %s: %s%n", option.getName(), option.getType());
			});
			command.getSubcommands().forEach(subcommand -> {
				out.printf("  - %s%n", subcommand.getName());
			});
			command.getSubcommandGroups().forEach(subcommand -> {
				out.printf("  - %s (%s)%n", subcommand.getName(), subcommand.getSubcommands().size());
				subcommand.getSubcommands().forEach(subsubcommand -> {
					out.printf("    - %s%n", subsubcommand.getName());
				});
			});
		});
	}

}
