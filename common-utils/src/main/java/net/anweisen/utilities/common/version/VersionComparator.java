package net.anweisen.utilities.common.version;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class VersionComparator implements Comparator<Version> {

	@Override
	public int compare(@Nonnull Version v1, @Nonnull Version v2) {
		return v1.equals(v2) ? 0 : v1.isNewerThan(v2) ? 1 : -1;
	}

}
