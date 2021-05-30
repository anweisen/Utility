package net.anweisen.utilities.jda.commandmanager.impl.parser;

import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class BotRelatedArgumentParser<T> implements ArgumentParser<T, Object> {

	private final BiFunction<? super JDA, ? super String, ? extends T> jdaMapper;
	private final BiFunction<? super ShardManager, ? super String, ? extends T> shardManagerMapper;

	public BotRelatedArgumentParser(@Nonnull BiFunction<? super JDA, ? super String, ? extends T> jdaMapper,
	                                @Nullable BiFunction<? super ShardManager, ? super String, ? extends T> shardManagerMapper) {
		this.jdaMapper = jdaMapper;
		this.shardManagerMapper = shardManagerMapper;
	}

	@Nullable
	@Override
	public T parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		if (shardManagerMapper != null && event.getShardManager() != null) {
			return shardManagerMapper.apply(event.getShardManager(), input);
		}

		return jdaMapper.apply(event.getJDA(), input);
	}

}
