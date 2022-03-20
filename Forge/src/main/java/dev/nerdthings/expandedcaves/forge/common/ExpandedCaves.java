package dev.nerdthings.expandedcaves.forge.common;

import dev.nerdthings.expandedcaves.CommonClass;
import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.gen.ModFeatures;
import dev.nerdthings.expandedcaves.forge.common.config.ForgeConfig;
import dev.nerdthings.expandedcaves.forge.common.gen.WorldGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class ExpandedCaves {

    public static CreativeModeTab TAB = new CreativeModeTab(Constants.MOD_ID + "." + Constants.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.LUMISHROOM);
        }
    };
    
    public ExpandedCaves() {
        // Setup config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.COMMON_SPEC, Constants.MOD_ID + "_common.toml");
    
        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
    
        bind(ForgeRegistries.BLOCKS, ModBlocks::registerBlocks);
        bind(ForgeRegistries.ITEMS, ModBlocks::registerBlockItems);
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