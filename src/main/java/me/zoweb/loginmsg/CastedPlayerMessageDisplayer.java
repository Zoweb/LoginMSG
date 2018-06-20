package me.zoweb.loginmsg;

import me.zoweb.loginmsg.lambda.Lambda;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.junit.runners.model.InitializationError;

/**
 * Adds an event listener, which will set the message and/or play sounds to
 * the running user when it happens.
 *
 * @param <TEvent> <code>EntityEvent</code>, will be converted to a
 *                <code>PlayerEvent</code> so it is compatible
 */
public class CastedPlayerMessageDisplayer<TEvent extends EntityEvent> extends MessageDisplayer<PlayerEntityEvent<TEvent>> implements Listener {
    /**
     * Listen for an event specified by <code>TEvent</code>
     * @param name The name to be used in config to reference values used here
     * @param valueResetter A lambda that is run before any other code is. Run
     *                      once per login
     */
    public static <TEvent extends EntityEvent> void listenCasted(String name, Lambda<TEvent> valueResetter) {
        new CastedPlayerMessageDisplayer<>(name, valueResetter).register();
    }

    /**
     * Listen for an event specified by <code>TEvent</code>
     * @param name The name to be used in config to reference values used here
     * @param <TEvent> The event class. For example, to listen for a player
     *                logging in, use <code>PlayerJoinEvent</code>
     */
    public static <TEvent extends EntityEvent> void listenCasted(String name) {
        CastedPlayerMessageDisplayer.<TEvent>listenCasted(name, a -> {});
    }

    private CastedPlayerMessageDisplayer(String name, Lambda<TEvent> valueResetter) {
        super(name, event -> {
            valueResetter.run(event.getEvent());
        });
    }

    public void register() {
        LoginMSG.registerEvents(this);
    }

    public void onEvent(TEvent event) {
        try {
            super.onEvent(new PlayerEntityEvent<>(event));
        } catch (InitializationError initializationError) {
            initializationError.printStackTrace();
        }
    }
}
