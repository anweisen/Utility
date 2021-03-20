package net.anweisen.utilities.commons.config;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@FunctionalInterface
public interface Json {

	@Nonnull
	String toJson();

}