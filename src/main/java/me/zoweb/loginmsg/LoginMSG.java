package me.zoweb.loginmsg;

import me.zoweb.loginmsg.command.LoginMSGCommand;
import me.zoweb.loginmsg.listener.PlayerDeathListener;
import me.zoweb.loginmsg.listener.PlayerJoinListener;
import me.zoweb.loginmsg.listener.PlayerQuitListener;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class, gets instantiated by Spigot
 */

public class LoginMSG extends JavaPlugin {
    public static LoginMSG instance;

    public LoginMSG() {
        super();
        instance = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("Welcome!");

        try {
            // Add a listener for login
            registerEvents(new PlayerJoinListener("login", event -> event.setJoinMessage("")));
            // Add a listener for logout
            registerEvents(new PlayerQuitListener("logout", event -> event.setQuitMessage("")));
            // Add a listener for death. Todo: make work, has NoClassDefFoundError
            //registerEvents(new PlayerDeathListener("death", event -> {}));
        } catch (Exception err) {
            getLogger().severe("An error occurred during initalisation:");
            err.printStackTrace();
            return;
        }

        // Listen for commands
        getLogger().info("Initialising commands");
        getCommand("loginmsg").setExecutor(new LoginMSGCommand(this));

        // Configuration
        getLogger().info("Setting up configuration");
        Configuration defaults = new YamlConfiguration();

        defaults.set("permission.reload", "op");
        defaults.set("permission.save", "op");

        try {
            // If the plugin folder doesn't exist, make it
            File dataFolder = getDataFolder();
            dataFolder.mkdirs();

            // Loop through listeners and load their data
            for (MessageDisplayer listener : MessageDisplayer.listeners) {
                // Create a file for listener if it doesn't already exist
                File target = new File(dataFolder, listener.name + ".yml");

                // Add permission option
                defaults.set("permission." + listener.name + ".me", "all");
                defaults.set("permission." + listener.name + ".others", "op");
                defaults.set("permission." + listener.name + ".query.me", "all");
                defaults.set("permission." + listener.name + ".query.others", "op");

                if (!target.exists()) {
                    getLogger().info("Creating listener config file: " + listener.name);
                    InputStream stream = getResource("template.yml");
                    Scanner scanner = new Scanner(stream).useDelimiter("\\A");
                    PrintWriter writer = new PrintWriter(target);
                    String contents = scanner.hasNext() ? scanner.next() : "";
                    contents = contents.replace("<event>", listener.name);
                    writer.write(contents);
                    writer.close();
                    scanner.close();
                }

                getLogger().info("Loading configuration for above file: " + target.getPath() + ".");
                listener.loadData(target);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        defaults.set("note", "Do not edit CACHE unless you know what you're doing!");

        FileConfiguration config = getConfig();
        defaults.getValues(true).forEach((key, value) -> {
            if (!config.isSet(key)) config.set(key, value);
        });
        saveConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving changes");
        saveConfig();
    }

    public static void registerEvents(Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    private boolean createDataFile(String resourcePath, String dataFileName) {
        File target = new File(getDataFolder(), dataFileName + ".yml");
        return false;
    }
}
