package net.anweisen.utilities.jda.commandmanager.impl.parser;

import net.anweisen.utilities.commons.common.Tuple;
import net.anweisen.utilities.commons.misc.ReflectionUtils;
import net.anweisen.utilities.jda.commandmanager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.commandmanager.arguments.ParserOptions;

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
		return Tuple.ofA("invalid-enum-" + info.getSimpleName().toLowerCase());
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
				.requireExtraInfo();
	}
}
