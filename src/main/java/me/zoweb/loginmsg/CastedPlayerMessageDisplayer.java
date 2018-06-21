package me.zoweb.loginmsg;

import me.zoweb.loginmsg.lambda.Lambda;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.junit.runners.model.InitializationError;

import java.util.function.Consumer;

/**
 * Adds an event listener, which will set the message and/or play sounds to
 * the running user when it happens.
 *
 * @param <TEvent> <code>EntityEvent</code>, will be converted to a
 *                <code>PlayerEvent</code> so it is compatible
 */
public abstract class CastedPlayerMessageDisplayer<TEvent extends EntityEvent> extends MessageDisplayer<PlayerEntityEvent<TEvent>> implements Listener {
    protected CastedPlayerMessageDisplayer(String name, Consumer<TEvent> valueResetter) {
        super(name, event -> {
            valueResetter.accept(event.getEvent());
        });
    }

    public void register() {
        LoginMSG.registerEvents(this);
    }

    protected void run(TEvent event) {
        try {
            super.run(new PlayerEntityEvent<>(event));
        } catch (InitializationError initializationError) {
            initializationError.printStackTrace();
        }
    }
}
