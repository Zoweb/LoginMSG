package me.zoweb.loginmsg.listener;

import me.zoweb.loginmsg.CastedPlayerMessageDisplayer;
import me.zoweb.loginmsg.MessageDisplayer;
import me.zoweb.loginmsg.PlayerEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.function.Consumer;

public class PlayerDeathListener extends CastedPlayerMessageDisplayer<PlayerDeathEvent> implements Listener {
    public PlayerDeathListener(String name, Consumer<PlayerDeathEvent> valueResetter) {
        super(name, valueResetter);
    }

    @Override
    @EventHandler
    public void onEvent(PlayerEntityEvent<PlayerDeathEvent> event) {
        run(event);
    }
}
