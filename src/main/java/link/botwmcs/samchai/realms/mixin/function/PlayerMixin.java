package link.botwmcs.samchai.realms.mixin.function;

import com.mojang.authlib.GameProfile;
import link.botwmcs.samchai.realms.Realms;
import link.botwmcs.samchai.realms.util.PlayerDataHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow public abstract Component getName();

    @Shadow public abstract Inventory getInventory();

    @Shadow public abstract boolean isLocalPlayer();

    @Inject(method = "getDestroySpeed", at = @At("TAIL"), cancellable = true)
    protected void modifyDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        if (this.isLocalPlayer()) {
            cir.setReturnValue(cir.getReturnValue());
        } else {
            ItemStack heldItem = this.getInventory().getItem(this.getInventory().selected);
            boolean isHoldingTool = heldItem.is(ItemTags.TOOLS);
            if (PlayerDataHandler.getProfession(this.getInventory().player).equals("miner") && isHoldingTool) {
                cir.setReturnValue(cir.getReturnValue() * 1.5F);
                Realms.LOGGER.info("Player " + this.getName().getString() + " dig speed is: " + cir.getReturnValue() + " (modified)");
            } else {
                cir.setReturnValue(cir.getReturnValue());
            }
        }
    }

}
