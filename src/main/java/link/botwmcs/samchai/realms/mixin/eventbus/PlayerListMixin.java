package link.botwmcs.samchai.realms.mixin.eventbus;

import link.botwmcs.samchai.realms.event.player.PlayerEventHandler;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = PlayerList.class, priority = 1001)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    protected void injectPlayerJoinEvent(Connection connection, ServerPlayer player, CallbackInfo ci) {
        PlayerEventHandler.PLAYER_LOGGED_IN_EVENT.invoker().onPlayerLoggedIn(player.level(), player);
    }
    @Inject(method = "remove", at = @At("HEAD"))
    protected void injectPlayerLeaveEvent(ServerPlayer player, CallbackInfo ci) {
        PlayerEventHandler.PLAYER_LOGGED_OUT_EVENT.invoker().onPlayerLoggedOut(player.level(), player);
    }
}
