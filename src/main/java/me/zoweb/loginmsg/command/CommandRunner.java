package me.zoweb.loginmsg.command;

import org.bukkit.ChatColor;

public class CommandRunner {
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
}
