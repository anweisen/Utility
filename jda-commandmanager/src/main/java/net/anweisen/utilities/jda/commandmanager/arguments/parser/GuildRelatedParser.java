package net.anweisen.utilities.jda.commandmanager.arguments.parser;

import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class GuildRelatedParser<T> implements ArgumentParser<T> {

	private final BiFunction<? super Guild, ? super String, ? extends T> mapper;

	public GuildRelatedParser(@Nonnull BiFunction<? super Guild, ? super String, ? extends T> mapper) {
		this.mapper = mapper;
	}

	@Nullable
	@Override
	public T parse(@Nonnull CommandEvent event, @Nonnull String input) throws Exception {
		return mapper.apply(event.getGuild(), input);
	}

}
