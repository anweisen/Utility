package net.anweisen.utilities.database.internal.mongodb.where;

import com.mongodb.client.model.Collation;
import org.bson.conversions.Bson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public interface MongoDBWhere {

	@Nonnull
	Bson toBson();

	@Nullable
	Collation getCollation();

}
