package net.anweisen.utilities.jda.manager.impl.slashcommands.build;

import net.dv8tion.jda.api.interactions.commands.build.BaseCommand;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.3.0
 */
public interface IBaseCommandData<I extends IBaseCommandData<I, ?>, T extends BaseCommand<?>> {

	boolean hasOptions();
	boolean hasSubCommands();

	void clearOptions();
	void clearSubCommands();

	void addOption(@Nonnull OptionData data);

}
