package net.anweisen.utilities.commons.version;

import net.anweisen.utilities.commons.annotations.Since;
import net.anweisen.utilities.commons.logging.ILogger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class VersionInfo implements Version {

	protected static final ILogger logger = ILogger.forThisClass();

	private final int major, minor, revision;

	public VersionInfo() {
		this(1, 0, 0);
	}

	public VersionInfo(int major, int minor, int revision) {
		this.major = major;
		this.minor = minor;
		this.revision = revision;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getRevision() {
		return revision;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Version)) return false;
		return this.equals((Version) other);
	}

	@Override
	public int hashCode() {
		return Objects.hash(major, minor, revision);
	}

	@Override
	public String toString() {
		return this.format();
	}

	@Nonnull
	@CheckReturnValue
	public static Version parse(@Nullable String input) {
		try {
			String[] array = input.split("\\.");
			if (array.length == 0) throw new IllegalArgumentException("Version cannot be empty");
			int major = Integer.parseInt(array[0]);
			int minor = array.length >= 2 ? Integer.parseInt(array[1]) : 0;
			int revision = array.length >= 3 ? Integer.parseInt(array[2]) : 0;
			return new VersionInfo(major, minor, revision);
		} catch (Exception ex) {
			logger.error("Could not parse version for input '{}': {}", input, ex.getMessage());
			return new VersionInfo(1, 0, 0);
		}
	}

}
