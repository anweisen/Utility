package net.anweisen.utilities.jda.commandmanager.arguments.parser;

import net.anweisen.utilities.commons.misc.StringUtils;
import net.anweisen.utilities.jda.commandmanager.CommandEvent;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class TimeParser implements ArgumentParser<Integer> {

	@Nullable
	@Override
	public Integer parse(@Nonnull CommandEvent event, @Nonnull String input) throws Exception {
		return StringUtils.parseSeconds(input);
	}

}
