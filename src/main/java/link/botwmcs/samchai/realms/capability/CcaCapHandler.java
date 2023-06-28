package link.botwmcs.samchai.realms.capability;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import link.botwmcs.samchai.realms.Realms;
import net.minecraft.resources.ResourceLocation;

public class CcaCapHandler implements EntityComponentInitializer {
    public static final ComponentKey<IAccount> ACCOUNT =
            ComponentRegistry.getOrCreate(new ResourceLocation(Realms.MODID, "player_data"), IAccount.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ACCOUNT, Account::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
