package net.anweisen.utilities.common.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Since {

	@Nonnull
	String value();

}
