package link.botwmcs.samchai.realms.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlayerEventHandler {
    private PlayerEventHandler() {
    }
    // Code from https://github.com/ricksouth/serilum-mc-mod-sources/blob/main/sources/Collective/1.20.0/Fabric/src/main/java/com/natamus/collective/fabric/callbacks/CollectivePlayerEvents.java
    public static final Event<PlayerLoggedIn> PLAYER_LOGGED_IN_EVENT = EventFactory.createArrayBacked(PlayerLoggedIn.class, callbacks -> (world, player) -> {
        for (PlayerLoggedIn callback : callbacks) {
            callback.onPlayerLoggedIn(world, player);
        }
    });
    @FunctionalInterface
    public interface PlayerLoggedIn {
        void onPlayerLoggedIn(Level world, Player player);
    }

    public static final Event<PlayerLoggedOut> PLAYER_LOGGED_OUT_EVENT = EventFactory.createArrayBacked(PlayerLoggedOut.class, callbacks -> (world, player) -> {
        for (PlayerLoggedOut callback : callbacks) {
            callback.onPlayerLoggedOut(world, player);
        }
    });
    @FunctionalInterface
    public interface PlayerLoggedOut {
        void onPlayerLoggedOut(Level world, Player player);
    }
}
