package dev.nerdthings.expandedcaves.forge.common;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.gen.ModFeatures;
import dev.nerdthings.expandedcaves.common.items.ModItems;
import dev.nerdthings.expandedcaves.data.ItemTagProvider;
import dev.nerdthings.expandedcaves.data.ModBlockTagProvider;
import dev.nerdthings.expandedcaves.forge.common.config.ForgeConfig;
import dev.nerdthings.expandedcaves.forge.common.gen.WorldGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class ForgeExpandedCaves {
    public ForgeExpandedCaves() {
        // Setup config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.COMMON_SPEC, Constants.MOD_ID + "_common.toml");

        bind(ForgeRegistries.BLOCKS, ModBlocks::registerBlocks);
        bind(ForgeRegistries.ITEMS, ModBlocks::registerBlockItems);
        bind(ForgeRegistries.ITEMS, ModItems::registerItems);
        bind(ForgeRegistries.FEATURES, ModFeatures::registerFeatures);

        // Add world gen features
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, WorldGen::onBiomeLoad);
    }

    // Thanks botania!
    private static <T extends IForgeRegistryEntry<T>> void bind(IForgeRegistry<T> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(registry.getRegistrySuperType(),
            (RegistryEvent.Register<T> event) -> {
                IForgeRegistry<T> forgeRegistry = event.getRegistry();
                source.accept((t, rl) -> {
                    t.setRegistryName(rl);
                    forgeRegistry.register(t);
                });
            });
    }
}