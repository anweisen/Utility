package net.anweisen.utility.jda.manager.impl;

import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.jda.manager.ParserContext;
import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.impl.parser.*;
import net.anweisen.utility.jda.manager.utils.SearchHelper;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultParserContext implements ParserContext {

	private final Map<String, Tuple<Class<?>, ArgumentParser<?, ?>>> parsersByKeys = new HashMap<>();

	public DefaultParserContext() {
		registerParser("boolean",               Boolean.class,          new BooleanArgumentParser());
		registerParser("double",                Double.class,           new NumberArgumentParser<>(Double::parseDouble));
		registerParser("float",                 Float.class,            new NumberArgumentParser<>(Float::parseFloat));
		registerParser("long",                  Long.class,             new NumberArgumentParser<>(Long::parseLong));
		registerParser("int",                   Integer.class,          new NumberArgumentParser<>(Integer::parseInt));
		registerParser("short",                 Short.class,            new NumberArgumentParser<>(Short::parseShort));
		registerParser("byte",                  Byte.class,             new NumberArgumentParser<>(Byte::parseByte));
		registerParser("char",                  Character.class,        new CharArgumentParser());
		registerParser("string",                String.class,           new StringArgumentParser());
		registerParser("time",                  Long.class,             new TimeArgumentParser());
		registerParser("color",                 Color.class,            new ColorArgumentParser());
		registerParser("uuid",                  UUID.class,             new UUIDArgumentParser());
		registerParser("enum",                  Enum.class,             new EnumArgumentParser());
		registerParser("guild:member",          Member.class,           new MemberArgumentParser());
		registerParser("guild:category",        Category.class,         new GuildRelatedArgumentParser<>(OptionType.STRING, SearchHelper::findCategory));
		registerParser("guild:channel",         GuildChannel.class,     new GuildRelatedArgumentParser<>(OptionType.CHANNEL, SearchHelper::findGuildChannel));
		registerParser("guild:textchannel",     TextChannel.class,      new GuildRelatedArgumentParser<>(OptionType.CHANNEL, SearchHelper::findTextChannel));
		registerParser("guild:voicechannel",    VoiceChannel.class,     new GuildRelatedArgumentParser<>(OptionType.CHANNEL, SearchHelper::findVoiceChannel));
		registerParser("guild:role",            Role.class,             new GuildRelatedArgumentParser<>(OptionType.ROLE, SearchHelper::findRole));
		registerParser("bot:user",              User.class,             new BotRelatedArgumentParser<>(OptionType.USER, SearchHelper::findUser, SearchHelper::findUser));
	}

	@Nonnull
	@Override
	public ParserContext registerParser(@Nonnull String key, @Nonnull Class<?> clazz, @Nonnull ArgumentParser<?, ?> parser) {
		parsersByKeys.put(key, new Tuple<>(clazz, parser));
		return this;
	}

	@Nullable
	@Override
	public Tuple<ArgumentParser<?, ?>, Class<?>> getParser(@Nonnull String key) {
		Tuple<Class<?>, ArgumentParser<?, ?>> pair = parsersByKeys.get(key);
		if (pair == null) return null;
		return new Tuple<>(pair.getSecond(), pair.getFirst());
	}

}
