package net.anweisen.utilities.database.action;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.common.logging.ILogger;
import net.anweisen.utilities.common.logging.LogLevel;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ExecutedQuery extends Iterable<Document> {

	@Nonnull
	@CheckReturnValue
	Optional<Document> first();

	@Nonnull
	@CheckReturnValue
	default Document firstOrEmpty() {
		return first().orElse(Document.empty());
	}

	@Nonnull
	@CheckReturnValue
	Optional<Document> get(int index);

	@Nonnull
	@CheckReturnValue
	default Document getOrEmpty(int index) {
		return get(index).orElse(Document.empty());
	}

	@Nonnull
	@CheckReturnValue
	Stream<Document> all();

	@Nonnull
	<C extends Collection<? super Document>> C into(@Nonnull C collection);

	int index(@Nonnull Predicate<? super Document> filter);

	boolean isEmpty();

	boolean isSet();

	int size();

	void print(@Nonnull PrintStream out);

	default void print(@Nonnull ILogger logger) {
		print(logger.asPrintStream(LogLevel.INFO));
	}

	default void print() {
		print(System.out);
	}

}
