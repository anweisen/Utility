package net.anweisen.utility.database;

import net.anweisen.utility.document.Document;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class DatabaseConfig {

	private final String host;
	private final String database;
	private final String authDatabase;
	private final String password;
	private final String user;
	private final String file;
	private final int port;
	private final boolean portIsSet;

	public DatabaseConfig(String host, String database, String password, String user, int port) {
		this(host, database, null, password, user, port, true, null);
	}
	public DatabaseConfig(String host, String database, String password, String user) {
		this(host, database, null, password, user, 0, false, null);
	}

	public DatabaseConfig(String host, String database, String authDatabase, String password, String user, int port) {
		this(host, database, authDatabase, password, user, port, true, null);
	}
	public DatabaseConfig(String host, String database, String authDatabase, String password, String user) {
		this(host, database, authDatabase, password, user, 0, false, null);
	}

	public DatabaseConfig(String database, String file) {
		this(null, database, null, null, null, 0, false, file);
	}

	public DatabaseConfig(String host, String database, String authDatabase, String password, String user, int port, boolean portIsSet, String file) {
		this.host = host;
		this.database = database;
		this.authDatabase = authDatabase;
		this.password = password;
		this.user = user;
		this.port = port;
		this.portIsSet = portIsSet;
		this.file = file;
	}

	public DatabaseConfig(@Nonnull Document document) {
		this(
			document.getString("host"),
			document.getString("database"),
			document.getString("auth-database"),
			document.getString("password"),
			document.getString("user"),
			document.getInt("port"),
			document.contains("port"),
			document.getString("file")
		);
	}

	public int getPort() {
		return port;
	}

	public String getAuthDatabase() {
		return authDatabase;
	}

	public String getDatabase() {
		return database;
	}

	public String getHost() {
		return host;
	}

	public String getPassword() {
		return password;
	}

	public String getUser() {
		return user;
	}

	public boolean isPortSet() {
		return portIsSet;
	}

	public String getFile() {
		return file;
	}

	@Override
	public String toString() {
		return "DatabaseConfig{" +
				"host='" + host + '\'' +
				", database='" + database + '\'' +
				", authDatabase='" + authDatabase + '\'' +
				", user='" + user + '\'' +
				", file='" + file + '\'' +
				", port=" + port +
				", portIsSet=" + portIsSet +
				'}';
	}

}
