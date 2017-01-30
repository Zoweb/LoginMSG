package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.bukkit.Bukkit.getServer;

/**
 * LoginMSG (c) zachy 2017
 */
public class LogoutEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");

        // Message to be sent to players with message sending enabled
        String logoutMessage = "";

        // Sound to be sent to players with message sending enabled
        Sound logoutSound = Sound.UI_BUTTON_CLICK;

        // LoginMSG plugin
        Plugin loginMSG = getServer().getPluginManager().getPlugin("LoginMSG");

        // Player
        Player player = event.getPlayer();

        try {
            // Get config for LoginMSG to use it when sending a message
            Configuration configuration = loginMSG.getConfig();

            // Get config for player settings
            File configFile = new File(loginMSG.getDataFolder(), "player-settings.yml");
            YamlConfiguration playerSettings = YamlConfiguration.loadConfiguration(configFile);
            String pathName = player.getUniqueId().toString() + ".messages-enabled";

            // Custom Messages section
            ConfigurationSection customMessagesSection = configuration.getConfigurationSection("custom-messages");
            boolean hasDisplayedCustomMessage = false;
            for (String key : customMessagesSection.getKeys(false)) {
                if (MessagerHelpers.checkPlayerPerms(player, key)) {
                    String currentLogoutMessage = customMessagesSection.getConfigurationSection(key).getString("login-message");
                    if (currentLogoutMessage != null) {
                        // Set login message, with custom %player% variable and colours too!
                        logoutMessage = ChatColor.translateAlternateColorCodes('&', MessagerHelpers.parsePlayerVariable(currentLogoutMessage, player));

                        // Because we used a custom message, we need to set hasDisplayedCustomMessage to true
                        hasDisplayedCustomMessage = true;
                        // Exit out of for loop to stop setting more messages.
                        break;
                    }
                }
            }

            // Set default message for people who don't meet the conditions for a custom message
            if (!hasDisplayedCustomMessage) {
                String currentLoginMessage = configuration.getString("login-message");
                String preParsedLoginMessage = "";
                if (currentLoginMessage == null) {
                    preParsedLoginMessage = "&e&l%player%&e joined the game.";
                } else {
                    preParsedLoginMessage = currentLoginMessage;
                }

                logoutMessage = ChatColor.translateAlternateColorCodes('&', MessagerHelpers.parsePlayerVariable(preParsedLoginMessage, player));
            }

            // Custom sounds section
            boolean useSounds = configuration.getBoolean("use-sounds.leave");
            if (useSounds) {
                ConfigurationSection customSoundsSection = configuration.getConfigurationSection("custom-sounds");
                boolean hasDisplayedCustomSound = false;
                boolean hasPlayedCustomSound = false;
                for (String key : customSoundsSection.getKeys(false)) {
                    if (MessagerHelpers.checkPlayerPerms(player, key)) {
                        String currentLoginSound = customSoundsSection.getConfigurationSection(key).getString("logout-sound");
                        if (currentLoginSound != null) {
                            // Set login sound
                            logoutSound = Sound.valueOf(currentLoginSound);

                            // Because we used a custom sound, we need to set hasDisplayedCustomSound to true
                            hasDisplayedCustomSound = true;
                            // Exit out of loop to stop setting more messages
                            break;
                        }
                    }
                }

                // Set default sound for people who don't meet the conditions for a custom sound
                if (!hasDisplayedCustomSound) {
                    String currentLoginSoundAsString = configuration.getString("logout-sound");
                    if (currentLoginSoundAsString == null) {
                        useSounds = false;
                    } else {
                        logoutSound = Sound.valueOf(currentLoginSoundAsString);
                    }
                }
            }

            // Display the messages
            for (Player currentPlayer : getServer().getOnlinePlayers()) {

                if (playerSettings.getBoolean(player.getUniqueId().toString() + ".messages-enabled")) {
                    currentPlayer.sendMessage(logoutMessage);

                    if (useSounds) {
                        currentPlayer.playSound(currentPlayer.getLocation(), logoutSound, 1.0F, 1.0F);
                    }
                }

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
