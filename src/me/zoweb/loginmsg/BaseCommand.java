package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.bukkit.Bukkit.getServer;

/**
 * (c) zoweb
 */
public class BaseCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6by Zoweb.\n" +
                    " - Reload: &e&l/lmsg r"));
        } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
            if (sender.hasPermission("loginmsg.reload")) {
                try {
                    getServer().getPluginManager().getPlugin("LoginMSG").reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6Reload complete."));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &cYou do not have" +
                        "permission to run this command. If you think this is a server error, please tell an administrator."));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6That command doesn't" +
                    "seem to exist."));
        }
        return true;
    }

}
