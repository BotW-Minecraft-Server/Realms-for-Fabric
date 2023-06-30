package link.botwmcs.samchai.realms.screen;

import link.botwmcs.samchai.realms.config.RealmsCommonConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class SinglePlayerLockScreen implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        int x = 0;
        int y = 0;
        Minecraft mc = Minecraft.getInstance();
        RealmsCommonConfig config = AutoConfig.getConfigHolder(RealmsCommonConfig.class).getConfig();
        boolean enableSinglePlayerFunction = !config.enable_single_player_function;
        if (mc != null && mc.isSingleplayer() && enableSinglePlayerFunction) {
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            drawContext.pose().pushPose();
            drawContext.pose().scale(1F, 1F, 1F);
            drawContext.drawString(mc.font, Component.translatable("gui.realms.singleplayerlocked"), width / 2 - 50, height / 2 - 10, 0xFFFFFF);
            drawContext.pose().popPose();
        }
    }
}
