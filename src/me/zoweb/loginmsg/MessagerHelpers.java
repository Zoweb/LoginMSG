package me.zoweb.loginmsg;

import org.bukkit.entity.Player;

/**
 * LoginMSG (c) zachy 2017
 */
class MessagerHelpers {

    static boolean checkPlayerPerms(Player player, String key) {
        return player.hasPermission(key.replace(' ', '.')) || key.equals("server op") && player.isOp() || key.equalsIgnoreCase("player " + player.getName());
    }

    static String parsePlayerVariable(String string, Player player) {
        return string.replace("%player%", player.getName());
    }

}
