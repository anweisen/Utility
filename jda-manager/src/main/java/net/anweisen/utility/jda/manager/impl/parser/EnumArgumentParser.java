package net.anweisen.utility.jda.manager.impl.parser;

import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.common.misc.ReflectionUtils;
import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.arguments.ParserOptions;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.5
 */
public class EnumArgumentParser implements ArgumentParser<Object, Class<Enum<?>>> {

	@Nullable
	@Override
	public Object parse(@Nonnull CommandEvent event, @Nullable Class<Enum<?>> info, @Nonnull String input) throws Exception {
		return ReflectionUtils.getEnumByAlternateNames(info, input);
	}

	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public Class<Enum<?>> parseInfoContainer(@Nonnull String input) throws Exception {
		return (Class<Enum<?>>) Class.forName(input);
	}

	@Nullable
	@Override
	public Tuple<String, Object[]> getErrorMessage(@Nullable Class<Enum<?>> info, @Nullable Object context) {
		return Tuple.ofFirst("invalid-enum-" + info.getSimpleName().toLowerCase());
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
			.requireExtraInfo();
	}
}
