package net.anweisen.utility.common.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * Used to declare alternate names which are used in used or similar libraries.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface AlsoKnownAs {

	@Nonnull
	String[] value();

}
