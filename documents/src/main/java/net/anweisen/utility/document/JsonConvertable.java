package net.anweisen.utility.document;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Objects with this interface can be serialized to a json string.
 *
 * @author anweisen | https://github.com/anweisen
 * @see Document
 * @see Bundle
 * @see IEntry
 * @see #toJson()
 * @see #toPrettyJson()
 * @since 1.0
 */
public interface JsonConvertable {

	@Nonnull
	String toJson();

	@Nonnull
	String toPrettyJson();

	@Nonnull
	@CheckReturnValue
	static JsonConvertable newEmptyObject() {
		return of("{}", "{}");
	}

	@Nonnull
	@CheckReturnValue
	static JsonConvertable newEmptyArray() {
		return of("[]", "[]");
	}

	@Nonnull
	@CheckReturnValue
	static JsonConvertable of(@Nonnull Supplier<String> normal, @Nonnull Supplier<String> pretty) {
		return new JsonConvertable() {
			@Nonnull
			public String toJson() {
				return normal.get();
			}

			@Nonnull
			public String toPrettyJson() {
				return pretty.get();
			}
		};
	}

	@Nonnull
	@CheckReturnValue
	static JsonConvertable of(@Nonnull String json, @Nonnull String prettyJson) {
		return of(() -> json, () -> prettyJson);
	}

}
