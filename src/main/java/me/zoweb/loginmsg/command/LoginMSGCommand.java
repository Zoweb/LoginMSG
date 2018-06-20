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

    private boolean help(CommandSender sender) {
        ArrayList<String> options = new ArrayList<>();

        boolean isPlayer = sender instanceof Player;

        options.add("/lmsg reload");
        options.add("/lmsg <enable|disable> " + (isPlayer ? "[playername]" : "<playername>"));
        options.add("/lmsg <enable|disable> <login|logout|death> " + (isPlayer ? "[playername]" : "<playername>"));
        options.add("/lmsg query <playername>");

        sender.sendMessage(generateHelp("LoginMSG", options.toArray(new String[]{})));

        return true;
    }

    private boolean checkPermission(CommandSender sender, String permission) {
        if (permission.equals("op")) return sender.isOp();
        if (permission.equals("all")) return true;
        return sender.hasPermission(permission);
    }

    private String colour(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private boolean sendNoPermission(CommandSender sender) {
        sender.sendMessage(noPermissionsMessage);
        return true;
    }

    private boolean runReload(CommandSender sender, String[] args) {
        if (!checkPermission(sender, plugin.getConfig().getString("permission.reload"))) return sendNoPermission(sender);

        sender.sendMessage(colour(prefix + "Reloading LoginMSG..."));
        plugin.reloadConfig();
        sender.sendMessage(colour(prefix + "Done"));

        return true;
    }

    public LoginMSGCommand(LoginMSG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return help(sender);

        switch (args[0]) {
            case "reload": runReload(sender, args); break;
            case "enable": runEnable(sender, args); break;
            case "disable": runDisable(sender, args); break;
            case "query": runQuery(sender, args); break;
            default: help(sender);
        }

        return true;
    }
}
