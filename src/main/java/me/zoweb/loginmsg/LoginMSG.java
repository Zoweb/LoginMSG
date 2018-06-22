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

import java.io.*;
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

    private Configuration configDefaults = new YamlConfiguration();

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

        configDefaults.set("permission.reload", "op");
        configDefaults.set("permission.save", "op");

        try {
            // If the plugin folder doesn't exist, make it
            File dataFolder = getDataFolder();
            dataFolder.mkdirs();

            // Loop through listeners and load their data
            for (MessageDisplayer listener : MessageDisplayer.listeners) {
                File target = writeListenerConfig(listener);

                getLogger().info("Loading configuration for above file: " + target.getPath() + ".");
                listener.loadData(target);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        configDefaults.set("note", "Do not edit CACHE unless you know what you're doing!");

        writeConfigDefaults();
    }

    public void writeConfigDefaults() {
        FileConfiguration config = getConfig();
        configDefaults.getValues(true).forEach((key, value) -> {
            if (!config.isSet(key)) config.set(key, value);
        });
        saveConfig();
    }

    public File writeListenerConfig(MessageDisplayer listener) throws FileNotFoundException {
        configDefaults.set("permission." + listener.name + ".me", "all");
        configDefaults.set("permission." + listener.name + ".others", "op");
        configDefaults.set("permission." + listener.name + ".query.me", "all");
        configDefaults.set("permission." + listener.name + ".query.others", "op");

        File target = new File(getDataFolder(), listener.name + ".yml");

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

        return target;
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
