package me.zoweb.loginmsg.command;

import me.zoweb.loginmsg.LoginMSG;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LoginMSGCommand implements CommandExecutor {
    public static final String prefix = "&8[&6&LoginMSG&8] &f";
    public static final String errorPrefix = prefix + "&4&lError! &c";
    public static final String noPermissionsMessage = errorPrefix + "It seems like you aren't allowed to run this " +
            "command. If you think this is wrong, please send the administrators a message!";

    public static String generateHelp(String name, String[] options) {
        StringBuilder message = new StringBuilder(prefix);
        message.append("&o").append(name).append("&f command\n");

        for (String current : options) {
            message.append(" - ").append(current).append("\n&f");
        }

        return ChatColor.translateAlternateColorCodes('&', message.substring(0, message.length() - 3));
    }

    private LoginMSG plugin;

    private void help(CommandSender sender) {
        ArrayList<String> options = new ArrayList<>();

        boolean isPlayer = sender instanceof Player;

        options.add("/lmsg reload");
        options.add("/lmsg <enable|disable> " + (isPlayer ? "[playername]" : "<playername>"));
        options.add("/lmsg <enable|disable> <login|logout|death> " + (isPlayer ? "[playername]" : "<playername>"));

        sender.sendMessage(generateHelp("LoginMSG", options.toArray(new String[]{})));
    }

    public LoginMSGCommand(LoginMSG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) help(sender);

        return true;
    }
}
