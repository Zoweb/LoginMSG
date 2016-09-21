package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.bukkit.Bukkit.getServer;

/**
 * (c) zoweb
 */
public class BaseCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6by Zoweb.\n" +
                    " - Reload: &e&l/lmsg r&6\n" +
                    " - Update: &e&l/lmsg u"));
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
        } else if (args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("u")) {
            // Check for updates
            try {
                URL url = new URL("http://zoweb.me/products/loginmsg/VERSION");
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6That command doesn't" +
                    "seem to exist."));
        }
        return true;
    }

}
