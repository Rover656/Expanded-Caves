package dev.nerdthings.expandedcaves.fabric.common;

import dev.nerdthings.expandedcaves.CommonClass;
import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.gen.ModFeatures;
import dev.nerdthings.expandedcaves.fabric.common.config.FabricConfig;
import dev.nerdthings.expandedcaves.fabric.common.gen.WorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;

public class ExpandedCaves implements ModInitializer {

    public static final CreativeModeTab TAB = FabricItemGroupBuilder.build(Constants.loc(Constants.MOD_ID), () -> new ItemStack(ModBlocks.LUMISHROOM));
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Run first so its ready.
        FabricConfig.setup();

        ModBlocks.registerBlocks(bind(Registry.BLOCK));
        ModBlocks.registerBlockItems(bind(Registry.ITEM));
        ModFeatures.registerFeatures(bind(Registry.FEATURE));

        WorldGen.createModifications();
    }

    // Thanks again botania
    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
