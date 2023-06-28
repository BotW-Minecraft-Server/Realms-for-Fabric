package link.botwmcs.samchai.realms.util;

import link.botwmcs.samchai.realms.capability.CcaCapHandler;
import link.botwmcs.samchai.realms.mixin.function.PlayerMixin;
import net.minecraft.world.entity.player.Player;

public class PlayerDataHandler {
    public static String getProfession(Player player) {
        return CcaCapHandler.ACCOUNT.get(player).getProfession();
    }
    public static String getTown(Player player) {
        return CcaCapHandler.ACCOUNT.get(player).getTown();
    }
    public static int getLevel(Player player) {
        return CcaCapHandler.ACCOUNT.get(player).getLevel();
    }

    public static void setProfession(Player player, String profession) {
        CcaCapHandler.ACCOUNT.get(player).setProfession(profession);
    }
    public static void setTown(Player player, String town) {
        CcaCapHandler.ACCOUNT.get(player).setTown(town);
    }
    public static void setLevel(Player player, int level) {
        CcaCapHandler.ACCOUNT.get(player).setLevel(level);
    }
    public static void setProfession(Player player, String profession, int level) {
        setProfession(player, profession);
        setLevel(player, level);
    }
}
