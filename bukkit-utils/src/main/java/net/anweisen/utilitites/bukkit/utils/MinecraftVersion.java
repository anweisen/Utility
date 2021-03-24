package net.anweisen.utilitites.bukkit.utils;

import net.anweisen.utilities.commons.version.Version;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum MinecraftVersion implements Version {

	V1_0,       // 1.0
	V1_1,       // 1.1
	V1_2_1,     // 1.2.1
	V1_3_1,     // 1.3.1
	V1_4_2,     // 1.4.2
	V1_5,       // 1.5
	V1_6,       // 1.6
	V1_7,       // 1.7
	V1_7_2,     // 1.7.2
	V1_8,       // 1.8
	V1_9,       // 1.9
	V1_10,      // 1.10
	V1_11,      // 1.11
	V1_12,      // 1.12
	V1_13,      // 1.13
	V1_14,      // 1.14
	V1_15,      // 1.15
	V1_16,      // 1.16
	V1_17;      // 1.17

	private final int major, minor, revision;

	MinecraftVersion() {

		String name = this.name().substring(1);
		String[] version = name.split("_");

		if (version.length != 2 && version.length != 3)
			throw new IllegalArgumentException("Name '" + name() + "' does not match pattern: V{major}_{minor}_[revision]");

		major    = Integer.parseInt(version[0]);
		minor    = Integer.parseInt(version[1]);
		revision = version.length > 2 ? Integer.parseInt(version[2]) : 0;

	}

	@Override
	public int getMajor() {
		return major;
	}

	@Override
	public int getMinor() {
		return minor;
	}

	@Override
	public int getRevision() {
		return revision;
	}

	@Override
	public String toString() {
		return this.format();
	}

}
