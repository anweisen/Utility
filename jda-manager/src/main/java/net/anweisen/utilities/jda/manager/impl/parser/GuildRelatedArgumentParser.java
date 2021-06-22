package net.anweisen.utilities.jda.manager.impl.parser;

import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.hooks.option.CommandScope;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.manager.arguments.ParserOptions;
import net.anweisen.utilities.jda.manager.impl.parser.GuildRelatedArgumentParser.Options;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class GuildRelatedArgumentParser<T> implements ArgumentParser<T, Options> {

	protected static class Options {
		private boolean nullable;
	}

	private final BiFunction<? super Guild, ? super String, ? extends T> mapper;
	private final OptionType slashCommandOptionType;

	public GuildRelatedArgumentParser(@Nonnull OptionType slashCommandOptionType, @Nonnull BiFunction<? super Guild, ? super String, ? extends T> mapper) {
		this.slashCommandOptionType = slashCommandOptionType;
		this.mapper = mapper;
	}

	@Nullable
	@Override
	public T parse(@Nonnull CommandEvent event, @Nullable Options info, @Nonnull String input) throws Exception {
		T value = mapper.apply(event.getGuild(), input);
		if (value == null && (info == null || !info.nullable)) throw new NullPointerException();
		return value;
	}

	@Nonnull
	@Override
	public OptionType asSlashCommandType() {
		return slashCommandOptionType;
	}

	@Nullable
	@Override
	public Options parseInfoContainer(@Nonnull String input) throws Exception {
		Options options = new Options();
		options.nullable = input.contains("nullable");
		return options;
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
				.nullable(true)
				.withScopes(CommandScope.GUILD);
	}

}
