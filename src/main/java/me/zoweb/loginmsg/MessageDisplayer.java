package me.zoweb.loginmsg;

import me.zoweb.loginmsg.command.LoginMSGCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Adds an event listener, which will set the message and/or play sounds to
 * the running user when it happens.
 *
 * @param <TEvent> The event class. For example, to listen for a player
 *                logging in, use <code>PlayerJoinEvent</code>
 */
public abstract class MessageDisplayer<TEvent extends PlayerEvent> {
    /**
     * List of every MessageDisplayer
     */
    public static List<MessageDisplayer> listeners = new ArrayList<>();

    private YamlConfiguration data;
    private Class<TEvent> clazz;

    /**
     * Consumer to reset the value of this listener
     */
    protected Consumer<TEvent> valueResetter;

    /**
     * Name of this listener
     */
    public String name;

    private String getValue(Player player, String section) {
        ConfigurationSection conf = data.getConfigurationSection(section);

        if (!conf.getBoolean("enabled")) return null;

        String[] order = conf.getString("check first").replaceAll("[,;.]", "").split(" then ");

        String matching = readValue(player, conf.getConfigurationSection("when matching"), order, false);
        if (matching == null) matching = readValue(player, conf.getConfigurationSection("otherwise"), order, true);
        if (matching == null) matching = conf.getString("default");

        return matching;
    }

    private String readValue(Player player, ConfigurationSection conf, String[] order, boolean invert) {
        for (String current : order) {
            switch (current) {
                case "ops":
                    if (player.isOp() ^ invert) return conf.getString("ops");
                    break;
                case "usernames":
                    if (conf.getConfigurationSection("by username").contains(player.getName().replace('.', ' ')) ^ invert)
                        return conf.getString("by username." + player.getName().replace('.', ' '));
                    break;
                case "permissions":
                    for (String permission : conf.getConfigurationSection("by permission").getKeys(true)) {
                        String perm = permission.replace(' ', '.');
                        if (player.hasPermission(perm) ^ invert) return conf.getString("by permission." + permission);
                    }
            }
        }

        return null;
    }

    /**
     * Creates a MessageDisplayer instance
     * @param name The name of the message displayer
     * @param valueResetter The consumer to run to reset the message value
     */
    protected MessageDisplayer(String name, Consumer<TEvent> valueResetter) {
        this.name = name;
        this.valueResetter = valueResetter;
        data = new YamlConfiguration();

        listeners.add(this);
    }

    /**
     * Loads data from the configuration file specified
     * @param location The location of the configuration file
     * @throws IOException File could not be found
     * @throws InvalidConfigurationException File has invalid markup
     */
    public void loadData(File location) throws IOException, InvalidConfigurationException {
        data.load(location);
    }

    /**
     * Runs message and sounds and sends to all players
     * @param event Event instance to use
     */
    protected void run(TEvent event) {
        valueResetter.accept(event);

        if (data == null) throw new NullPointerException("Data is null. Run loadData() to fix.");

        Player player = event.getPlayer();

        if (data.getBoolean("messages.enabled")) {
            String result = getValue(player, "messages");
            if (result != null) {
                result = result.replace("%player%", player.getDisplayName());
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', result));
                for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                    pl.sendMessage(ChatColor.translateAlternateColorCodes('&', result));
                }
            }
        }
        if (data.getBoolean("sounds.enabled")) {
            String result = getValue(player, "sounds");
            if (result != null) {
                Bukkit.getServer().getConsoleSender().sendMessage(LoginMSGCommand.prefix + "(sound: " + result + ")");
                for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                    pl.playSound(pl.getLocation(), result, 1f, 1f);
                }
            }
        }
    }

    /**
     * Override to signal to Spigot what event to listen to.
     * @param event The event data. !! MUST NOT BE GENERIC !!
     */
    public abstract void onEvent(TEvent event);
}
