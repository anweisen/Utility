package net.anweisen.utilities.database.action;

import net.anweisen.utilities.commons.config.Document;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
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
	Document firstOrEmpty();

	@Nonnull
	@CheckReturnValue
	Optional<Document> get(int index);

	@Nonnull
	@CheckReturnValue
	Stream<Document> all();

	@Nonnull
	@CheckReturnValue
	<C extends Collection<? super Document>> C into(@Nonnull C collection);

	int index(@Nonnull Predicate<? super Document> filter);

	boolean isEmpty();

	boolean isSet();

	int size();

	void print();

}