package link.botwmcs.samchai.realms.config;

import link.botwmcs.samchai.realms.Realms;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Realms.MODID)
public class RealmsCommonConfig implements ConfigData {
    public boolean enable_single_player_function = false;
    public boolean enable_xp_economy = true;
    public boolean enable_ecohelper_hook = true;
}
