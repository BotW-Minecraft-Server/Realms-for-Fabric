package link.botwmcs.samchai.realms.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;

@Environment(EnvType.CLIENT)
public class HudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {

    }
}
