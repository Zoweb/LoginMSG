package me.zoweb.loginmsg;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.junit.runners.model.InitializationError;

/**
 * Converts <code>EntityEvent</code>s to <code>PlayerEvent</code>s if that
 * <code>EntityEvent</code> is for players.<br/>
 * <br/>
 * For example, this could be used with the <code>PlayerDeathEvent</code> to
 * convert it to a <code>PlayerEvent</code> (it is a <code>EntityEvent</code>).
 *
 * @param <TEvent> The <code>EntityEvent</code> to convert to a
 *                <code>PlayerEvent</code>. <code>event#getEntity()</code> must
 *                be castable to a <code>Player</code>, otherwise an
 *                <code>InitializationError</code> will be thrown.
 *
 * @see org.bukkit.event.player.PlayerEvent Player Events - include
 *                                          <code>getPlayer()</code>
 * @see org.bukkit.event.entity.EntityEvent Entity Events - use
 *                                          <code>getEntity()</code> instead.
 */

public class PlayerEntityEvent<TEvent extends EntityEvent> extends PlayerEvent {
    private TEvent event;
    private HandlerList handlers = new HandlerList();

    public PlayerEntityEvent(TEvent convertable) throws InitializationError {
        super(null);

        event = convertable;

        if (!(convertable.getEntity() instanceof Player)) {
            throw new InitializationError("Invalid entity event");
        }

        player = (Player) convertable.getEntity();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public TEvent getEvent() {
        return event;
    }
}
