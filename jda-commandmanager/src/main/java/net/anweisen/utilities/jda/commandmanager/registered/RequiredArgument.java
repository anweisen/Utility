package net.anweisen.utilities.jda.commandmanager.registered;

import net.anweisen.utilities.commons.common.Tuple;
import net.anweisen.utilities.jda.commandmanager.ParserContext;
import net.anweisen.utilities.jda.commandmanager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.commandmanager.arguments.ParserOptions;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class RequiredArgument {

	private final int givenLength;
	private final String key;
	private final String name;
	private final ArgumentParser<?> parser;
	private final Class<?> classOfArgument;

	public RequiredArgument(@Nonnull ParserContext context, @Nonnull String input) {

		String[] args = input.split(" ");
		StringBuilder name = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			if (name.length() > 0) name.append(" ");
			name.append(args[i]);
		}

		String argument = args[0];

		int startLengthIndex = input.indexOf("|");

		if (startLengthIndex != -1) {
			key = argument.substring(0, startLengthIndex);
		} else {
			key = argument;
		}

		this.name = name.length() == 0 ? key : name.toString();

		Tuple<ArgumentParser<?>, Class<?>> pair = context.getParser(key);
		if (pair == null) throw new IllegalArgumentException("No such argument parser '" + key + "' in '" + input + "'");

		parser = pair.getFirst();
		classOfArgument = pair.getSecond();

		if (startLengthIndex != -1) {
			String lengthString = argument.substring(startLengthIndex + 1);
			givenLength = lengthString.equals("...") ? 0 : Integer.parseInt(lengthString);
		} else {
			givenLength = 1;
		}

		ParserOptions options = parser.options();
		if (options.getMaxMultiWords() != 0 && givenLength > options.getMaxMultiWords())
			throw new IllegalArgumentException("Wanted argument length " + givenLength + " is bigger than max length " + options.getMaxMultiWords() + " for argument '" + key + "'");

	}

	@Nonnull
	public ArgumentParser<?> getParser() {
		return parser;
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
				", classOfArgument=" + classOfArgument +
				'}';
	}

}
