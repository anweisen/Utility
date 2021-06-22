package net.anweisen.utilities.common.config.exceptions;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ConfigReadOnlyException extends UnsupportedOperationException {

	public ConfigReadOnlyException(@Nonnull String action) {
		super("Config." + action);
	}

}
