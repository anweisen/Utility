package net.anweisen.utilities.common.config;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Document
 */
public interface Json {

	@Nonnull
	String toJson();

	@Nonnull
	String toPrettyJson();

	@Nonnull
	@CheckReturnValue
	static Json empty() {
		return constant("{}");
	}

	@Nonnull
	@CheckReturnValue
	static Json supply(@Nonnull Supplier<String> normal, @Nonnull Supplier<String> pretty) {
		return new Json() {
			@Nonnull
			@Override
			public String toJson() {
				return normal.get();
			}

			@Nonnull
			@Override
			public String toPrettyJson() {
				return pretty.get();
			}
		};
	}

	@Nonnull
	@CheckReturnValue
	static Json constant(@Nonnull String json) {
		return constant(json, json);
	}

	@Nonnull
	@CheckReturnValue
	static Json constant(@Nonnull String json, @Nonnull String prettyJson) {
		return supply(() -> json, () -> prettyJson);
	}

}
