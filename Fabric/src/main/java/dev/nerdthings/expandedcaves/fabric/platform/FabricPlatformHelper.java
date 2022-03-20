package dev.nerdthings.expandedcaves.fabric.platform;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricPlatformHelper implements IPlatformHelper {

    private static final CreativeModeTab TAB = FabricItemGroupBuilder.build(Constants.loc(Constants.MOD_ID), () -> new ItemStack(ModBlocks.LUMISHROOM));

    private static final TagKey<Item> IRON_INGOTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", "iron_ingots"));

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
        return new Item.Properties().tab(TAB);
    }

    @Override
    public TagKey<Item> ironIngotTag() {
        return IRON_INGOTS;
    }
}
