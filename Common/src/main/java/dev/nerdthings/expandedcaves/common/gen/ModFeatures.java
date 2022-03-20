package dev.nerdthings.expandedcaves.common.gen;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.gen.feature.TallSpelothemFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

import java.util.function.BiConsumer;

public class ModFeatures {

    public static final TallSpelothemFeature TALL_SPELOTHEM = new TallSpelothemFeature(SimpleBlockConfiguration.CODEC);

    public static void registerFeatures(BiConsumer<Feature<?>, ResourceLocation> registerFeature) {
        registerFeature.accept(TALL_SPELOTHEM, Constants.loc("tall_spelothem"));
    }

}
