package link.botwmcs.samchai.realms.screen.element;

import link.botwmcs.samchai.realms.Realms;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class BotwLogoRenderer {
    public static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
    public static final ResourceLocation EASTER_EGG_LOGO = new ResourceLocation("textures/gui/title/minceraft.png");
    public static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation("textures/gui/title/edition.png");
    public static final ResourceLocation BOTWMCS_REALMS = new ResourceLocation(Realms.MODID, "textures/gui/title/botwmcs_realms.png");
    public static final int LOGO_WIDTH = 256;
    public static final int LOGO_HEIGHT = 44;
    private static final int LOGO_TEXTURE_WIDTH = 256;
    private static final int LOGO_TEXTURE_HEIGHT = 64;
    private static final int EDITION_WIDTH = 128;
    private static final int EDITION_HEIGHT = 14;
    private static final int EDITION_TEXTURE_WIDTH = 128;
    private static final int EDITION_TEXTURE_HEIGHT = 16;
    public static final int DEFAULT_HEIGHT_OFFSET = 30;
    private static final int EDITION_LOGO_OVERLAP = 7;
    private final boolean showEasterEgg = (double) RandomSource.create().nextFloat() < 1.0E-4;
    private final boolean keepLogoThroughFade;

    public BotwLogoRenderer(boolean bl) {
        this.keepLogoThroughFade = bl;
    }

    public void renderLogo(GuiGraphics guiGraphics, int i, float f) {
        this.renderLogo(guiGraphics, i, f, 30);
    }

    public void renderLogo(GuiGraphics guiGraphics, int i, float f, int j) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : f);
        guiGraphics.blit(BOTWMCS_REALMS, i / 2 - 128, j - 15, 0.0F, 0.0F, 256, 324 / 4, 256, 324 / 4);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
