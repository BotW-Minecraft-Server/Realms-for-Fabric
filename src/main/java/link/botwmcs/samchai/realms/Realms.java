package link.botwmcs.samchai.realms;

import com.mojang.logging.LogUtils;
import link.botwmcs.samchai.realms.command.ProfessionCommand;
import link.botwmcs.samchai.realms.event.player.PlayerEventHandler;
import link.botwmcs.samchai.realms.event.player.WorldJoinEvent;
import link.botwmcs.samchai.realms.event.player.WorldLeaveEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;

public class Realms implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "realms";
    @Override
    public void onInitialize() {
        loadEvents();
        loadCommands();


    }

    private void loadEvents() {
        PlayerEventHandler.PLAYER_LOGGED_IN_EVENT.register(WorldJoinEvent::onLoggedIn);
        PlayerEventHandler.PLAYER_LOGGED_OUT_EVENT.register(WorldLeaveEvent::onLoggedOut);
    }

    private void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ProfessionCommand.register(dispatcher);
        });
    }

}
