package me.zoweb.loginmsg.command;

import me.zoweb.loginmsg.LoginMSG;
import me.zoweb.loginmsg.MessageDisplayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * LoginMSG command executor
 */
public class LoginMSGCommand implements CommandExecutor {
    /**
     * LoginMSG prefix
     */
    public static final String prefix = "&8[&6&lLoginMSG&8] &f";

    /**
     * Error prefix
     */
    public static final String errorPrefix = prefix + "&4&lError! &c";

    /**
     * Message to display when user doesn't have permission to do something
     */
    public static final String noPermissionsMessage = errorPrefix + "It seems like you aren't allowed to run this " +
            "command. If you think this is wrong, please send the administrators a message!";

    /**
     * Generate a help message for a command
     * @param name The name of the command
     * @param options The different options the command has
     * @return Help message - must use colour(message) to generate colour codes
     */
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

        StringBuilder listeners = new StringBuilder();
        for (MessageDisplayer listener : MessageDisplayer.listeners) {
            listeners.append(listener.name + "|");
        }

        options.add("/lmsg reload");
        options.add("/lmsg <enable|disable|query> <all|" + listeners.substring(0, listeners.length() - 1) + "> " + (isPlayer ? "[playername]" : "<playername>"));

        sender.sendMessage(generateHelp("LoginMSG", options.toArray(new String[]{})));

        return true;
    }

    private boolean checkPermission(CommandSender sender, String permission) {
        if (permission == null) return false;
        if (permission.equals("console")) return sender instanceof ConsoleCommandSender;
        if (permission.equals("op")) return sender.isOp() || sender instanceof ConsoleCommandSender;
        if (permission.equals("all")) return true;
        return sender.hasPermission(permission);
    }

    private String colour(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private void sendNoPermission(CommandSender sender) {
        sender.sendMessage(noPermissionsMessage);
    }

    private void runReload(CommandSender sender, String[] args) {
        if (!checkPermission(sender, plugin.getConfig().getString("permission.reload"))) {
            sendNoPermission(sender);
            return;
        }

        sender.sendMessage(colour(prefix + "Reloading LoginMSG..."));
        plugin.reloadConfig();
        sender.sendMessage(colour(prefix + "Done"));
    }

    private void runSet(CommandSender sender, String[] args, boolean value) {
        if (args.length == 1) {
            runSet(sender, new String[]{args[0], "all"}, value);
            return;
        }
        if (args.length == 2) {
            if (sender instanceof Player) runSet(sender, new String[]{args[0], args[1], sender.getName()}, value);
            else sender.sendMessage(colour(errorPrefix + "You must specify a player name."));
            return;
        }
        if (args[1].equals("all")) {
            for (MessageDisplayer listener : MessageDisplayer.listeners) {
                runSet(sender, new String[]{args[0], listener.name, args[2]}, value);
            }
            return;
        }

        sender.sendMessage(colour(prefix + (value ? "Enabling" : "Disabling") + " " + args[1] + " listeners."));

        // Args length is always 3+
        String messageType = args[1];
        String targetName = args[2];

        if (!targetName.equals(sender.getName()) &&
                !checkPermission(sender, plugin.getConfig().getString("permission." + messageType + ".others"))) {
            sender.sendMessage(colour(noPermissionsMessage));
            return;
        }

        if (targetName.equals(sender.getName()) &&
                !checkPermission(sender, plugin.getConfig().getString("permission." + messageType + ".me"))) {
            sender.sendMessage(colour(noPermissionsMessage));
            return;
        }

        plugin.getConfig().set("cache." + targetName + "." + messageType, value);
    }

    private void runSave(CommandSender sender) {
        if (!checkPermission(sender, plugin.getConfig().getString("permission.save"))) {
            sender.sendMessage(colour(noPermissionsMessage));
            return;
        }

        sender.sendMessage(prefix + "Saving configuration.");
        plugin.saveConfig();
        sender.sendMessage(prefix + "Done");
    }

    private void runQuery(CommandSender sender, String[] args) {
        if (args.length == 1) {
            runQuery(sender, new String[]{args[0], "all"});
            return;
        }
        if (args.length == 2) {
            if (sender instanceof Player) runQuery(sender, new String[]{args[0], args[1], sender.getName()});
            else sender.sendMessage(colour(errorPrefix + "You must specify a player name."));
            return;
        }
        if (args[1].equals("all")) {
            for (MessageDisplayer listener : MessageDisplayer.listeners) {
                runQuery(sender, new String[]{args[0], listener.name, args[2]});
            }
            return;
        }

        String messageType = args[1];
        String targetName = args[2];

        if (!targetName.equals(sender.getName()) &&
                !checkPermission(sender, plugin.getConfig().getString("permission." + messageType + ".query.others"))) {
            sender.sendMessage(colour(noPermissionsMessage));
            return;
        }

        if (targetName.equals(sender.getName()) &&
                !checkPermission(sender, plugin.getConfig().getString("permission." + messageType + ".query.me"))) {
            sender.sendMessage(colour(noPermissionsMessage));
            return;
        }

        sender.sendMessage(colour(prefix + messageType.substring(0, 1).toUpperCase() + messageType.substring(1) + " is " +
                (plugin.getConfig().getBoolean("cache." + targetName + "." + messageType) ? "enabled." : "disabled.")));
    }

    public LoginMSGCommand(LoginMSG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return help(sender);

        switch (args[0]) {
            case "reload": runReload(sender, args); break;
            case "enable": runSet(sender, args, true); break;
            case "disable": runSet(sender, args, false); break;
            case "save": runSave(sender); break;
            case "query": runQuery(sender, args); break;
            default: help(sender);
        }

        return true;
    }
}
