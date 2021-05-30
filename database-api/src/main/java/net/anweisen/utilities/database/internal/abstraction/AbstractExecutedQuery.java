package net.anweisen.utilities.database.internal.abstraction;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.database.action.ExecutedQuery;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class AbstractExecutedQuery implements ExecutedQuery {

	protected final List<Document> results;

	public AbstractExecutedQuery(@Nonnull List<Document> results) {
		this.results = results;
	}

	@Nonnull
	@Override
	public Optional<Document> first() {
		if (results.isEmpty()) return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	@Nonnull
	@Override
	public Document firstOrEmpty() {
		return first().orElse(Document.empty());
	}

	@Nonnull
	@Override
	public Optional<Document> get(int index) {
		if (index >= results.size()) return Optional.empty();
		return Optional.ofNullable(results.get(index));
	}

	@Nonnull
	@Override
	public Stream<Document> all() {
		return results.stream();
	}

	@Nonnull
	@Override
	public <C extends Collection<? super Document>> C into(@Nonnull C collection) {
		collection.addAll(results);
		return collection;
	}

	@Override
	public int index(@Nonnull Predicate<? super Document> filter) {
		int index = 0;
		for (Document result : results) {
			if (filter.test(result))
				return index;
			index++;
		}
		return index;
	}

	@Override
	public boolean isEmpty() {
		return results.isEmpty();
	}

	@Override
	public boolean isSet() {
		return !results.isEmpty();
	}

	@Override
	public int size() {
		return results.size();
	}

	@Override
	public void print() {
		if (results.isEmpty()) {
			System.out.println("<Empty Result>");
			return;
		}

		int index = 0;
		for (Document result : results) {
			System.out.print(index + ". | ");
			for (Entry<String, Object> entry : result.values().entrySet()) {
				System.out.print(entry.getKey() + " = '" + entry.getValue() + "' ");
			}
			System.out.println();
			index++;
		}
	}

	@Override
	public Iterator<Document> iterator() {
		return Collections.unmodifiableCollection(results).iterator();
	}

}
