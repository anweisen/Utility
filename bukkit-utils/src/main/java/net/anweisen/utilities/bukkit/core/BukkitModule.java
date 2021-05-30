package net.anweisen.utilities.bukkit.core;

import net.anweisen.utilities.bukkit.utils.menu.MenuPosition;
import net.anweisen.utilities.bukkit.utils.menu.MenuPositionListener;
import net.anweisen.utilities.bukkit.utils.wrapper.SimpleEventExecutor;
import net.anweisen.utilities.bukkit.utils.wrapper.ActionListener;
import net.anweisen.utilities.commons.annotations.ReplaceWith;
import net.anweisen.utilities.commons.common.NamedThreadFactory;
import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.commons.config.FileDocument;
import net.anweisen.utilities.commons.logging.ILogger;
import net.anweisen.utilities.commons.logging.JavaILogger;
import net.anweisen.utilities.commons.logging.internal.BukkitLoggerWrapper;
import net.anweisen.utilities.commons.logging.internal.factory.ConstantLoggerFactory;
import net.anweisen.utilities.commons.version.Version;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public abstract class BukkitModule extends JavaPlugin {

	private static volatile BukkitModule firstInstance;
	private static boolean setFirstInstance = true;
	private static boolean wasShutdown;

	private final Map<String, CommandExecutor> commands = new HashMap<>();
	private final List<Listener> listeners = new ArrayList<>();
	private final SimpleConfigManager configManager = new SimpleConfigManager(this);

	private JavaILogger logger;
	private ExecutorService executorService;
	private Document config;
	private Version version;
	private MinecraftVersion serverVersion;
	private boolean devMode;
	private boolean firstInstall;
	private boolean isReloaded;
	private boolean isLoaded;

	@Override
	public void onLoad() {
		isLoaded = true;

		if (setFirstInstance || firstInstance == null) {
			setFirstInstance(this);
		}

		ILogger.setFactory(new ConstantLoggerFactory(this.getLogger()));
		getLogger().info("Detected server version {}", getServerVersion());
		trySaveDefaultConfig();
		executorService = Executors.newCachedThreadPool(new NamedThreadFactory(threadId -> String.format("%s-Task-%s", this.getName(), threadId)));
		if (wasShutdown) isReloaded = true;
		if (firstInstall = !getDataFolder().exists()) {
			getLogger().info("Detected first install!");
		}
		if (devMode = getConfigDocument().getBoolean("dev-mode") || getConfigDocument().getBoolean("dev-mode.enabled")) {
			getLogger().setLevel(Level.ALL);
			getLogger().debug("Devmode is enabled: Showing debug messages. This can be disabled in the plugin.yml ('dev-mode')");
		} else {
			getLogger().setLevel(Level.INFO);
		}
	}

	@Override
	public void onEnable() {
		commands.forEach((name, executor) -> registerCommand0(executor, name));
		listeners.forEach(this::registerListener);
	}

	@Override
	public void onDisable() {
		setFirstInstance = true;
		wasShutdown = true;
		isLoaded = false;
		commands.clear();
		listeners.clear();
		executorService.shutdown();

		for (Player player : Bukkit.getOnlinePlayers()) {
			InventoryView view = player.getOpenInventory();
			Inventory inventory = view.getTopInventory();
			if (inventory.getHolder() == MenuPosition.HOLDER)
				view.close();
		}
	}

	public boolean isDevMode() {
		return devMode;
	}

	public final boolean isFirstInstall() {
		return firstInstall;
	}

	public final boolean isReloaded() {
		return isReloaded;
	}

	public final boolean isLoaded() {
		return isLoaded;
	}

	public final boolean isFirstInstance() {
		return firstInstance == this;
	}

	@Nonnull
	@Override
	public JavaILogger getLogger() {
		return logger != null ? logger : (logger = new BukkitLoggerWrapper(super.getLogger()));
	}

	@Nonnull
	public Document getConfigDocument() {
		checkLoaded();
		return config != null ? config : (config = new YamlDocument(super.getConfig()));
	}

	@Nonnull
	public FileDocument getConfig(@Nonnull String filename) {
		return configManager.getDocument(filename);
	}

	@Nonnull
	public Version getVersion() {
		return version != null ? version : (version = Version.parse(getDescription().getVersion()));
	}

	@Nonnull
	public MinecraftVersion getServerVersion() {
		return serverVersion != null ? serverVersion : (serverVersion = MinecraftVersion.parse());
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
		if (!(listenerAndExecutor instanceof Listener && listenerAndExecutor instanceof CommandExecutor)) throw new IllegalArgumentException("listenerAndExecutor is not an instance of Listener and CommandExecutor");
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
		executorService.submit(task);
	}

	public final void checkLoaded() {
		if (!isLoaded())
			throw new IllegalStateException("Plugin is not loaded yet");
	}

	public final void checkEnabled() {
		if (!isEnabled())
			throw new IllegalStateException("Plugin is not enabled yet");
	}

	private void registerOnFirstInstance() {
		getLogger().info(getName() + " was loaded as the first BukkitModule");
		registerListener(
			new MenuPositionListener()
		);
	}

	private void trySaveDefaultConfig() {
		try {
			saveDefaultConfig();
		} catch (IllegalArgumentException ex) {
			// No default config exists
		}
	}

	@Nonnull
	public static BukkitModule getFirstInstance() {
		if (firstInstance == null) {
			JavaPlugin provider = JavaPlugin.getProvidingPlugin(BukkitModule.class);
			if (!(provider instanceof BukkitModule)) throw new IllegalStateException("No BukkitModule was initialized yet & BukkitModule class was not loaded by a BukkitModule");
			firstInstance = (BukkitModule) provider;
		}

		return firstInstance;
	}

	private static synchronized void setFirstInstance(@Nonnull BukkitModule module) {
		setFirstInstance = false;
		firstInstance = module;
		module.registerOnFirstInstance();
	}

}
