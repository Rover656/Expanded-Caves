package dev.nerdthings.expandedcaves.common.config;

public class ModConfig {
    public interface OreFeatureConfig {
        boolean enabled();
        int veinSize();
        int count();
    }

    public interface PatchFeatureConfig {
        boolean enabled();
        int count();
    }

    public interface CommonConfig {
        // region Ores

        OreFeatureConfig oreBrokenStone();
        OreFeatureConfig oreBrokenDeepslate();
        OreFeatureConfig oreSedimentStone();
        OreFeatureConfig oreLavastone();
        OreFeatureConfig oreDirtstone();
        OreFeatureConfig oreMarlstone();
        OreFeatureConfig orePackedIce();

        // endregion

        // region Patches

        PatchFeatureConfig flintPebble();
        PatchFeatureConfig pebbles();
        PatchFeatureConfig surfacePebbles();
        PatchFeatureConfig rockPiles();
        PatchFeatureConfig stalagmites();
        PatchFeatureConfig stalactites();

        // endregion

        // region Mushrooms

        boolean mushroomsEnabled();
        int sweetshroomCount();
        int goldishroomCount();
        int shinyshroomCount();
        int lumishroomCount();
        int fluoshroomCount();
        int rockshroomCount();

        // endregion

        // region Moss/Vines

        boolean mossEnabled();
        boolean vinesEnabled();
        int dryMossCount();
        int fireMossCount();
        int frozenMossCount();
        int hangingRootsCount();
        int humidMossCount();
        int caveVineCount();

        // endregion
    }

    private static CommonConfig COMMON;

    public static CommonConfig common() {
        return COMMON;
    }

    public static void setCommon(CommonConfig common) {
        COMMON = common;
    }
}
