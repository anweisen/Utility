package net.anweisen.utilities.database.internal.mongodb.query;

import net.anweisen.utilities.database.internal.abstraction.AbstractExecutedQuery;
import org.bson.Document;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ExecutedMongoDBQuery extends AbstractExecutedQuery {

	public ExecutedMongoDBQuery(@Nonnull List<Document> documents) {
		super(new ArrayList<>(documents.size()));
		for (Document document : documents) {
			results.add(new MongoDBResult(document));
		}
	}

}
