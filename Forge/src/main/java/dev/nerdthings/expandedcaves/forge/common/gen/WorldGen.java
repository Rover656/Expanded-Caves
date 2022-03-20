package dev.nerdthings.expandedcaves.forge.common.gen;

import dev.nerdthings.expandedcaves.common.gen.ModWorldGen;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldGen {
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if (!ModWorldGen.INVALID_BIOMES.contains(event.getCategory())) {
            // Add features
            ModWorldGen.addOverworldFeatures(generation::addFeature);

            if (ModWorldGen.IN_WET.contains(event.getCategory())) {
                ModWorldGen.addOverworldSurfaceBeachFeatures(generation::addFeature);
            }

            if (ModWorldGen.IN_COLD.contains(event.getCategory())) {
                ModWorldGen.addOverworldColdFeatures(generation::addFeature);
            }

            if (ModWorldGen.IN_HUMID.contains(event.getCategory())) {
                ModWorldGen.addOverworldHumidFeatures(generation::addFeature);
            }

            if (ModWorldGen.IN_DRY.contains(event.getCategory())) {
                ModWorldGen.addOverworldDryFeatures(generation::addFeature);
            }
        }
    }
}
