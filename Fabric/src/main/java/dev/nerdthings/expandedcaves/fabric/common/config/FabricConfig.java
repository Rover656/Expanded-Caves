package dev.nerdthings.expandedcaves.fabric.common.config;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.config.ConfigDefaults;
import dev.nerdthings.expandedcaves.common.config.ModConfig;
import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;

import java.io.*;
import java.nio.file.*;

public class FabricConfig {
    private static class OreFeatureConfig implements ModConfig.OreFeatureConfig {

        public final PropertyMirror<Boolean> ENABLED = PropertyMirror.create(ConfigTypes.BOOLEAN);
        public final PropertyMirror<Integer> VEIN_SIZE = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> COUNT = PropertyMirror.create(ConfigTypes.INTEGER);

        public OreFeatureConfig(String oreName, ConfigDefaults.Ores ore, ConfigTreeBuilder builder) {
            builder.fork(oreName)

                .beginValue("enabled", ConfigTypes.BOOLEAN, true)
                .withComment(ore.getEnabledComment())
                .finishValue(ENABLED::mirror)

                .beginValue("veinSize", ConfigTypes.INTEGER.withMinimum(0).withMaximum(64), ore.veinSize)
                .withComment(ore.getVeinSizeComment())
                .finishValue(VEIN_SIZE::mirror)

                .beginValue("count", ConfigTypes.INTEGER.withMinimum(0).withMaximum(64), ore.count)
                .withComment(ore.getCountComment())
                .finishValue(COUNT::mirror)

                .finishBranch();
        }

        @Override
        public boolean enabled() {
            return ENABLED.getValue();
        }

        @Override
        public int veinSize() {
            return VEIN_SIZE.getValue();
        }

        @Override
        public int count() {
            return COUNT.getValue();
        }
    }

    private static class PatchFeatureConfig implements ModConfig.PatchFeatureConfig {

        public final PropertyMirror<Boolean> ENABLED = PropertyMirror.create(ConfigTypes.BOOLEAN);
        public final PropertyMirror<Integer> COUNT = PropertyMirror.create(ConfigTypes.INTEGER);

        public PatchFeatureConfig(String patchName, ConfigDefaults.Patches patch, ConfigTreeBuilder builder) {
            builder.fork(patchName)

                .beginValue("enabled", ConfigTypes.BOOLEAN, true)
                .withComment(patch.getEnabledComment())
                .finishValue(ENABLED::mirror)

                .beginValue("count", ConfigTypes.INTEGER.withMinimum(0).withMaximum(64), patch.count)
                .withComment(patch.getCountComment())
                .finishValue(COUNT::mirror)

                .finishBranch();
        }

        @Override
        public boolean enabled() {
            return ENABLED.getValue();
        }

        @Override
        public int count() {
            return COUNT.getValue();
        }
    }

    private static class Common implements ModConfig.CommonConfig {

        public OreFeatureConfig ORE_BROKEN_STONE;
        public OreFeatureConfig ORE_BROKEN_DEEPSLATE;
        public OreFeatureConfig ORE_SEDIMENT_STONE;
        public OreFeatureConfig ORE_LAVASTONE;
        public OreFeatureConfig ORE_DIRTSTONE;
        public OreFeatureConfig ORE_MARLSTONE;
        public OreFeatureConfig ORE_PACKED_ICE;

        public final PatchFeatureConfig FLINT_PEBBLE;
        public final PatchFeatureConfig PEBBLES;
        public final PatchFeatureConfig SURFACE_PEBBLES;
        public final PatchFeatureConfig ROCKPILES;
        public final PatchFeatureConfig STALAGMITES;
        public final PatchFeatureConfig STALACTITES;

        public final PropertyMirror<Boolean> MUSHROOMS_ENABLED = PropertyMirror.create(ConfigTypes.BOOLEAN);
        public final PropertyMirror<Integer> SWEETSHROOM_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> GOLDISHROOM_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> SHINYSHROOM_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> LUMISHROOM_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> FLUOSHROOM_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> ROCKSHROOM_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);

        public final PropertyMirror<Boolean> MOSS_ENABLED = PropertyMirror.create(ConfigTypes.BOOLEAN);
        public final PropertyMirror<Integer> DRY_MOSS_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> FIRE_MOSS_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> FROZEN_MOSS_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> HANGING_ROOTS_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);
        public final PropertyMirror<Integer> HUMID_MOSS_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);

        public final PropertyMirror<Boolean> VINES_ENABLED = PropertyMirror.create(ConfigTypes.BOOLEAN);
        public final PropertyMirror<Integer> CAVE_VINE_COUNT = PropertyMirror.create(ConfigTypes.INTEGER);

        private final ConfigTree configTree;

        public Common(ConfigTreeBuilder builder) {
            builder = builder.fork("ore_features");
            ORE_BROKEN_STONE = new OreFeatureConfig("broken_stone", ConfigDefaults.Ores.BROKEN_STONE, builder);
            ORE_BROKEN_DEEPSLATE = new OreFeatureConfig("broken_deepslate", ConfigDefaults.Ores.BROKEN_DEEPSLATE, builder);
            ORE_SEDIMENT_STONE = new OreFeatureConfig("sediment_stone", ConfigDefaults.Ores.SEDIMENT_STONE, builder);
            ORE_LAVASTONE = new OreFeatureConfig("lavastone", ConfigDefaults.Ores.LAVASTONE, builder);
            ORE_DIRTSTONE = new OreFeatureConfig("dirtstone", ConfigDefaults.Ores.DIRTSTONE, builder);
            ORE_MARLSTONE = new OreFeatureConfig("marlstone", ConfigDefaults.Ores.MARLSTONE, builder);
            ORE_PACKED_ICE = new OreFeatureConfig("packed_ice", ConfigDefaults.Ores.PACKED_ICE, builder);
            builder = builder.finishBranch();

            builder = builder.fork("rock_features");
            FLINT_PEBBLE = new PatchFeatureConfig("flint_pebbles", ConfigDefaults.Patches.FLINT_PEBBLE, builder);
            PEBBLES = new PatchFeatureConfig("pebbles", ConfigDefaults.Patches.PEBBLES, builder);
            SURFACE_PEBBLES = new PatchFeatureConfig("surface_pebbles", ConfigDefaults.Patches.SURFACE_PEBBLES, builder);
            ROCKPILES = new PatchFeatureConfig("rockpiles", ConfigDefaults.Patches.ROCKPILES, builder);
            STALAGMITES = new PatchFeatureConfig("stalagmites", ConfigDefaults.Patches.STALAGMITES, builder);
            STALACTITES = new PatchFeatureConfig("stalactites", ConfigDefaults.Patches.STALACTITES, builder);
            builder = builder.finishBranch();

            builder = builder.fork("mushrooms");
            builder.beginValue("enabled", ConfigTypes.BOOLEAN, true).withComment("Whether mushrooms can generate.").finishValue(MUSHROOMS_ENABLED::mirror);
            commonPatch(builder, "sweetshroom_count", ConfigDefaults.Patches.SWEETSHROOM, SWEETSHROOM_COUNT);
            commonPatch(builder, "goldishroom_count", ConfigDefaults.Patches.GOLDISHROOM, GOLDISHROOM_COUNT);
            commonPatch(builder, "shinyshroom_count", ConfigDefaults.Patches.SHINYSHROOM, SHINYSHROOM_COUNT);
            commonPatch(builder, "lumishroom_count", ConfigDefaults.Patches.LUMISHROOM, LUMISHROOM_COUNT);
            commonPatch(builder, "fluoshroom_count", ConfigDefaults.Patches.FLUOSHROOM, FLUOSHROOM_COUNT);
            commonPatch(builder, "rockshroom_count", ConfigDefaults.Patches.ROCKSHROOM, ROCKSHROOM_COUNT);
            builder = builder.finishBranch();

            builder = builder.fork("moss");
            builder.beginValue("enabled", ConfigTypes.BOOLEAN, true).withComment("Whether moss can generate.").finishValue(MOSS_ENABLED::mirror);
            commonPatch(builder, "dry_moss_count", ConfigDefaults.Patches.DRY_MOSS, DRY_MOSS_COUNT);
            commonPatch(builder, "fire_moss_count", ConfigDefaults.Patches.FIRE_MOSS, FIRE_MOSS_COUNT);
            commonPatch(builder, "frozen_moss_count", ConfigDefaults.Patches.FROZEN_MOSS, FROZEN_MOSS_COUNT);
            commonPatch(builder, "hanging_roots_count", ConfigDefaults.Patches.HANGING_ROOTS, HANGING_ROOTS_COUNT);
            commonPatch(builder, "humid_moss_count", ConfigDefaults.Patches.HUMID_MOSS, HUMID_MOSS_COUNT);
            builder = builder.finishBranch();

            builder = builder.fork("vines");
            builder.beginValue("enabled", ConfigTypes.BOOLEAN, true).withComment("Whether vines can generate.").finishValue(VINES_ENABLED::mirror);
            commonPatch(builder, "cave_vine_count", ConfigDefaults.Patches.CAVE_VINE, CAVE_VINE_COUNT);
            builder = builder.finishBranch();

            configTree = builder.build();
        }

        private void commonPatch(ConfigTreeBuilder builder, String name, ConfigDefaults.Patches patch, PropertyMirror<Integer> mirror) {
            builder.beginValue(name, ConfigTypes.INTEGER.withMinimum(0).withMaximum(64), patch.count).withComment(patch.getCountComment()).finishValue(mirror::mirror);
        }

        public ConfigTree getConfigTree() {
            return configTree;
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
            return MUSHROOMS_ENABLED.getValue();
        }

        @Override
        public int sweetshroomCount() {
            return SWEETSHROOM_COUNT.getValue();
        }

        @Override
        public int goldishroomCount() {
            return GOLDISHROOM_COUNT.getValue();
        }

        @Override
        public int shinyshroomCount() {
            return SHINYSHROOM_COUNT.getValue();
        }

        @Override
        public int lumishroomCount() {
            return LUMISHROOM_COUNT.getValue();
        }

        @Override
        public int fluoshroomCount() {
            return FLUOSHROOM_COUNT.getValue();
        }

        @Override
        public int rockshroomCount() {
            return ROCKSHROOM_COUNT.getValue();
        }

        @Override
        public boolean mossEnabled() {
            return MOSS_ENABLED.getValue();
        }

        @Override
        public boolean vinesEnabled() {
            return VINES_ENABLED.getValue();
        }

        @Override
        public int dryMossCount() {
            return DRY_MOSS_COUNT.getValue();
        }

        @Override
        public int fireMossCount() {
            return FIRE_MOSS_COUNT.getValue();
        }

        @Override
        public int frozenMossCount() {
            return FROZEN_MOSS_COUNT.getValue();
        }

        @Override
        public int hangingRootsCount() {
            return HANGING_ROOTS_COUNT.getValue();
        }

        @Override
        public int humidMossCount() {
            return HUMID_MOSS_COUNT.getValue();
        }

        @Override
        public int caveVineCount() {
            return CAVE_VINE_COUNT.getValue();
        }
    }

    private static final Common COMMON = new Common(ConfigTree.builder());

    // Thanks botania
    // TODO: Properly license/reference Botania reference code.
    private static void writeDefaultConfig(ConfigTree config, Path path, JanksonValueSerializer serializer) {
        try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
            FiberSerialization.serialize(config, s, serializer);
        } catch (FileAlreadyExistsException ignored) {} catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupConfig(ConfigTree config, Path p, JanksonValueSerializer serializer) {
        writeDefaultConfig(config, p, serializer);

        try (InputStream s = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
            FiberSerialization.deserialize(config, s, serializer);
        } catch (IOException | ValueDeserializationException e) {
            e.printStackTrace();
        }
    }

    public static void setup() {
        try {
            Files.createDirectories(Paths.get("config"));
        } catch (FileAlreadyExistsException ignore) {}
        catch (IOException e) {
            e.printStackTrace();
        }

        JanksonValueSerializer serializer = new JanksonValueSerializer(false);
        setupConfig(COMMON.getConfigTree(), Paths.get("config", Constants.MOD_ID + "-common.json5"), serializer);
        ModConfig.setCommon(COMMON);
    }
}
