package link.botwmcs.samchai.realms.screen.multiplayer;

import link.botwmcs.samchai.realms.Realms;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RealmsServerScreen extends Screen {
    protected final Screen lastScreen;
    ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/block/cut_copper.png");
    ResourceLocation ELEMENTS = new ResourceLocation(Realms.MODID, "textures/gui/elements.png");
    String BOTWMCS_COPYRIGHT = "MIT SOFTWARE, COPYRIGHT 2023 BOTWMCS";

    public RealmsServerScreen(Screen screen) {
        super(Component.translatable("menu.botwmcs.realms"));
        this.lastScreen = screen;
    }

    protected void init() {
    }

    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }

    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderBrickBackground(guiGraphics);
        this.renderCopyrightComponent(guiGraphics);
        this.renderServerSelectorBackground(guiGraphics);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
        super.render(guiGraphics, i, j, f);
    }

    public void renderBrickBackground(GuiGraphics guiGraphics) {
        guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        guiGraphics.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderCopyrightComponent(GuiGraphics guiGraphics) {
        guiGraphics.drawCenteredString(this.font, BOTWMCS_COPYRIGHT, this.width / 2, this.height - 10, 16777215);
    }

    public void renderServerSelectorBackground(GuiGraphics guiGraphics) {
        // Render sidebar
        int leftSideHeight = 52;
        int leftSideWidth = 8;
        int middleScreen = this.width / 2;
        guiGraphics.blit(ELEMENTS, middleScreen, 0, 0, 0, 0, 8, 52, 256, 256);
    }

    public void renderSeverSelector(GuiGraphics guiGraphics) {
    }

    public boolean isMouseHovered() {

        return false;
    }

}
