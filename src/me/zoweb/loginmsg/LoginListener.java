package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.Bukkit.getServer;

/**
 * (c) zoweb
 */
public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        try {
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&',
                    (getServer().getPluginManager().getPlugin("LoginMSG").getConfig().getString("login-message") == null)
                            ? "&e&l%player%&e joined the game."
                            : getServer().getPluginManager().getPlugin("LoginMSG").getConfig().getString("login-message"))
                    .replace("%player%", e.getPlayer().getName()));
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        try {
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&',
                    (getServer().getPluginManager().getPlugin("LoginMSG").getConfig().getString("leave-message") == null)
                            ? "&e&l%player%&e left the game."
                            : getServer().getPluginManager().getPlugin("LoginMSG").getConfig().getString("leave-message"))
                    .replace("%player%", e.getPlayer().getName()));
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

}
