package link.botwmcs.samchai.realms.mixin.function;

import link.botwmcs.samchai.realms.Realms;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(net.minecraft.client.gui.screens.multiplayer.ServerSelectionList.LANHeader.class)
public abstract class ServerSelectionListLANHeaderMixin {
    @Shadow @Final private Minecraft minecraft;

    /**
     * @author Sam_Chai
     * @reason Delete "Scanning for games on your local network"
     */
    @Overwrite
    public void render(GuiGraphics guiGraphics, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
        String text = Component.translatable("gui.realms.finding").getString();
        int var10000 = j + m / 2;
        int p = var10000 - 9 / 2;
        guiGraphics.drawString(this.minecraft.font, text, this.minecraft.screen.width / 2 - this.minecraft.font.width(text) / 2, p, 16777215, false);
        String string = LoadingDotsText.get(Util.getMillis());
        Font var10001 = this.minecraft.font;
        int var10003 = this.minecraft.screen.width / 2 - this.minecraft.font.width(string) / 2;
        Objects.requireNonNull(this.minecraft.font);
        guiGraphics.drawString(var10001, string, var10003, p + 9, 8421504, false);
    }
}
