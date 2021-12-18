package net.anweisen.utility.common.collection;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ExposedSecurityManager extends SecurityManager {

	@Nonnull
	public Class<?>[] getPublicClassContext() {
		return getClassContext();
	}

}
