package dev.nerdthings.expandedcaves.fabric.data;

import dev.nerdthings.expandedcaves.data.AdvancementProvider;
import dev.nerdthings.expandedcaves.data.ModBlockTagProvider;
import dev.nerdthings.expandedcaves.data.ItemTagProvider;
import dev.nerdthings.expandedcaves.data.ModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;

public class FabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        if (System.getProperty("expcaves.common_datagen") != null) {
            initCommonDatagen(fabricDataGenerator);
        }
    }

    private static void initFabricDatagen(DataGenerator generator) {

    }

    private static void initCommonDatagen(DataGenerator generator) {
        ModBlockTagProvider blockTagProvider = new ModBlockTagProvider(generator);
        generator.addProvider(blockTagProvider);
        generator.addProvider(new ItemTagProvider(generator, blockTagProvider));
        generator.addProvider(new AdvancementProvider(generator));
        generator.addProvider(new ModelProvider(generator));
    }
}
