package dev.nerdthings.expandedcaves.forge.common.config;

import dev.nerdthings.expandedcaves.common.config.ConfigDefaults;
import dev.nerdthings.expandedcaves.common.config.ModConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeConfig {
    private static class OreFeatureConfig implements ModConfig.OreFeatureConfig {

        public ForgeConfigSpec.BooleanValue ENABLED;
        public ForgeConfigSpec.IntValue VEIN_SIZE;
        public ForgeConfigSpec.IntValue COUNT;

        public OreFeatureConfig(String oreName, ConfigDefaults.Ores ore, ForgeConfigSpec.Builder builder) {
            builder.push(oreName);
            ENABLED = builder.comment(ore.getEnabledComment()).define("enabled", true);
            VEIN_SIZE = builder.comment(ore.getVeinSizeComment()).defineInRange("veinSize", ore.veinSize, 0, 64); // TODO: Proper ranges.
            COUNT = builder.comment(ore.getCountComment()).defineInRange("count", ore.count, 0, 64);
            builder.pop();
        }

        @Override
        public boolean enabled() {
            return ENABLED.get();
        }

        @Override
        public int veinSize() {
            return VEIN_SIZE.get();
        }

        @Override
        public int count() {
            return COUNT.get();
        }
    }

    private static class PatchFeatureConfig implements ModConfig.PatchFeatureConfig {

        public ForgeConfigSpec.BooleanValue ENABLED;
        public ForgeConfigSpec.IntValue COUNT;

        public PatchFeatureConfig(String patchName, ConfigDefaults.Patches patch, ForgeConfigSpec.Builder builder) {
            // TODO: Better comments
            builder.push(patchName);
            ENABLED = builder.comment(patch.getEnabledComment()).define("enabled", true);
            COUNT = builder.comment(patch.getCountComment()).defineInRange("count", patch.count, 1, 50);
            builder.pop();
        }

        @Override
        public boolean enabled() {
            return ENABLED.get();
        }

        @Override
        public int count() {
            return COUNT.get();
        }
    }

    private static class Common implements ModConfig.CommonConfig {

        public final OreFeatureConfig ORE_BROKEN_STONE;
        public final OreFeatureConfig ORE_BROKEN_DEEPSLATE;
        public final OreFeatureConfig ORE_SEDIMENT_STONE;
        public final OreFeatureConfig ORE_LAVASTONE;
        public final OreFeatureConfig ORE_DIRTSTONE;
        public final OreFeatureConfig ORE_MARLSTONE;
        public final OreFeatureConfig ORE_PACKED_ICE;

        public final PatchFeatureConfig FLINT_PEBBLE;
        public final PatchFeatureConfig PEBBLES;
        public final PatchFeatureConfig SURFACE_PEBBLES;
        public final PatchFeatureConfig ROCKPILES;
        public final PatchFeatureConfig STALAGMITES;
        public final PatchFeatureConfig STALACTITES;

        public final ForgeConfigSpec.BooleanValue MUSHROOMS_ENABLED;
        public final ForgeConfigSpec.BooleanValue MOSSES_ENABLED;
        public final ForgeConfigSpec.BooleanValue VINES_ENABLED;

        public final ForgeConfigSpec.IntValue SWEETSHROOM_COUNT;
        public final ForgeConfigSpec.IntValue GOLDISHROOM_COUNT;
        public final ForgeConfigSpec.IntValue SHINYSHROOM_COUNT;
        public final ForgeConfigSpec.IntValue LUMISHROOM_COUNT;
        public final ForgeConfigSpec.IntValue FLUOSHROOM_COUNT;
        public final ForgeConfigSpec.IntValue ROCKSHROOM_COUNT;

        public final ForgeConfigSpec.IntValue DRY_MOSS_COUNT;
        public final ForgeConfigSpec.IntValue FIRE_MOSS_COUNT;
        public final ForgeConfigSpec.IntValue FROZEN_MOSS_COUNT;
        public final ForgeConfigSpec.IntValue HANGING_ROOTS_COUNT;
        public final ForgeConfigSpec.IntValue HUMID_MOSS_COUNT;

        public final ForgeConfigSpec.IntValue CAVE_VINE_COUNT;

        public Common(ForgeConfigSpec.Builder builder) {
            // TODO: Review all defaults.
            // TODO: Store defaults in common.ConfigConstants.
            builder.push("oreFeatures");
            ORE_BROKEN_STONE = new OreFeatureConfig("brokenStone", ConfigDefaults.Ores.BROKEN_STONE, builder);
            ORE_BROKEN_DEEPSLATE = new OreFeatureConfig("brokenDeepslate", ConfigDefaults.Ores.BROKEN_DEEPSLATE, builder);
            ORE_SEDIMENT_STONE = new OreFeatureConfig("sedimentStone", ConfigDefaults.Ores.SEDIMENT_STONE, builder);
            ORE_LAVASTONE = new OreFeatureConfig("lavastone", ConfigDefaults.Ores.LAVASTONE, builder);
            ORE_DIRTSTONE = new OreFeatureConfig("dirtstone", ConfigDefaults.Ores.DIRTSTONE, builder);
            ORE_MARLSTONE = new OreFeatureConfig("marlstone", ConfigDefaults.Ores.MARLSTONE, builder);
            ORE_PACKED_ICE = new OreFeatureConfig("packedIce", ConfigDefaults.Ores.PACKED_ICE, builder);
            builder.pop();

            builder.push("rockFeatures");
            FLINT_PEBBLE = new PatchFeatureConfig("flintPebbles", ConfigDefaults.Patches.FLINT_PEBBLE, builder);
            PEBBLES = new PatchFeatureConfig("pebbles", ConfigDefaults.Patches.PEBBLES, builder);
            SURFACE_PEBBLES = new PatchFeatureConfig("surface_pebbles", ConfigDefaults.Patches.SURFACE_PEBBLES, builder);
            ROCKPILES = new PatchFeatureConfig("rockpiles", ConfigDefaults.Patches.ROCKPILES, builder);
            STALAGMITES = new PatchFeatureConfig("stalagmites", ConfigDefaults.Patches.STALAGMITES, builder);
            STALACTITES = new PatchFeatureConfig("stalactites", ConfigDefaults.Patches.STALACTITES, builder);
            builder.pop();

            builder.push("mushrooms");
            MUSHROOMS_ENABLED = builder.comment("Whether mushrooms can generate.").define("enabled", true);
            SWEETSHROOM_COUNT = commonPatch(builder, "sweetshroomCount", ConfigDefaults.Patches.SWEETSHROOM);
            GOLDISHROOM_COUNT = commonPatch(builder, "goldishroomCount", ConfigDefaults.Patches.GOLDISHROOM);
            SHINYSHROOM_COUNT = commonPatch(builder, "shinyshroomCount", ConfigDefaults.Patches.SHINYSHROOM);
            LUMISHROOM_COUNT = commonPatch(builder, "lumishroomCount", ConfigDefaults.Patches.LUMISHROOM);
            FLUOSHROOM_COUNT = commonPatch(builder, "fluoshroomCount", ConfigDefaults.Patches.FLUOSHROOM);
            ROCKSHROOM_COUNT = commonPatch(builder, "rockshroomCount", ConfigDefaults.Patches.ROCKSHROOM);
            builder.pop();

            builder.push("moss");
            MOSSES_ENABLED = builder.comment("Whether moss can generate.").define("enabled", true);

            DRY_MOSS_COUNT = commonPatch(builder, "dryMossCount", ConfigDefaults.Patches.DRY_MOSS);
            FIRE_MOSS_COUNT = commonPatch(builder, "fireMossCount", ConfigDefaults.Patches.FIRE_MOSS);
            FROZEN_MOSS_COUNT = commonPatch(builder, "frozenMossCount", ConfigDefaults.Patches.FROZEN_MOSS);
            HANGING_ROOTS_COUNT = commonPatch(builder, "hangingRootsCount", ConfigDefaults.Patches.HANGING_ROOTS);
            HUMID_MOSS_COUNT = commonPatch(builder, "humidMossCount", ConfigDefaults.Patches.HUMID_MOSS);
            builder.pop();

            builder.push("vines");
            VINES_ENABLED = builder.comment("Whether vines can generate.").define("enabled", true);
            CAVE_VINE_COUNT = commonPatch(builder, "caveVineCount", ConfigDefaults.Patches.CAVE_VINE);
            builder.pop();

            builder.push("structures");
            builder.pop();
        }

        private ForgeConfigSpec.IntValue commonPatch(ForgeConfigSpec.Builder builder, String path, ConfigDefaults.Patches patch) {
            return builder.comment(patch.getCountComment()).defineInRange(path, patch.count, 0, 64);
        }

        @Override
        public ModConfig.OreFeatureConfig oreBrokenStone() {
            return ORE_BROKEN_STONE;
        }

        @Override
        public ModConfig.OreFeatureConfig oreBrokenDeepslate() {
            return ORE_BROKEN_DEEPSLATE;
        }

        @Override
        public ModConfig.OreFeatureConfig oreSedimentStone() {
            return ORE_SEDIMENT_STONE;
        }

        @Override
        public ModConfig.OreFeatureConfig oreLavastone() {
            return ORE_LAVASTONE;
        }

        @Override
        public ModConfig.OreFeatureConfig oreDirtstone() {
            return ORE_DIRTSTONE;
        }

        @Override
        public ModConfig.OreFeatureConfig oreMarlstone() {
            return ORE_MARLSTONE;
        }

        @Override
        public ModConfig.OreFeatureConfig orePackedIce() {
            return ORE_PACKED_ICE;
        }

        @Override
        public ModConfig.PatchFeatureConfig flintPebble() {
            return FLINT_PEBBLE;
        }

        @Override
        public ModConfig.PatchFeatureConfig pebbles() {
            return PEBBLES;
        }

        @Override
        public ModConfig.PatchFeatureConfig surfacePebbles() {
            return SURFACE_PEBBLES;
        }

        @Override
        public ModConfig.PatchFeatureConfig rockPiles() {
            return ROCKPILES;
        }

        @Override
        public ModConfig.PatchFeatureConfig stalagmites() {
            return STALAGMITES;
        }

        @Override
        public ModConfig.PatchFeatureConfig stalactites() {
            return STALACTITES;
        }

        @Override
        public boolean mushroomsEnabled() {
            return MUSHROOMS_ENABLED.get();
        }

        @Override
        public int sweetshroomCount() {
            return SWEETSHROOM_COUNT.get();
        }

        @Override
        public int goldishroomCount() {
            return GOLDISHROOM_COUNT.get();
        }

        @Override
        public int shinyshroomCount() {
            return SHINYSHROOM_COUNT.get();
        }

        @Override
        public int lumishroomCount() {
            return LUMISHROOM_COUNT.get();
        }

        @Override
        public int fluoshroomCount() {
            return FLUOSHROOM_COUNT.get();
        }

        @Override
        public int rockshroomCount() {
            return ROCKSHROOM_COUNT.get();
        }

        @Override
        public boolean mossEnabled() {
            return MOSSES_ENABLED.get();
        }

        @Override
        public boolean vinesEnabled() {
            return VINES_ENABLED.get();
        }

        @Override
        public int dryMossCount() {
            return DRY_MOSS_COUNT.get();
        }

        @Override
        public int fireMossCount() {
            return FIRE_MOSS_COUNT.get();
        }

        @Override
        public int frozenMossCount() {
            return FROZEN_MOSS_COUNT.get();
        }

        @Override
        public int hangingRootsCount() {
            return HANGING_ROOTS_COUNT.get();
        }

        @Override
        public int humidMossCount() {
            return HUMID_MOSS_COUNT.get();
        }

        @Override
        public int caveVineCount() {
            return CAVE_VINE_COUNT.get();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();

        ModConfig.setCommon(commonSpecPair.getLeft());
    }
}
