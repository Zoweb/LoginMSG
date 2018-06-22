package me.zoweb.loginmsg.listener;

import me.zoweb.loginmsg.MessageDisplayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.function.Consumer;

/**
 * Event handler for when a player quits the server
 */
public class PlayerQuitListener extends MessageDisplayer<PlayerQuitEvent> implements Listener {
    public PlayerQuitListener(String name, Consumer<PlayerQuitEvent> valueResetter) {
        super(name, valueResetter);
    }

    @Override
    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        run(event);
    }
}
