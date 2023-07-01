package link.botwmcs.samchai.realms.mixin.function;

import link.botwmcs.samchai.realms.Realms;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ObjectOutputStream;

@Mixin(JoinMultiplayerScreen.class)
public abstract class JoinMultiplayerScreenMixin extends Screen {
    @Shadow protected ServerSelectionList serverSelectionList;
    private final int listHeight = 96;

    protected JoinMultiplayerScreenMixin(Component component) {
        super(component);
    }


    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/multiplayer/ServerSelectionList;updateSize(IIII)V"))
    protected void modifyServerSelectionListWhenInited(ServerSelectionList instance, int i, int j, int k, int l) {
        this.serverSelectionList.updateSize(this.width, this.height, listHeight, listHeight + 80);
    }

    @Redirect(method = "init", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/multiplayer/JoinMultiplayerScreen;serverSelectionList:Lnet/minecraft/client/gui/screens/multiplayer/ServerSelectionList;", opcode = Opcodes.PUTFIELD))
    protected void modifyServerSelectionList(JoinMultiplayerScreen joinMultiplayerScreen, ServerSelectionList serverSelectionList) {
        this.serverSelectionList = new ServerSelectionList((JoinMultiplayerScreen) (Object) this, this.minecraft, this.width, this.height, listHeight, listHeight + 80, 36);
    }

//    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/multiplayer/JoinMultiplayerScreen;renderBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"))
//    protected void modifyRenderBackground(JoinMultiplayerScreen instance, GuiGraphics guiGraphics) {
//        this.renderBrickBackground(guiGraphics);
//    }

    /**
     * @author Sam_Chai
     * @reason Overwrite render for realms style
     */
    @Overwrite
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        String title = Component.translatable("menu.botwmcs.realms").getString();
        ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/block/cut_copper.png");
        guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        guiGraphics.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.serverSelectionList.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, title, this.width / 2, 20, 16777215);
        super.render(guiGraphics, i, j, f);
    }


}
