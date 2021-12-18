package net.anweisen.utility.common.collection;

import javax.annotation.Nonnull;
import java.io.PrintWriter;

/**
 * @author org.apache.commons.io
 * @since 1.0
 */
public class StringBuilderPrintWriter extends PrintWriter {

	protected final StringBuilderWriter writer;

	public StringBuilderPrintWriter() {
		super(new StringBuilderWriter());
		writer = (StringBuilderWriter) out;
	}

	@Nonnull
	public StringBuilder getBuilder() {
		return writer.getBuilder();
	}

	@Override
	public String toString() {
		return getBuilder().toString();
	}

}
