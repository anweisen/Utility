package net.anweisen.utilities.commons.annotations;

import javax.annotation.Nonnull;

/**
 * Used to declare alternate names which are used in used or similar libraries.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public @interface AlsoKnownAs {

	@Nonnull
	String[] value();

}
