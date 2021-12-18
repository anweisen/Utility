package net.anweisen.utility.jda.manager.hooks.registered;

import net.anweisen.utility.common.collection.WrappedException;
import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.jda.manager.ParserContext;
import net.anweisen.utility.jda.manager.arguments.ArgumentParser;
import net.anweisen.utility.jda.manager.arguments.ParserOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class RequiredArgument {

	private final int givenLength;
	private final String key;
	private final String name;
	private final ArgumentParser<?, ?> parser;
	private final Object infoContainer;
	private final Class<?> classOfArgument;

	public RequiredArgument(@Nonnull ParserContext context, @Nonnull RegisteredCommand command, @Nonnull String input) {

		String[] args = input.split(" ");
		StringBuilder customName = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			if (customName.length() > 0) customName.append(" ");
			customName.append(args[i]);
		}

		String argument = args[0];

		int startLengthIndex = argument.indexOf("|");
		int startInfoIndex = argument.indexOf('(');
		int endInfoIndex = argument.indexOf(')');

		if (startInfoIndex != -1 && endInfoIndex == -1
		 || startInfoIndex == -1 && endInfoIndex != -1)
			throw new IllegalArgumentException("Started info container with '(' but did not find end with ')' in '" + argument + "'");

		if (startLengthIndex != -1) {
			key = argument.substring(0, startLengthIndex);
		} else if (startInfoIndex != -1) {
			key = argument.substring(0, startInfoIndex);
		} else {
			key = argument;
		}

		if (startLengthIndex != -1) {
			String lengthString = (startInfoIndex == -1) ? argument.substring(startLengthIndex + 1) : argument.substring(startLengthIndex + 1, startInfoIndex);
			givenLength = lengthString.equals("...") ? 0 : Integer.parseInt(lengthString);
		} else {
			givenLength = 1;
		}

		int startLastTileIndex = key.indexOf(":");
		if (customName.length() > 0) {
			this.name = customName.toString();
		} else if (startLastTileIndex == -1) {
			this.name = key;
		} else {
			this.name = key.substring(startLastTileIndex + 1);
		}

		Tuple<ArgumentParser<?, ?>, Class<?>> pair = context.getParser(key);
		if (pair == null) throw new IllegalArgumentException("No such argument parser '" + key + "' ('" + input + "')");

		parser = pair.getFirst();
		classOfArgument = pair.getSecond();

		try {
			infoContainer = startInfoIndex == -1 ? null : parser.parseInfoContainer(argument.substring(startInfoIndex + 1, endInfoIndex));
		} catch (Exception ex) {
			throw new WrappedException(ex);
		}

		ParserOptions options = parser.options();
		if (options.getMaxMultiWords() != 0 && givenLength > options.getMaxMultiWords())
			throw new IllegalArgumentException("Wanted argument length " + givenLength + " is bigger than max length " + options.getMaxMultiWords() + " for argument '" + key + "'");
		if (options.isExtraInfoRequired() && infoContainer == null)
			throw new IllegalArgumentException("Argument '" + key + "' requires extra information");
		if (!options.getScopes().contains(command.getOptions().getScope()))
			throw new IllegalArgumentException("Argument '" + key + "' is only usable in " + options.getScopes() + ", not in " + command.getOptions().getScope());

	}

	@Nonnull
	public ArgumentParser<?, ?> getParser() {
		return parser;
	}

	@Nullable
	public Object getInfoContainer() {
		return infoContainer;
	}

	@Nonnull
	public Class<?> getClassOfArgument() {
		return classOfArgument;
	}

	public int getLength() {
		return givenLength;
	}

	@Nonnull
	public String getKey() {
		return key;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Argument{" +
				"length=" + givenLength +
				", key='" + key + "'" +
				", classOfArgument=" + classOfArgument.getName() +
				'}';
	}

}
