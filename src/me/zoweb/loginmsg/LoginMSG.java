package me.zoweb.loginmsg;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * (c) zoweb
 */
public class LoginMSG extends JavaPlugin {

    public static String getVersion() {
        return "1.0";
    }

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
                fileStream.write(Files.readAllBytes(Paths.get("config.yml")));
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
