package me.zoweb.loginmsg;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * (c) zoweb
 */
public class LoginMSG extends JavaPlugin {

    //public static HashMap<String, Boolean> playerEnabled = new HashMap<String, Boolean>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new LoginEvent(), this);
        getServer().getPluginManager().registerEvents(new LogoutEvent(), this);

        getServer().getPluginCommand("loginmsg").setExecutor(new BaseCommand());
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists()) {
                if (dataFolder.mkdirs()) {
                    getLogger().info("Created data directory");
                } else {
                    getLogger().severe("Could not create data directory! Error listed below.");
                }
            }
            File configYmlFile = new File(dataFolder, "config.yml");
            File playerSettingsYmlFile = new File(dataFolder, "player-settings.yml");

            if (!configYmlFile.exists() && configYmlFile.createNewFile()) {
                getLogger().info("Created config file. Populating...");
                FileCreationHelpers.resourceToDataFile(this, "resources/config.yml", "config.yml");
                reloadConfig();
            }
            if (!playerSettingsYmlFile.exists() && playerSettingsYmlFile.createNewFile()) {
                getLogger().info("Created player settings file. Populating...");
                FileCreationHelpers.resourceToDataFile(this, "resources/player-settings.yml", "player-settings.yml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.getConfig().getBoolean("mc-stats")) {
            try {
                Metrics metrics = new Metrics(this);

                metrics.start();

                this.getLogger().log(Level.INFO, "Metrics started: http://mcstats.org/plugin/LoginMSG");
            } catch (IOException e) {
                this.getLogger().log(Level.WARNING, "Failed to submit MCStats.");
            }
        } else {
            this.getLogger().log(Level.WARNING, "MCStats is not enabled!");
        }

    }

    @Override
    public void onDisable() {
        getLogger().info("LoginMSG disabled.");
    }

}
