package net.anweisen.utility.database.internal.abstraction;

import net.anweisen.utility.database.action.ExecutedQuery;
import net.anweisen.utility.document.Document;

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
 */
public class DefaultExecutedQuery implements ExecutedQuery {

	protected final List<Document> results;

	public DefaultExecutedQuery(@Nonnull List<Document> results) {
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

	@Nonnull
	@Override
	public <T, C extends Collection<? super T>> C mapInto(@Nonnull C collection, @Nonnull Function<? super Document, ? extends T> mapper) {
		for (Document document : results) {
			T value = mapper.apply(document);
			if (value == null) continue;
			collection.add(value);
		}
		return collection;
	}

	@Nonnull
	@Override
	public  <K, V> Map<K, V> toMap(@Nonnull Function<? super Document, ? extends K> keyMapper, @Nonnull Function<? super Document, ? extends V> valueMapper) {
		Map<K, V> map = new LinkedHashMap<>();
		for (Document document : results) {
			map.put(keyMapper.apply(document), valueMapper.apply(document));
		}
		return map;
	}

	@Nonnull
	@Override
	public Document[] toArray(@Nonnull IntFunction<Document[]> arraySupplier) {
		Document[] array = arraySupplier.apply(size());
		for (int i = 0; i < size(); i++) {
			array[i] = results.get(i);
		}
		return array;
	}

	@Override
	public int index(@Nonnull Predicate<? super Document> filter) {
		int index = 0;
		for (Document result : results) {
			if (filter.test(result))
				return index;
			index++;
		}
		return -1;
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
	@Nonnull
	public ExecutedQuery print(@Nonnull PrintStream out) {
		if (results.isEmpty()) {
			out.println("<Empty ExecutedQuery Result>");
			return this;
		}

		int index = 0;
		for (Document result : results) {
			out.print(index + " | ");
			result.forEach((key, value) -> {
				out.print(key + " = '" + value + "' ");
			});
			out.println();
			index++;
		}
		return this;
	}

	@Override
	public Iterator<Document> iterator() {
		return Collections.unmodifiableCollection(results).iterator();
	}

	@Override
	public String toString() {
		return "ExecutedQuery[size=" + size() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DefaultExecutedQuery documents = (DefaultExecutedQuery) o;
		return Objects.equals(results, documents.results);
	}

	@Override
	public int hashCode() {
		return Objects.hash(results);
	}
}
