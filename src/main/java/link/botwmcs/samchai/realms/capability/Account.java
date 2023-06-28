package link.botwmcs.samchai.realms.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class Account implements IAccount {

    private String profession;
    private String town;
    private int level;

    public Account(Player player) {
        this(player, "default", "default", 0);
    }

    public Account(Player player, String profession, String town, int level) {
        this.profession = profession;
        this.town = town;
        this.level = level;
    }

    @Override
    public String getProfession() {
        return profession;
    }

    @Override
    public String getTown() {
        return town;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        profession = tag.getString("profession");
        town = tag.getString("town");
        level = tag.getInt("level");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putString("profession", profession);
        tag.putString("town", town);
        tag.putInt("level", level);
    }
}
