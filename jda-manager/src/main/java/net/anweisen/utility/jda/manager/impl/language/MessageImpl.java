package net.anweisen.utility.jda.manager.impl.language;

import net.anweisen.utility.common.collection.IRandom;
import net.anweisen.utility.common.misc.StringUtils;
import net.anweisen.utility.jda.manager.language.Message;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class MessageImpl implements Message {

	private final String name;
	private Object value;

	public MessageImpl(@Nonnull String name) {
		this.name = name;
	}

	@Nonnull
	@Override
	public String asString(@Nonnull Object... args) {
		if (value == null)                      return Message.unknown(name);
		if (value instanceof String)            return StringUtils.format((String) value, args);
		if (value instanceof String[])          return StringUtils.getArrayAsString(StringUtils.format((String[]) value, args), "\n");
		LOGGER.error("Message '{}' has an illegal value {}", name, value.getClass().getName());
		return Message.unknown(name);
	}

	@Nonnull
	@Override
	public String[] asArray(@Nonnull Object... args) {
		if (value == null)                      return new String[] { Message.unknown(name)};
		if (value instanceof String[])          return StringUtils.format((String[]) value, args);
		if (value instanceof String)            return StringUtils.getStringAsArray(StringUtils.format((String) value, args));
		LOGGER.error("Message '{}' has an illegal value {}", name, value.getClass().getName());
		return new String[] { Message.unknown(name)};
	}

	@Nonnull
	@Override
	public String asRandomString(@Nonnull Random random, @Nonnull Object... args) {
		return asRandomString(IRandom.wrap(random), args);
	}

	@Nonnull
	@Override
	public String asRandomString(@Nonnull Object... args) {
		return asRandomString(IRandom.threadLocal(), args);
	}

	@Nonnull
	@Override
	public String asRandomString(@Nonnull IRandom random, @Nonnull Object... args) {
		String[] array = asArray(args);
		return random.choose(array);
	}

	@Override
	public void setValue(@Nullable String value) {
		this.value = value;
	}

	@Override
	public void setValue(@Nullable String[] value) {
		this.value = value;
	}

	@Override
	public boolean isEmpty() {
		return value == null;
	}

	@Nonnull
	@Override
	public String getName() {
		return name;
	}

}
