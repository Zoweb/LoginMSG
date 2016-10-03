package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * (c) zoweb
 */
public class LoginMSG extends JavaPlugin {

    public static HashMap<String, Boolean> playerEnabled = new HashMap<String, Boolean>();

    @Override
    public void onEnable() {

        LoginMSG.playerEnabled.put("blob", true);
        getServer().getLogger().log(Level.INFO, LoginMSG.playerEnabled.get("blob").toString());

        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginCommand("loginmsg").setExecutor(new BaseCommand());
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File file = new File(dataFolder, "config.yml");
            if (!file.exists()) {
                //file.createNewFile();

                /*FileOutputStream writer = new FileOutputStream(new File(getDataFolder(), "config.yml"));
                InputStream out = this.getClass().getResourceAsStream("/src/config.yml");
                byte[] linebuffer = new byte[4096];
                int lineLength = 0;
                while ((lineLength = out.read(linebuffer)) > 0) {
                    writer.write(linebuffer, 0, lineLength);
                }
                writer.close();
                out.close();*/
                BufferedReader reader = null;
                try {
                    InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml");
                    reader = new BufferedReader(new InputStreamReader(in));

                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(getDataFolder(), "config.yml")));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line + "\r\n");
                    }
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reader.close();
                }
            }
            getServer().getPluginManager().getPlugin("LoginMSG").reloadConfig();
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
