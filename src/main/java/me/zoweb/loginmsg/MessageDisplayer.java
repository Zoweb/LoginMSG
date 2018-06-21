package me.zoweb.loginmsg;

import me.zoweb.loginmsg.lambda.Lambda;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adds an event listener, which will set the message and/or play sounds to
 * the running user when it happens.
 *
 * @param <TEvent> The event class. For example, to listen for a player
 *                logging in, use <code>PlayerJoinEvent</code>
 */
public class MessageDisplayer<TEvent extends PlayerEvent> implements Listener {
    public static List<MessageDisplayer> listeners = new ArrayList<>();

    /**
     * Listen for an event specified by <code>TEvent</code>
     * @param name The name to be used in config to reference values used here
     * @param valueResetter A lambda that is run before any other code is. Run
     *                      once per login
     * @param <TEvent> The event class. For example, to listen for a player
     *                logging in, use <code>PlayerJoinEvent</code>
     */
    public static <TEvent extends PlayerEvent> void listen(String name, Lambda<TEvent> valueResetter) {
        new MessageDisplayer<>(name, valueResetter).register();
    }

    /**
     * Listen for an event specified by <code>TEvent</code>
     * @param name The name to be used in config to reference values used here
     * @param <TEvent> The event class. For example, to listen for a player
     *                logging in, use <code>PlayerJoinEvent</code>
     */
    public static <TEvent extends PlayerEvent> void listen(String name) {
        MessageDisplayer.<TEvent>listen(name, a -> {});
    }

    private YamlConfiguration data;
    protected Lambda<TEvent> valueResetter;
    public String name;

    protected MessageDisplayer(String name, Lambda<TEvent> valueResetter) {
        this.name = name;
        this.valueResetter = valueResetter;

        listeners.add(this);
    }

    public void loadData(File location) throws IOException, InvalidConfigurationException {
        this.data.load(location);
    }

    public void register() {
        LoginMSG.registerEvents(this);
    }

    public void onEvent(TEvent event) {
        valueResetter.run(event);


    }
}
