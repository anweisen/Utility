package net.anweisen.utilities.jda.commandmanager.impl;

import net.anweisen.utilities.commons.common.Tuple;
import net.anweisen.utilities.jda.commandmanager.ParserContext;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.commandmanager.utils.SearchHelper;
import net.anweisen.utilities.jda.commandmanager.arguments.parser.*;
import net.dv8tion.jda.api.entities.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultParserContext implements ParserContext {

	private final Map<String, Tuple<Class<?>, ArgumentParser<?>>> parsersByKeys = new HashMap<>();

	public DefaultParserContext() {
		registerParser("double",                Double.class,           new NumberParser(Double::parseDouble));
		registerParser("float",                 Float.class,            new NumberParser(Float::parseFloat));
		registerParser("long",                  Long.class,             new NumberParser(Long::parseLong));
		registerParser("int",                   Integer.class,          new NumberParser(Integer::parseInt));
		registerParser("short",                 Short.class,            new NumberParser(Short::parseShort));
		registerParser("byte",                  Byte.class,             new NumberParser(Byte::parseByte));
		registerParser("char",                  Character.class,        new CharParser());
		registerParser("string",                String.class,           new StringParser());
		registerParser("time",                  Integer.class,          new TimeParser());
		registerParser("guild:member",          Member.class,           new GuildRelatedParser<>(SearchHelper::findMember));
		registerParser("guild:category",        Category.class,         new GuildRelatedParser<>(SearchHelper::findCategory));
		registerParser("guild:channel",         GuildChannel.class,     new GuildRelatedParser<>(SearchHelper::findGuildChannel));
		registerParser("guild:textchannel",     TextChannel.class,      new GuildRelatedParser<>(SearchHelper::findTextChannel));
		registerParser("guild:voicechannel",    VoiceChannel.class,     new GuildRelatedParser<>(SearchHelper::findVoiceChannel));
		registerParser("guild:role",            Role.class,             new GuildRelatedParser<>(SearchHelper::findRole));
	}

	@Nonnull
	@Override
	public ParserContext registerParser(@Nonnull String key, @Nonnull Class<?> clazz, ArgumentParser<?> parser) {
		parsersByKeys.put(key, new Tuple<>(clazz, parser));
		return this;
	}

	@Nullable
	@Override
	public Tuple<ArgumentParser<?>, Class<?>> getParser(@Nonnull String key) {
		Tuple<Class<?>, ArgumentParser<?>> pair = parsersByKeys.get(key);
		if (pair == null) return null;
		return new Tuple<>(pair.getSecond(), pair.getFirst());
	}

}
