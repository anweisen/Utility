package net.anweisen.utilities.commons.common;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author org.apache.commons.io
 * @since 1.0
 */
public class IOUtils {

	private IOUtils() {
	}

	public static String toString(@Nonnull String url) throws IOException {
		return toString(new URL(url));
	}

	public static String toString(@Nonnull URL url) throws IOException {
		InputStream inputStream = url.openStream();
		String string;
		try {
			string = toString(inputStream);
		} finally {
			inputStream.close();
		}
		return string;
	}

	public static String toString(@Nonnull InputStream input) throws IOException {
		StringBuilderWriter writer = new StringBuilderWriter();
		copy(input, writer);
		return writer.toString();
	}

	public static void copy(@Nonnull InputStream input, @Nonnull Writer output) throws IOException {
		InputStreamReader in = new InputStreamReader(input, StandardCharsets.UTF_8);
		copy(in, output);
	}

	public static int copy(@Nonnull Reader input, @Nonnull Writer output) throws IOException {
		long count = copyLarge(input, output);
		return count > 2147483647L ? -1 : (int) count;
	}

	public static long copyLarge(@Nonnull Reader input, @Nonnull Writer output) throws IOException {
		return copyLarge(input, output, new char[4096]);
	}

	public static long copyLarge(@Nonnull Reader input, @Nonnull Writer output, @Nonnull char[] buffer) throws IOException {
		long count;
		int n;
		for(count = 0L; -1 != (n = input.read(buffer)); count += n) {
			output.write(buffer, 0, n);
		}
		return count;
	}

	public static HttpURLConnection createHttpConnection(@Nonnull String url) throws IOException {
		return (HttpURLConnection) new URL(url).openConnection();
	}

}
