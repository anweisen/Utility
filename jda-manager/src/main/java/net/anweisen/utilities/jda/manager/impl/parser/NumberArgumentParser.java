package net.anweisen.utilities.jda.manager.impl.parser;

import net.anweisen.utilities.common.collection.Tuple;
import net.anweisen.utilities.jda.manager.hooks.event.CommandEvent;
import net.anweisen.utilities.jda.manager.arguments.ArgumentParser;
import net.anweisen.utilities.jda.manager.arguments.ParserOptions;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class NumberArgumentParser<N extends Number> implements ArgumentParser<N, NumberArgumentParser<N>.Range> {

	public final Range POSITIVE = new Range(0, Double.MAX_VALUE),
				   	   NEGATIVE = new Range(Double.MIN_VALUE, -1);

	public class Range {

		private final N min, max;

		@SuppressWarnings("unchecked")
		public Range(@Nullable Number min, @Nullable Number max) {
			this.min = (N) min;
			this.max = (N) max;
		}

		@Override
		public String toString() {
			return "[" + min + ".." + max + "]";
		}
	}

	private final Function<String, ? extends N> parser;

	public NumberArgumentParser(@Nonnull Function<String, ? extends N> parser) {
		this.parser = parser;
	}

	@Nullable
	@Override
	public N parse(@Nonnull CommandEvent event, @Nullable Range range, @Nonnull String input) throws Exception {
		return parser.apply(input);
	}

	@Override
	public boolean validateInfoContainer(@Nonnull Range range, @Nonnull N number) {
		if (range.max != null && number.doubleValue() > range.max.doubleValue()) return false;
		if (range.min != null && number.doubleValue() < range.min.doubleValue()) return false;
		return true;
	}

	@Nullable
	@Override
	public Range parseInfoContainer(@Nonnull String input) {
		if (input.equalsIgnoreCase("positive")) return POSITIVE;
		if (input.equalsIgnoreCase("negative")) return NEGATIVE;

		int index = input.indexOf("..");
		if (index == -1) throw new IllegalArgumentException("Cannot parse number range for '" + input + "'");

		String from = input.substring(0, index);
		String to = input.substring(index + 2);
		N min = from.isEmpty()  ? null : parser.apply(from);
		N max = to.isEmpty()    ? null : parser.apply(to);
		return new Range(min, max);
	}

	@Nullable
	@Override
	public Tuple<String, Object[]> getErrorMessage(@Nullable Range range, @Nullable N context) {
		if (range == null)           return null;
		if (range == POSITIVE) return new Tuple<>("invalid-number-positive", null);
		if (range == NEGATIVE) return new Tuple<>("invalid-number-negative", null);
		if (range.max == null) return new Tuple<>("invalid-number-greater", new Object[] { range.min });
		if (range.min == null) return new Tuple<>("invalid-number-smaller", new Object[] { range.max });
		return new Tuple<>("invalid-number-range", new Object[] { range.min, range.max });
	}

	@Nonnull
	@Override
	public ParserOptions options() {
		return ArgumentParser.super.options()
				.disableMultiWords();
	}

	@Nonnull
	@Override
	public OptionType asSlashCommandType() {
		return OptionType.INTEGER;
	}
}
