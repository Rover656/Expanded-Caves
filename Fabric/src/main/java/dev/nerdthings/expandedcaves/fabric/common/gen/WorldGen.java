package dev.nerdthings.expandedcaves.fabric.common.gen;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.gen.ModWorldGen;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.BiConsumer;

public class WorldGen {
    public static void createModifications() {
        ModWorldGen.init();

        BiomeModifications.create(Constants.loc(Constants.MOD_ID))
            .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), context -> {
                ModWorldGen.addOverworldFeatures(bind(context.getGenerationSettings()));
            })
            .add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(ModWorldGen.IN_HUMID.toArray(new Biome.BiomeCategory[0])), context -> {
                ModWorldGen.addOverworldHumidFeatures(bind(context.getGenerationSettings()));
            })
            .add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(ModWorldGen.IN_DRY.toArray(new Biome.BiomeCategory[0])), context -> {
                ModWorldGen.addOverworldDryFeatures(bind(context.getGenerationSettings()));
            })
            .add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(ModWorldGen.IN_COLD.toArray(new Biome.BiomeCategory[0])), context -> {
                ModWorldGen.addOverworldColdFeatures(bind(context.getGenerationSettings()));
            })
            .add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(ModWorldGen.IN_WET.toArray(new Biome.BiomeCategory[0])), context -> {
                ModWorldGen.addOverworldSurfaceBeachFeatures(bind(context.getGenerationSettings()));
            });
    }

    private static BiConsumer<GenerationStep.Decoration, Holder<PlacedFeature>> bind(BiomeModificationContext.GenerationSettingsContext context) {
        return (decoration, feature) -> context.addFeature(decoration, feature.unwrapKey().orElseThrow());
    }
}
