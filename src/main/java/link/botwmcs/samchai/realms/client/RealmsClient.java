package link.botwmcs.samchai.realms.client;

import link.botwmcs.samchai.realms.screen.HudOverlay;
import link.botwmcs.samchai.realms.screen.SinglePlayerLockScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class RealmsClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        loadScreens();
    }

    private void loadScreens() {
        HudRenderCallback.EVENT.register(new SinglePlayerLockScreen());
        HudRenderCallback.EVENT.register(new HudOverlay());
    }
}
