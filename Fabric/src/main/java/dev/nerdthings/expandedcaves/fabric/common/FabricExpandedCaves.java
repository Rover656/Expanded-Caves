package dev.nerdthings.expandedcaves.fabric.common;

import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.gen.ModFeatures;
import dev.nerdthings.expandedcaves.common.items.ModItems;
import dev.nerdthings.expandedcaves.fabric.common.config.FabricConfig;
import dev.nerdthings.expandedcaves.fabric.common.gen.WorldGen;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class FabricExpandedCaves implements ModInitializer {
    
    @Override
    public void onInitialize() {
        // Init config.
        FabricConfig.setup();

        // Registration
        ModBlocks.registerBlocks(bind(Registry.BLOCK));
        ModBlocks.registerBlockItems(bind(Registry.ITEM));
        ModItems.registerItems(bind(Registry.ITEM));
        ModFeatures.registerFeatures(bind(Registry.FEATURE));

        // World gen
        WorldGen.createModifications();
    }

    // Thanks again botania
    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
