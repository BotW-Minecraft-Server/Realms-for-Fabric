package link.botwmcs.samchai.realms.capability;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;

public interface IAccount extends PlayerComponent {
    String getProfession();
    String getTown();
    int getLevel();

    void setProfession(String profession);
    void setTown(String town);
    void setLevel(int level);
}
