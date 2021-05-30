package net.anweisen.utilities.jda.commandmanager.impl.parser;

import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.hooks.CommandScope;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.commandmanager.arguments.ParserOptions;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class GuildRelatedArgumentParser<T> implements ArgumentParser<T, Object> {

	private final BiFunction<? super Guild, ? super String, ? extends T> mapper;

	public GuildRelatedArgumentParser(@Nonnull BiFunction<? super Guild, ? super String, ? extends T> mapper) {
		this.mapper = mapper;
	}

	@Nullable
	@Override
	public T parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		return mapper.apply(event.getGuild(), input);
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
				.withScopes(CommandScope.GUILD);
	}

}
