package link.botwmcs.samchai.realms.mixin.function;

import com.mojang.blaze3d.systems.RenderSystem;
import link.botwmcs.samchai.realms.Realms;
import link.botwmcs.samchai.realms.screen.element.BotwLogoRenderer;
import link.botwmcs.samchai.realms.screen.element.ImageTextButton;
import link.botwmcs.samchai.realms.screen.multiplayer.BotwServerScreen;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.Objects;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow @Final private boolean fading;

    @Shadow private long fadeInStart;
    private final BotwLogoRenderer logoRenderer = new BotwLogoRenderer(false);

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
        }).texStart(0, 140).offset(0, 0).yDiffTex(20).usedTextureSize(98, 20).textureSize(256, 256).build());
        botwRealmsButton.setPosition(this.width / 2 - 100, i);
        botwRealmsButton.setWidth(98);
        botwRealmsButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.realms.tooltip")));

        // Official website button
        String url = "https://botwmcs.link";
        final AbstractWidget officialWebsiteButton = addRenderableWidget(ImageTextButton.builder(Component.translatable("menu.botwmcs.botwpage"), buttons, button -> {
            Util.getPlatform().openUri(url);
        }).texStart(0, 140).offset(0, 0).yDiffTex(20).usedTextureSize(98, 20).textureSize(256, 256).build());
        officialWebsiteButton.setPosition(this.width / 2 + 2, i);
        officialWebsiteButton.setWidth(98);
        officialWebsiteButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.botwpage.tooltip")));

        // Social screen button
        final AbstractWidget socialButton = addRenderableWidget(Button.builder(Component.translatable("menu.botwmcs.social"), (button) -> {
//            this.minecraft.setScreen(new SocialScreen(this));
        }).bounds(this.width / 2 - 100, i + j * 1, 98, 20).build());
        socialButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.social.tooltip")));

        // Personal screen button
        final AbstractWidget personalButton = addRenderableWidget(Button.builder(Component.translatable("menu.botwmcs.personal"), (button) -> {
//            this.minecraft.setScreen(new PersonalScreen(this));
        }).bounds(this.width / 2 + 2, i + j * 1, 98, 20).build());
        personalButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.personal.tooltip")));

        // Singleplayer button (never used)
        final AbstractWidget singleplayerButton = addRenderableWidget(Button.builder(Component.translatable("menu.botwmcs.singleplayer.inactive"), (button) -> {
            button.active = false;
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(this.width / 2 - 100, i + j * 2, 200, 20).build());
        singleplayerButton.active = false;
        singleplayerButton.setTooltip(Tooltip.create(Component.translatable("menu.botwmcs.singleplayer.inactive.tooltip")));

    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/LogoRenderer;renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IF)V"))
    public void modifyRender(LogoRenderer instance, GuiGraphics guiGraphics, int i, float f) {
        float g = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        float h = this.fading ? Mth.clamp(g - 1.0F, 0.0F, 1.0F) : 1.0F;
        this.logoRenderer.renderLogo(guiGraphics, this.width, h);
    }
}
