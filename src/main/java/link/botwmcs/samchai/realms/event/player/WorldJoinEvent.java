package link.botwmcs.samchai.realms.event.player;

import link.botwmcs.samchai.realms.Realms;
import link.botwmcs.samchai.realms.util.PlayerDataHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Objects;


public class WorldJoinEvent {

    public static void onLoggedIn(Level world, Player player) {
        if (world.isClientSide) {
            return;
        }
        if (Objects.equals(PlayerDataHandler.getProfession(player), "default")) {
            player.sendSystemMessage(Component.nullToEmpty("Welcome to Realms!"));
            player.sendSystemMessage(Component.nullToEmpty("Please choose a profession using /profession <profession>"));
        } else {
            player.sendSystemMessage(Component.nullToEmpty("Welcome back to Realms!"));
            player.sendSystemMessage(Component.nullToEmpty("Your profession is " + PlayerDataHandler.getProfession(player)));
        }
//        Realms.LOGGER.info("Player " + player.getName().getString() + " has joined the world.");
    }


}
