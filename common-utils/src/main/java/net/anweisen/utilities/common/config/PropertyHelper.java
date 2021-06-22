package net.anweisen.utilities.common.config;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.2.5
 */
public final class PropertyHelper {

	private PropertyHelper() {}

	@Nullable
	public static Date parseDate(@Nullable String string) {
		try {
			return DateFormat.getDateTimeInstance().parse(string);
		} catch (Exception ex) {
			return null;
		}
	}

}
