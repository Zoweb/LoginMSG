package me.zoweb.loginmsg.listener;

import me.zoweb.loginmsg.MessageDisplayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.Consumer;

public class PlayerJoinListener extends MessageDisplayer<PlayerJoinEvent> implements Listener {
    public PlayerJoinListener(String name, Consumer<PlayerJoinEvent> valueResetter) {
        super(name, valueResetter);
    }

    @Override
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        run(event);
    }
}
