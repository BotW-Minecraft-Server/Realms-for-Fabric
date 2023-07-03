package link.botwmcs.samchai.realms.mixin.function;

import link.botwmcs.samchai.realms.Realms;
import link.botwmcs.samchai.realms.screen.element.ImageTextButton;
import link.botwmcs.samchai.realms.screen.multiplayer.BotwServerScreen;
import net.minecraft.Util;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component component) {
        super(component);
    }


    /**
     * @author Sam_Chai
     * @reason Overwrite normal menu options to botwmcs style.
     */
    @Overwrite
    private void createNormalMenuOptions(int i, int j) {
        ResourceLocation buttons = new ResourceLocation(Realms.MODID, "textures/gui/buttons.png");

        // BotWMCS Realms button
        final AbstractWidget botwRealmsButton = addRenderableWidget(ImageTextButton.builder(Component.translatable("menu.botwmcs.realms"), buttons, button -> {
            this.minecraft.setScreen(new BotwServerScreen(this));
        }).texStart(0, 20).offset(0, 0).yDiffTex(20).usedTextureSize(98, 20).textureSize(256, 256).build());
        botwRealmsButton.setPosition(this.width / 2 - 100, i);
        botwRealmsButton.setWidth(98);
        botwRealmsButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.realms.tooltip")));

        // Official website button
        String url = "https://botwmcs.link";
        final AbstractWidget officialWebsiteButton = addRenderableWidget(ImageTextButton.builder(Component.translatable("menu.botwmcs.botwpage"), buttons, button -> {
            Util.getPlatform().openUri(url);
        }).texStart(0, 20).offset(0, 0).yDiffTex(20).usedTextureSize(98, 20).textureSize(256, 256).build());
        officialWebsiteButton.setPosition(this.width / 2 + 2, i);
        officialWebsiteButton.setWidth(98);
        officialWebsiteButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.botwpage.tooltip")));

        // Singleplayer button (never used)
        final AbstractWidget singleplayerButton = addRenderableWidget(Button.builder(Component.translatable("menu.botwmcs.singleplayer.inactive"), (button) -> {
            button.active = false;
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(this.width / 2 - 100, i + j * 1, 200, 20).build());
        singleplayerButton.active = false;
        singleplayerButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.singleplayer.inactive.tooltip")));



    }
}
