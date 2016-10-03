package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.net.URL;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

/**
 * (c) zoweb
 */
public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage("");
        String message = "";

        try {
            LoginMSG.playerEnabled.put(e.getPlayer().getName(), true);

            Configuration config = getServer().getPluginManager().getPlugin("LoginMSG").getConfig();

            ConfigurationSection section = config.getConfigurationSection("custom-messages");
            // Get player's permission-specific messages
            boolean doneCustom = false;
            for (String key : section.getKeys(false)) {
                getServer().getLogger().log(Level.INFO, key);
                if (e.getPlayer().hasPermission(key.replace(' ', '.')) || (key.equals("server op") && e.getPlayer().isOp()) || key.equalsIgnoreCase("player " + e.getPlayer().getName())) {
                    getServer().getLogger().log(Level.INFO, key.replace(' ', '.') + " is true.");
                    if (section.getConfigurationSection(key).getString("login-message") != null) {

                        getServer().getLogger().log(Level.INFO, "Message = ", section.getConfigurationSection(key).getString("login-message"));

                        message = ChatColor.translateAlternateColorCodes('&', section.getConfigurationSection(key).getString("login-message")).replace("%player%", e.getPlayer().getName());

                        doneCustom = true;
                        break;
                    }
                }
            }

            if (!doneCustom) {
                try {
                    message = ChatColor.translateAlternateColorCodes('&',
                            (config.getString("login-message") == null)
                                    ? "&e&l%player%&e joined the game."
                                    : config.getString("login-message"))
                            .replace("%player%", e.getPlayer().getName());
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

            for (Player player : getServer().getOnlinePlayers()) {
                getServer().getLogger().log(Level.INFO, LoginMSG.playerEnabled.toString());
                if (LoginMSG.playerEnabled.get(player.getName())) {
                    player.sendMessage(message);
                }
            }
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
        String message = "";
        Configuration config = getServer().getPluginManager().getPlugin("LoginMSG").getConfig();

        ConfigurationSection section = config.getConfigurationSection("custom-messages");
        // Get player's permission-specific messages
        boolean doneCustom = false;
        for (String key : section.getKeys(false)) {
            if (e.getPlayer().hasPermission(key) || (key.equals("server.op") && e.getPlayer().isOp()) || key.equalsIgnoreCase("player." + e.getPlayer().getName())) {
                if (section.getConfigurationSection(key).getString("logout-message") != null) {
                    message = ChatColor.translateAlternateColorCodes('&', section.getConfigurationSection(key).getString("logout-message")).replace("%player%", e.getPlayer().getName());

                    doneCustom = true;
                    break;
                }
            }
        }

        if (!doneCustom) {
            try {
                message = ChatColor.translateAlternateColorCodes('&',
                        (config.getString("logout-message") == null)
                                ? "&e&l%player%&e joined the game."
                                : config.getString("logout-message"))
                        .replace("%player%", e.getPlayer().getName());
            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        for (Player player : getServer().getOnlinePlayers()) {
            if (LoginMSG.playerEnabled.get(player.getName())) {
                player.sendMessage(message);
            }
        }
    }

}
