package me.zoweb.loginmsg;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import sun.misc.IOUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import static org.bukkit.Bukkit.getServer;

/**
 * (c) zoweb
 */
public class BaseCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6by Zoweb.\n" +
                    " - Reload: &e&l/lmsg r&6\n" +
                    " - Update: &e&l/lmsg u&6\n" +
                    " - Version: &e&l/lmsg v&6\n" +
                    " - Toggle: &e&l/lmsg t&6"));
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
        } else if (args[0].equalsIgnoreCase("toggle") || args[0].equalsIgnoreCase("t")) {
            if (sender.hasPermission("loginmsg.toggle")) {
                LoginMSG.playerEnabled.put(sender.getName(), !LoginMSG.playerEnabled.get(sender.getName()));
                if (LoginMSG.playerEnabled.get(sender.getName())) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6Enabled Login Messages"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6Disabled Login Messages"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &cYou do not have" +
                        "permission to run this command. If you think this is a server error, please tell an administrator."));
            }
        } else if (args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("u")) {
            if (sender.hasPermission("loginmsg.update")) {
                // Check for updates
                try {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6Checking for updates..."));
                    URL url = new URL("http://zoweb.me/products/loginmsg/VERSION");
                    URLConnection yc = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        String actualVersion = getServer().getPluginManager().getPlugin("LoginMSG").getDescription().getVersion();
                        if (actualVersion.equals(inputLine)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6You have the latest version of LoginMSG: &lv" + actualVersion));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6Updating from &lv" + actualVersion + "&6 to &lv" + inputLine + "&6..."));

                            Plugin lmsg = getServer().getPluginManager().getPlugin("LoginMSG");

                            File file = new File("plugins/loginmsg_update.jar");
                            try (InputStream is = new URL("http://zoweb.me/products/loginmsg/updater.jar").openStream(); OutputStream os = new FileOutputStream(file)) {
                                int count;
                                final byte data[] = new byte[1024];
                                while ((count = is.read(data, 0, 1024)) != -1) {
                                    os.write(data, 0, count);
                                }

                                Files.setAttribute(file.toPath(), "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

                                Plugin LoginMSGUpdater = getServer().getPluginManager().loadPlugin(new File("plugins/loginmsg_update.jar"));
                                getServer().getPluginManager().enablePlugin(getServer().getPluginManager().getPlugin("LoginMSG-Updater"));

                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6Files successfully downloaded."));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &cYou do not have" +
                        "permission to run this command. If you think this is a server error, please tell an administrator."));
            }
        } else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v")) {

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &6LoginMSG by zoweb. v" + getServer().getPluginManager().getPlugin("LoginMSG").getDescription().getVersion()));

        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&f&lLoginMSG&7] &cThat command doesn't " +
                    "seem to exist."));
        }
        return true;
    }

}
