package dev.nerdthings.expandedcaves.platform;

import dev.nerdthings.expandedcaves.forge.common.ExpandedCaves;
import dev.nerdthings.expandedcaves.platform.services.IPlatformHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Item.Properties defaultItemBuilder() {
        return new Item.Properties().tab(ExpandedCaves.TAB);
    }
}