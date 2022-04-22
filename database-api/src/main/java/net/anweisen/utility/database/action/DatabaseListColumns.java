package net.anweisen.utility.database.action;

import net.anweisen.utility.database.action.hierarchy.TableAction;
import net.anweisen.utility.database.action.result.ExistingSqlColumn;
import net.anweisen.utility.database.exception.DatabaseException;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DatabaseListColumns extends DatabaseAction<List<ExistingSqlColumn>>, TableAction {

	@Nonnull
	@Override
	List<ExistingSqlColumn> execute() throws DatabaseException;

}
