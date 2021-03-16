package net.anweisen.utilities.commons.misc;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class PublicSecurityManager extends SecurityManager {

	@Nonnull
	public Class<?>[] getPublicClassContext() {
		return getClassContext();
	}

}
