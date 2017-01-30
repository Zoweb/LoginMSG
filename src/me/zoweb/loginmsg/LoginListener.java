/*package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.Bukkit.getServer;

/*
 * (c) zoweb


public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        // PEX support. This unfortunately does not work
        /*if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
            try {
                Class.forName("ru.tehkode.permissions.bukkit.PermissionsEx");
            } catch (Exception er) {
                er.printStackTrace();
            }
        }

        e.setJoinMessage("");
        String message = "";
        Sound sound;

        try {
            LoginMSG.playerEnabled.put(e.getPlayer().getName(), true);

            Configuration config = getServer().getPluginManager().getPlugin("LoginMSG").getConfig();

            ConfigurationSection section = config.getConfigurationSection("custom-messages");
            // Get player's permission-specific messages
            boolean usedCustomMessage = false,
                    usedCustomSound = false;
            boolean playSounds = config.getConfigurationSection("use-sounds").getBoolean("join");
            for (String key : section.getKeys(false)) {
                if (e.getPlayer().hasPermission(key.replace(' ', '.')) || (key.equals("server op") && e.getPlayer().isOp()) || key.equalsIgnoreCase("player " + e.getPlayer().getName())) {
                    if (section.getConfigurationSection(key).getString("login-message") != null) {
                        message = ChatColor.translateAlternateColorCodes('&', section.getConfigurationSection(key).getString("login-message")).replace("%player%", e.getPlayer().getName());

                        usedCustomMessage = true;
                        break;
                    }
                }/*
                PEX support. Not working.
                else if (key.indexOf("group ") == 0 && getServer().getPluginManager().getPlugin("PermissionsEx") != null && getServer().getPluginManager().getPlugin("PermissionsEx").getClass().getUser(e.getPlayer().getName()).inGroup(key.substring(6))) {
                    if (section.getConfigurationSection(key).getString("login-message") != null) {
                        message = ChatColor.translateAlternateColorCodes('&', section.getConfigurationSection(key).getString("login-message")).replace("%player%", e.getPlayer().getName());

                        doneCustom = true;
                        break;
                    }
                }
            }

            if (playSounds) {
                ConfigurationSection soundSection = config.getConfigurationSection("custom-sounds");
                for (String key : soundSection.getKeys(false)) {

                }

                if (!usedCustomSound) {
                    try {
                        sound = Sound.valueOf(config.getString("login-sound"));
                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }
            }

            if (!usedCustomMessage) {
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
            if (e.getPlayer().hasPermission(key.replace(' ', '.')) || (key.equals("server op") && e.getPlayer().isOp()) || key.equalsIgnoreCase("player " + e.getPlayer().getName())) {
                if (section.getConfigurationSection(key).getString("logout-message") != null) {
                    message = ChatColor.translateAlternateColorCodes('&', section.getConfigurationSection(key).getString("logout-message")).replace("%player%", e.getPlayer().getName());

                    doneCustom = true;
                    break;
                }
            }/*
            PEX support. Not working.
            else if (key.indexOf("group ") == 0 && getServer().getPluginManager().getPlugin("PermissionsEx") != null && PermissionsEx.getUser(e.getPlayer().getName()).inGroup(key.substring(6))) {
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
*/