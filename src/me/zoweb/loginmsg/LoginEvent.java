package me.zoweb.loginmsg;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.*;

import static org.bukkit.Bukkit.getServer;

/**
 * LoginMSG (c) zachy 2017
 */
public class LoginEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");

        // Message to be sent to players with message sending enabled
        String loginMessage = "";

        // Sound to be sent to players with message sending enabled
        Sound loginSound = Sound.UI_BUTTON_CLICK;

        // LoginMSG plugin
        Plugin loginMSG = getServer().getPluginManager().getPlugin("LoginMSG");

        // Player
        Player player = event.getPlayer();

        try {

            // Check if the player has set an option for logging in messages
            File configFile = new File(loginMSG.getDataFolder(), "player-settings.yml");
            YamlConfiguration playerSettings = YamlConfiguration.loadConfiguration(configFile);
            String pathName = player.getUniqueId().toString() + ".messages-enabled";

            if (!(playerSettings.isSet(pathName) && playerSettings.isBoolean(pathName))) {
                // If they haven't, set it for them (must be they're first time on!)
                playerSettings.set(pathName, true);
                playerSettings.save(configFile);
            }


            // Get config for LoginMSG to use it when sending a message
            Configuration configuration = loginMSG.getConfig();

            // Custom Messages section
            ConfigurationSection customMessagesSection = configuration.getConfigurationSection("custom-messages");
            boolean hasDisplayedCustomMessage = false;
            for (String key : customMessagesSection.getKeys(false)) {
                if (MessagerHelpers.checkPlayerPerms(player, key)) {
                    String currentLoginMessage = customMessagesSection.getConfigurationSection(key).getString("login-message");
                    if (currentLoginMessage != null) {
                        // Set login message, with custom %player% variable and colours too!
                        loginMessage = ChatColor.translateAlternateColorCodes('&', MessagerHelpers.parsePlayerVariable(currentLoginMessage, player));

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

                loginMessage = ChatColor.translateAlternateColorCodes('&', MessagerHelpers.parsePlayerVariable(preParsedLoginMessage, player));
            }

            // Custom sounds section
            boolean useSounds = configuration.getBoolean("use-sounds.join");
            if (useSounds) {
                ConfigurationSection customSoundsSection = configuration.getConfigurationSection("custom-sounds");
                boolean hasDisplayedCustomSound = false;
                boolean hasPlayedCustomSound = false;
                for (String key : customSoundsSection.getKeys(false)) {
                    if (MessagerHelpers.checkPlayerPerms(player, key)) {
                        String currentLoginSound = customSoundsSection.getConfigurationSection(key).getString("login-sound");
                        if (currentLoginSound != null) {
                            // Set login sound
                            loginSound = Sound.valueOf(currentLoginSound);

                            // Because we used a custom sound, we need to set hasDisplayedCustomSound to true
                            hasDisplayedCustomSound = true;
                            // Exit out of loop to stop setting more messages
                            break;
                        }
                    }
                }

                // Set default sound for people who don't meet the conditions for a custom sound
                if (!hasDisplayedCustomSound) {
                    String currentLoginSoundAsString = configuration.getString("login-sound");
                    if (currentLoginSoundAsString == null) {
                        useSounds = false;
                    } else {
                        loginSound = Sound.valueOf(currentLoginSoundAsString);
                    }
                }
            }

            // Display the messages
            for (Player currentPlayer : getServer().getOnlinePlayers()) {

                if (playerSettings.getBoolean(player.getUniqueId().toString() + ".messages-enabled")) {
                    currentPlayer.sendMessage(loginMessage);

                    if (useSounds) {
                        currentPlayer.playSound(currentPlayer.getLocation(), loginSound, 1.0F, 1.0F);
                    }
                }

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
