package net.anweisen.utilities.jda.manager.examples;

import net.anweisen.utilities.database.SQLColumn;
import net.anweisen.utilities.database.SQLColumn.Type;
import net.anweisen.utilities.jda.manager.bot.DiscordBot;
import net.anweisen.utilities.jda.manager.bot.DiscordBotBuilder;
import net.anweisen.utilities.jda.manager.defaults.commands.DefaultSetPrefixCommand;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DiscordBotExample extends DiscordBot {

    public DiscordBotExample() throws Exception {
        // This line is redundant and can be left out, we just left it in for clarity
        // The config is loaded in the super constructor and will throw an IOException if something goes wrong
        // If the config file (config.json) does not exist, a new one is created with default values (BotConfigCreator.DEFAULT_VALUES) and a FileNotFoundException is thrown
        // In most cases this will exit the application and gives the user time to setup the config and restart the application then
        super();

        // If you are instantiating final variables, you can do that before calling init()
        // Calling init() will create the database connection if required and start the bot
        init();
    }

    @Override
    protected void onStart() throws Exception {
        // Called after bot is built
    }

    @Override
    protected void onReady() throws Exception {
        // Called when all shards are ready
    }

    @Nonnull
    @Override
    protected DiscordBotBuilder builder() {
        return newBuilder() // Shortcut for new DiscordBotBuilder()
                .intents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MEMBERS) // GUILD_MESSAGES is required for guild commands
                                                                                                                   // DIRECT_MESSAGES            for private commands
                                                                                                                   // GUILD_MEMBERS              for MemberCachePolicy.ALL, see below
                                                                                                                   //                            (requires members intent)
                .withCache(CacheFlag.ROLE_TAGS) // All other cache flags will be disabled and only the given are enabled
                .defaultDatabaseConfig() // Default table and column names will be used, if you want use custom table and column names use databaseConfig(...)
                                         // If no database config is set, no database managers (database team role management, database language management, ...) are setup
                .createTable("user", new SQLColumn("id", Type.VARCHAR, 18), new SQLColumn("xp", Type.INT, 100)) // Create your custom database tables
                .requireDatabase() // The database connection will be created even when no tables were given or database config was set
                .resourceLanguages("de.json", "en.json") // Read hardcoded languages from resources (still better than hardcoding them directly into the code)
                                                         // You should use language files which are set in the config and can be edited without rebuilding this project
                .fileLanguages("de.json", "en.json") // Read languages from files
                                                     // You should consider setting them in the config, this can be helpful for dynamic editing
                .activity(                      "{0} Server",   (Supplier<Object>) () -> getShardManager().getGuilds().size()) // Default activity type playing will be used
                .activity(Activity::competing,  "{0} User",     (Supplier<Object>) () -> getShardManager().getUsers().size()) // Use a Supplier and not a direct value
                .updateActivity(15) // Activity will be updated every 15 seconds, if multiple activities were given it will cycle through them
                .useEmbedsInCommands(false) // If this is set to true, all replies from commands will be sent as embeds with the color of the bot member in that guild
                .commands(new CommandExample(), new DefaultSetPrefixCommand(15)) // Register your commands
                .slashcommands(new CommandData("setprefix", "Changes the prefix").addOption(OptionType.STRING, "prefix", "The new prefix", true)) // Add the slashcommands yourself
                .listeners(this) // Register your listeners, a CommandListener will automatically be registered
                .memberCachePolicy(MemberCachePolicy.ALL) // All members will be hold in cache
                                                          // If you dont cache all members, commands which require members as arguments will have to send a request to discord everytime, which takes some time
		;
	}

}
