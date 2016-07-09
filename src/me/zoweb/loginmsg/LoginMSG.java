package me.zoweb.loginmsg;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;

/**
 * (c) zoweb
 */
public class LoginMSG extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginCommand("loginmsg").setExecutor(new BaseCommand());

        getLogger().info("Testing for data folder...");
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File file = new File(dataFolder, "config.yml");
            if (!file.exists()) {
                getLogger().info("Config file doesn't exist, creating...");
                file.createNewFile();
                FileOutputStream fileStream = new FileOutputStream(file, false);
                fileStream.write(("# Configuration for LoginMSG.\n" +
                        "# Use %player% for the player name.\n" +
                        "# Use &[0-F] for colors." +
                        "\n" +
                        "# Login message:\n" +
                        "login-message: '&e&l%player%&e joined the game.'\n" +
                        "\n" +
                        "# Logout message:\n" +
                        "logout-message: '&e&l%player%&e left the game.'\n").getBytes());
                fileStream.close();
            } else {
                getLogger().info("Loading config...");
            }
            getServer().getPluginManager().getPlugin("LoginMSG").reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        getLogger().info("LoginMSG disabled.");
    }

}
