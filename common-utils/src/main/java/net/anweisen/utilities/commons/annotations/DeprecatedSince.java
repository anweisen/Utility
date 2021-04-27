package net.anweisen.utilities.commons.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 *
 * @see Deprecated
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE})
public @interface DeprecatedSince {

	@Nonnull
	String value();

}
