package link.botwmcs.samchai.realms.capability;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class Account implements IAccount, AutoSyncedComponent {

    private String profession;
    private String town;
    private int level;

    private final Object provider;

    public Account(Object provider) {
        this.provider = provider;
    }

//    public Account(Player player) {
//        this(player, "default", "default", 0);
//    }
//
//    public Account(Player player, String profession, String town, int level) {
//        this.profession = profession;
//        this.town = town;
//        this.level = level;
//    }

    @Override
    public String getProfession() {
        if (profession == null) {
            return "default";
        }
        return profession;
    }

    @Override
    public String getTown() {
        if (town == null) {
            return "default";
        }
        return town;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setProfession(String profession) {
        this.profession = profession;
        CcaCapHandler.ACCOUNT.sync(this.provider);
    }

    @Override
    public void setTown(String town) {
        this.town = town;
        CcaCapHandler.ACCOUNT.sync(this.provider);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        CcaCapHandler.ACCOUNT.sync(this.provider);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        profession = tag.getString("profession");
        town = tag.getString("town");
        level = tag.getInt("level");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        if (profession == null) {
            profession = "default";
        }
        if (town == null) {
            town = "default";
        }
        tag.putString("profession", profession);
        tag.putString("town", town);
        tag.putInt("level", level);
    }
}
