package net.anweisen.utility.common.logging.handler;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface LogHandler {

	DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

	void handle(@Nonnull LogEntry entry) throws Exception;

}
