package net.anweisen.utility.jda.manager.impl.parser;

import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.arguments.ParserOptions;
import net.anweisen.utility.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utility.jda.manager.hooks.option.CommandScope;
import net.anweisen.utility.jda.manager.impl.parser.MemberArgumentParser.Info;
import net.anweisen.utility.jda.manager.utils.SearchHelper;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MemberArgumentParser implements ArgumentParser<Member, Info> {

	protected static class Info {
		private boolean allowBots;
		private boolean allowUser;
	}

	@Nullable
	@Override
	public Member parse(@Nonnull CommandEvent event, @Nullable Info info, @Nonnull String input) throws Exception {
		return SearchHelper.findMember(event, input);
	}

	@Nullable
	@Override
	public Info parseInfoContainer(@Nonnull String input) {
		Info info = new Info();
		info.allowUser = input.contains("user");
		info.allowBots = input.contains("bot");
		return info;
	}

	@Override
	public boolean validateInfoContainer(@Nonnull Info info, @Nonnull Member context) {
		if (context.getUser().isBot() && !info.allowBots) return false;
		if (!context.getUser().isBot() && !info.allowUser) return false;
		return true;
	}

	@Nullable
	@Override
	public Tuple<String, Object[]> getErrorMessage(@Nullable Info info, @Nullable Member context) {
		if (info != null && context != null) {
			if (info.allowUser) return Tuple.ofFirst("invalid-member-user");
			if (info.allowBots) return Tuple.ofFirst("invalid-member-bot");
		}

		return Tuple.ofFirst("invalid-member");
	}

	@Nonnull
	@Override
	public OptionType asSlashCommandType() {
		return OptionType.USER;
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
				.withScopes(CommandScope.GUILD);
	}

}
