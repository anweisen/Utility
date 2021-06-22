package net.anweisen.utilities.jda.manager;

import net.anweisen.utilities.common.collection.Tuple;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ParserContext {

	@Nonnull
	ParserContext registerParser(@Nonnull String key, @Nonnull Class<?> clazz, @Nonnull ArgumentParser<?, ?> parser);

	@Nullable
	Tuple<ArgumentParser<?, ?>, Class<?>> getParser(@Nonnull String key);

}
