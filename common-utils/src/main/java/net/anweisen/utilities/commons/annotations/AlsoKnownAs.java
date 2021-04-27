package net.anweisen.utilities.commons.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;

/**
 * Used to declare alternate names which are used in used or similar libraries.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@Documented
public @interface AlsoKnownAs {

	@Nonnull
	String[] value();

}
