package net.anweisen.utilities.jda.manager.impl.parser;

import net.anweisen.utilities.common.misc.StringUtils;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class TimeArgumentParser implements ArgumentParser<Long, Object> {

	@Nullable
	@Override
	public Long parse(@Nonnull CommandEvent event, @Nullable Object info, @Nonnull String input) throws Exception {
		return StringUtils.parseSeconds(input);
	}

}
