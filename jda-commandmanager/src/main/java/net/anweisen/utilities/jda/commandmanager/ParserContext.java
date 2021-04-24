package net.anweisen.utilities.jda.commandmanager;

import net.anweisen.utilities.commons.common.Tuple;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ParserContext {

	@Nonnull
	ParserContext registerParser(@Nonnull String key, @Nonnull Class<?> clazz, ArgumentParser<?> parser);

	@Nullable
	Tuple<ArgumentParser<?>, Class<?>> getParser(@Nonnull String key);

}
