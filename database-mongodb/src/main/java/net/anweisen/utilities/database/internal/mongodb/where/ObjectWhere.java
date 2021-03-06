package net.anweisen.utilities.database.internal.mongodb.where;

import com.mongodb.client.model.Collation;
import net.anweisen.utilities.common.misc.MongoUtils;
import org.bson.conversions.Bson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ObjectWhere implements MongoDBWhere {

	protected final String field;
	protected final Object value;
	protected final BiFunction<? super String, ? super Object, ? extends Bson> creator;

	public ObjectWhere(@Nonnull String field, @Nullable Object value, @Nonnull BiFunction<? super String, ? super Object, ? extends Bson> creator) {
		this.field = field;
		this.value = MongoUtils.packObject(value);
		this.creator = creator;
	}

	@Nonnull
	@Override
	public Bson toBson() {
		return creator.apply(field, value);
	}

	@Nullable
	@Override
	public Collation getCollation() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ObjectWhere that = (ObjectWhere) o;
		return field.equals(that.field) && Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(field, value);
	}

}
