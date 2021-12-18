package net.anweisen.utility.database.action;

import net.anweisen.utility.common.logging.ILogger;
import net.anweisen.utility.common.logging.LogLevel;
import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.Documents;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see DatabaseQuery#execute()
 */
public interface ExecutedQuery extends Iterable<Document> {

	@Nonnull
	@CheckReturnValue
	Optional<Document> first();

	@Nonnull
	@CheckReturnValue
	default Document firstOrEmpty() {
		return first().orElse(Documents.emptyDocument());
	}

	@Nonnull
	@CheckReturnValue
	Optional<Document> get(int index);

	@Nonnull
	@CheckReturnValue
	default Document getOrEmpty(int index) {
		return get(index).orElse(Documents.emptyDocument());
	}

	@Nonnull
	@CheckReturnValue
	Stream<Document> all();

	@Nonnull
	@CheckReturnValue
	default List<Document> toList() {
		return intoNew(ArrayList::new);
	}

	@Nonnull
	@CheckReturnValue
	default <T> List<T> toList(@Nonnull Function<? super Document, ? extends T> mapper) {
		return mapIntoNew(ArrayList::new, mapper);
	}

	@Nonnull
	@CheckReturnValue
	default Set<Document> toSet() {
		return intoNew(HashSet::new);
	}

	@Nonnull
	<C extends Collection<? super Document>> C into(@Nonnull C collection);

	@Nonnull
	<T, C extends Collection<? super T>> C mapInto(@Nonnull C collection, @Nonnull Function<? super Document, ? extends T> mapper);

	@Nonnull
	@CheckReturnValue
	default <T, C extends Collection<T>> C mapIntoNew(@Nonnull IntFunction<C> collectionSupplier, @Nonnull Function<? super Document, ? extends T> mapper) {
		return mapInto(collectionSupplier.apply(size()), mapper);
	}

	@Nonnull
	@CheckReturnValue
	default <C extends Collection<? super Document>> C intoNew(@Nonnull IntFunction<C> collectionSupplier) {
		return into(collectionSupplier.apply(size()));
	}

	@Nonnull
	@CheckReturnValue
	<K, V> Map<K, V> toMap(@Nonnull Function<? super Document, ? extends K> keyMapper, @Nonnull Function<? super Document, ? extends V> valueMapper);

	@Nonnull
	Document[] toArray(@Nonnull IntFunction<Document[]> arraySupplier);

	int index(@Nonnull Predicate<? super Document> filter);

	boolean isEmpty();

	boolean isSet();

	int size();

	@Nonnull
	ExecutedQuery print(@Nonnull PrintStream out);

	@Nonnull
	default ExecutedQuery print(@Nonnull ILogger logger) {
		return print(logger.asPrintStream(LogLevel.INFO));
	}

	@Nonnull
	default ExecutedQuery print() {
		return print(System.out);
	}

}
