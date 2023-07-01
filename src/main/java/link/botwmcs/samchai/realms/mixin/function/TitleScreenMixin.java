package link.botwmcs.samchai.realms.mixin.function;

import link.botwmcs.samchai.realms.screen.multiplayer.BotwServerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
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
        this.addRenderableWidget(Button.builder(Component.translatable("menu.botwmcs.realms"), (button) -> {
            this.minecraft.setScreen(new BotwServerScreen(this));
        }).bounds(this.width / 2 - 100, i, 200, 20).build());

//        this.addRenderableWidget(Button.builder(Component.translatable("menu.botwmcs.realms"), (button) -> {
//            this.minecraft.setScreen(new RealmsServerScreen(this));
//        }).bounds(this.width / 2 - 100, i + j * 1, 200, 20).build());


    }
}
