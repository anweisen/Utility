package net.anweisen.utilities.bukkit.core;

import net.anweisen.utilities.bukkit.utils.wrapper.SimpleEventExecutor;
import net.anweisen.utilities.bukkit.utils.wrapper.ActionListener;
import net.anweisen.utilities.commons.annotations.ReplaceWith;
import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.JavaILogger;
import net.anweisen.utilities.commons.logging.LoggingExceptionHandler;
import net.anweisen.utilities.commons.logging.internal.BukkitLoggerWrapper;
import net.anweisen.utilities.commons.logging.internal.ConstantLoggerFactory;
import net.anweisen.utilities.commons.version.Version;
import net.anweisen.utilities.commons.version.VersionInfo;
import net.anweisen.utilities.bukkit.utils.misc.MinecraftVersion;
import net.anweisen.utilities.commons.config.document.YamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class BukkitModule extends JavaPlugin {

	private final Map<String, CommandExecutor> commands = new HashMap<>();
	private final List<Listener> listeners = new ArrayList<>();

	private JavaILogger logger;
	private Document config;
	private Version version;
	private MinecraftVersion serverVersion;
	private boolean devMode;
	private boolean firstInstall;
	private boolean isReload;
	private boolean wasShutdown;

	@Override
	public void onLoad() {
		ILogger.setFactory(new ConstantLoggerFactory(this.getLogger()));
		version = VersionInfo.parse(getDescription().getVersion());
		serverVersion = VersionInfo.findNearest(VersionInfo.parseFromCraftBukkit(getServer().getClass()), MinecraftVersion.values());
		getLogger().info("Detected server version {}", serverVersion);
		if (wasShutdown) isReload = true;
		if (firstInstall = !getDataFolder().exists()) {
			getLogger().info("Detected first install!");
		}
		if (devMode = getConfigDocument().getBoolean("dev-mode")) {
			getLogger().setLevel(Level.ALL);
			getLogger().debug("Devmode is enabled: Showing debug messages. This can be disabled in the plugin.yml ('dev-mode')");
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
		return logger != null ? logger : (logger = new BukkitLoggerWrapper(super.getLogger()));
	}

	@Nonnull
	public Document getConfigDocument() {
		return config != null ? config : (config = new YamlDocument(super.getConfig()).readonly());
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
	@ReplaceWith("getConfigDocument()")
	public FileConfiguration getConfig() {
		return super.getConfig();
	}

	@Override
	@Deprecated
	public void saveConfig() {
		super.saveConfig();
	}

	public final void registerListenerCommand(@Nonnull Object listenerAndExecutor, @Nonnull String... names) {
		if (!(listenerAndExecutor instanceof Listener && listenerAndExecutor instanceof CommandExecutor)) throw new IllegalArgumentException("listenerAndExecutor is not an instance of Listener or CommandExecutor");
		registerCommand((CommandExecutor) listenerAndExecutor, names);
		registerListener((Listener) listenerAndExecutor);
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
			getLogger().warn("Tried to register invalid command '{}'", name);
		} else {
			command.setExecutor(executor);
		}
	}

	public final void registerListener(@Nonnull Listener... listeners) {
		if (isEnabled()) {
			for (Listener listener : listeners) {
				registerListener0(listener);
			}
		} else {
			this.listeners.addAll(Arrays.asList(listeners));
		}
	}

	private void registerListener0(@Nonnull Listener listener) {
		if (listener instanceof ActionListener) {
			ActionListener<?> actionListener = (ActionListener<?>) listener;
			getServer().getPluginManager().registerEvent(
					actionListener.getClassOfEvent(), actionListener, actionListener.getPriority(),
					new SimpleEventExecutor(actionListener.getClassOfEvent(), actionListener.getListener()), this, actionListener.isIgnoreCancelled()
			);
		} else {
			getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	public final <E extends Event> void onEvent(@Nonnull Class<E> classOfEvent, @Nonnull Consumer<? super E> action) {
		onEvent(classOfEvent, EventPriority.NORMAL, action);
	}

	public final <E extends Event> void onEvent(@Nonnull Class<E> classOfEvent, @Nonnull EventPriority priority, @Nonnull Consumer<? super E> action) {
		onEvent(classOfEvent, priority, false, action);
	}

	public final <E extends Event> void onEvent(@Nonnull Class<E> classOfEvent, @Nonnull EventPriority priority, boolean ignoreCancelled, @Nonnull Consumer<? super E> action) {
		registerListener(new ActionListener<>(classOfEvent, action, priority, ignoreCancelled));
	}

	public final void disable() {
		getServer().getPluginManager().disablePlugin(this);
	}

	@Nonnull
	public final File getDataFile(@Nonnull String filename) {
		return new File(getDataFolder(), filename);
	}

	@Nonnull
	public final File getDataFile(@Nonnull String subfolder, @Nonnull String filename) {
		return new File(getDataFile(subfolder), filename);
	}

	public void runAsync(@Nonnull Runnable task) {
		Thread thread = new Thread(task);
		thread.setName(getName() + "-AsyncPluginTask-" + thread.getId());
		thread.setUncaughtExceptionHandler(new LoggingExceptionHandler(this.getLogger()));
		thread.start();
	}

}
