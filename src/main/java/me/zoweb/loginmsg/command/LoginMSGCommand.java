package me.zoweb.loginmsg.command;

import me.zoweb.loginmsg.LoginMSG;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LoginMSGCommand implements CommandExecutor {
    public static final String prefix = "&8[&6&LoginMSG&8] &f";
    public static final String errorPrefix = prefix + "&4&lError! &c";
    public static final String noPermissionsMessage = errorPrefix + "It seems like you aren't allowed to run this " +
            "command. If you think this is wrong, please send the administrators a message!";

    public static String generateHelp(String name, String[] options) {
        StringBuilder message = new StringBuilder(prefix);
        message.append("&").append(name).append("&f command\n");

        for (String current : options) {
            message.append(" - ").append(current).append("\n");
        }

        return ChatColor.translateAlternateColorCodes('&', message.substring(0, message.length() - 1));
    }

    private LoginMSG plugin;

    public LoginMSGCommand(LoginMSG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
