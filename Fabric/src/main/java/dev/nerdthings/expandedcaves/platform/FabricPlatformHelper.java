package dev.nerdthings.expandedcaves.platform;

import dev.nerdthings.expandedcaves.fabric.common.ExpandedCaves;
import dev.nerdthings.expandedcaves.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Item.Properties defaultItemBuilder() {
        return new Item.Properties().tab(ExpandedCaves.TAB);
    }
}
