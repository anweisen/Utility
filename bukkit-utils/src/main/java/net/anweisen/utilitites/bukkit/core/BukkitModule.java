package net.anweisen.utilitites.bukkit.core;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.JavaILogger;
import net.anweisen.utilities.commons.logging.LogLevel;
import net.anweisen.utilities.commons.logging.internal.JavaLoggerWrapperFactory;
import net.anweisen.utilities.commons.version.Version;
import net.anweisen.utilities.commons.version.VersionInfo;
import net.anweisen.utilitites.bukkit.utils.MinecraftVersion;
import net.anweisen.utilitites.commons.config.document.readonly.ReadOnlyYamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.logging.Level;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class BukkitModule extends JavaPlugin {

	private final Map<String, CommandExecutor> commands = new HashMap<>();
	private final List<Listener> listeners = new ArrayList<>();

	private Document config;
	private Version version;
	private MinecraftVersion serverVersion;
	private boolean devMode;
	private boolean firstInstall;
	private boolean isReload;
	private boolean wasShutdown;

	@Override
	public void onLoad() {
		ILogger.setFactory(new JavaLoggerWrapperFactory(super.getLogger()));
		version = VersionInfo.parse(getDescription().getVersion());
		serverVersion = VersionInfo.findNearest(VersionInfo.parseFromCraftBukkit(getServer().getClass()), MinecraftVersion.values());
		getLogger().info("Detected server version {}", serverVersion);
		if (wasShutdown) isReload = true;
		if (firstInstall = !getDataFolder().exists()) {
			getLogger().info("Detected first install!");
		}
		if (devMode = getConfigDocument().getBoolean("dev-mode")) {
			getLogger().setLevel(Level.ALL);
			getLogger().log(LogLevel.DEBUG, "Devmode is enabled: Showing debug messages. This can be disabled in the plugin.yml ('dev-mode')");
		} else {
			getLogger().setLevel(Level.INFO);
		}
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		commands.forEach((name, executor) -> registerCommand0(executor, name));
		listeners.forEach(this::registerListener);
	}

	@Override
	public void onDisable() {
		wasShutdown = true;
		commands.clear();
		listeners.clear();

		for (Player player : Bukkit.getOnlinePlayers()) {
			player.getOpenInventory().close();
		}
	}

	public boolean isDevMode() {
		return devMode;
	}

	public boolean isFirstInstall() {
		return firstInstall;
	}

	public boolean isReload() {
		return isReload;
	}

	@Nonnull
	@Override
	public JavaILogger getLogger() {
		return ILogger.forJavaLogger(super.getLogger());
	}

	@Nonnull
	public Document getConfigDocument() {
		return config != null ? config : (config = new ReadOnlyYamlDocument(super.getConfig()));
	}

	@Nonnull
	public Version getVersion() {
		return version;
	}

	@Nonnull
	public MinecraftVersion getServerVersion() {
		return serverVersion;
	}

	@Nonnull
	@Override
	@Deprecated
	public FileConfiguration getConfig() {
		return super.getConfig();
	}

	@Override
	@Deprecated
	public void saveConfig() {
		super.saveConfig();
	}

	public final void registerCommand(@Nonnull CommandExecutor executor, @Nonnull String... names) {
		for (String name : names) {
			if (isEnabled()) {
				registerCommand0(executor, name);
			} else {
				commands.put(name, executor);
			}
		}
	}

	private void registerCommand0(@Nonnull CommandExecutor executor, @Nonnull String name) {
		PluginCommand command = getCommand(name);
		if (command == null) {
			getLogger().warning("Tried to register invalid command '" + name + "'");
		} else {
			command.setExecutor(executor);
		}
	}

	public final void registerListener(@Nonnull Listener... listeners) {
		if (isEnabled()) {
			for (Listener listener : listeners) {
				getServer().getPluginManager().registerEvents(listener, this);
			}
		} else {
			this.listeners.addAll(Arrays.asList(listeners));
		}
	}

	public final void disable() {
		getServer().getPluginManager().disablePlugin(this);
	}

	public void runAsync(@Nonnull Runnable task) {
		Thread thread = new Thread(task);
		thread.setName("AsyncPluginTask");
		thread.start();
	}

}