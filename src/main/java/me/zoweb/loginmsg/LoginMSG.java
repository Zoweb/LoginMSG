package me.zoweb.loginmsg;

import me.zoweb.loginmsg.command.LoginMSGCommand;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
            MessageDisplayer.<PlayerJoinEvent>listen("login", event -> event.setJoinMessage(""));
            // Add a listener for logout
            MessageDisplayer.<PlayerQuitEvent>listen("logout", event -> event.setQuitMessage(""));
            // Add a listener for death. causes error for unknown reason
            //CastedPlayerMessageDisplayer.<PlayerDeathEvent>listenCasted("death", event -> event.setDeathMessage(""));
        } catch (Exception err) {
            getLogger().severe("An error occurred during initalisation:");
            err.printStackTrace();
            return;
        }

        // Listen for commands
        getCommand("loginmsg").setExecutor(new LoginMSGCommand(this));

        try {
            // If the plugin folder doesn't exist, make it
            File dataFolder = getDataFolder();
            dataFolder.mkdirs();

            // Loop through listeners and load their data
            for (MessageDisplayer listener : MessageDisplayer.listeners) {
                // Create a file for listener if it doesn't already exist
                File target = new File(dataFolder, listener.name + ".yml");

                if (!target.exists()) {
                    getLogger().info("Creating listener config file: " + listener.name);
                    InputStream stream = getResource("template.yml");
                    Files.copy(stream, target.toPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerEvents(Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    private boolean createDataFile(String resourcePath, String dataFileName) {
        File target = new File(getDataFolder(), dataFileName + ".yml");
        return false;
    }
}
