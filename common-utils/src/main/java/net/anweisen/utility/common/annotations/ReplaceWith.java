package net.anweisen.utility.common.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @see Deprecated
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE})
public @interface ReplaceWith {

	@Nonnull
	String value();

}
