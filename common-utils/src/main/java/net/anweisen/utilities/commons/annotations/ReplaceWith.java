package net.anweisen.utilities.commons.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Deprecated
 */
@Retention(RetentionPolicy.CLASS)
public @interface ReplaceWith {

	@Nonnull
	String value();

}
