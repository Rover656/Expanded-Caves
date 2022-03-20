package dev.nerdthings.expandedcaves.common.gen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class ModWorldGen {

    // TODO: Spelothems and Pebbles are spawning on the wrong base blocks

    // Common
    private static final int SPREAD_I = 8;
    private static final int SPREAD_J = 16;
    private static final int SPREAD_K = 4;

    private static final List<Block> WHITELIST_STONE = ImmutableList.of(Blocks.STONE, ModBlocks.BROKEN_STONE);
    private static final List<Block> WHITELIST_ANDESITE = ImmutableList.of(Blocks.ANDESITE);
    private static final List<Block> WHITELIST_DIORITE = ImmutableList.of(Blocks.DIORITE);
    private static final List<Block> WHITELIST_GRANITE = ImmutableList.of(Blocks.GRANITE);
    private static final List<Block> WHITELIST_TUFF = ImmutableList.of(Blocks.TUFF);
    private static final List<Block> WHITELIST_DEEPSLATE = ImmutableList.of(Blocks.DEEPSLATE);
    private static final List<Block> WHITELIST_SEDIMENT_STONE = ImmutableList.of(ModBlocks.SEDIMENT_STONE);
    private static final List<Block> WHITELIST_LAVASTONE = ImmutableList.of(ModBlocks.LAVASTONE);
    private static final List<Block> WHITELIST_PACKED_ICE = ImmutableList.of(Blocks.PACKED_ICE);

    // region Configs

    // region Rocks

    private static final int FLINT_PEBBLE_TRIES = 8;
    private static final int STONE_PEBBLE_TRIES = 32;
    private static final int ANDESITE_PEBBLE_TRIES = 32;
    private static final int DIORITE_PEBBLE_TRIES = 32;
    private static final int GRANITE_PEBBLE_TRIES = 32;
    private static final int TUFF_PEBBLE_TRIES = 32;
    private static final int DEEPSLATE_PEBBLE_TRIES = 32;
    private static final int SEDIMENT_STONE_PEBBLE_TRIES = 32;
    private static final int LAVASTONE_PEBBLE_TRIES = 32;

    private static final int SMALL_STONE_PILE_TRIES = 32;
    private static final int SMALL_ANDESITE_PILE_TRIES = 32;
    private static final int SMALL_DIORITE_PILE_TRIES = 32;
    private static final int SMALL_GRANITE_PILE_TRIES = 32;
    private static final int SMALL_TUFF_PILE_TRIES = 32;
    private static final int SMALL_DEEPSLATE_PILE_TRIES = 32;
    private static final int SMALL_SEDIMENT_STONE_PILE_TRIES = 32;
    private static final int SMALL_LAVASTONE_PILE_TRIES = 32;

    private static final int BIG_STONE_PILE_TRIES = 32;
    private static final int BIG_ANDESITE_PILE_TRIES = 32;
    private static final int BIG_DIORITE_PILE_TRIES = 32;
    private static final int BIG_GRANITE_PILE_TRIES = 32;
    private static final int BIG_TUFF_PILE_TRIES = 32;
    private static final int BIG_DEEPSLATE_PILE_TRIES = 32;
    private static final int BIG_SEDIMENT_STONE_PILE_TRIES = 32;
    private static final int BIG_LAVASTONE_PILE_TRIES = 32;

    private static final int PEBBLE_COUNT = ModConfig.common().pebbles().count();
    private static final int SURFACE_PEBBLE_COUNT = ModConfig.common().surfacePebbles().count();
    private static final int ROCKPILE_COUNT = ModConfig.common().rockPiles().count();

    // endregion

    // region Spelothems

    // TODO: CONFIG

    private static final int STALAGMITE_STONE_TRIES = 64;
    private static final int STALAGMITE_ANDESITE_TRIES = 64;
    private static final int STALAGMITE_DIORITE_TRIES = 64;
    private static final int STALAGMITE_GRANITE_TRIES = 64;
    private static final int STALAGMITE_TUFF_TRIES = 64;
    private static final int STALAGMITE_DEEPSLATE_TRIES = 64;
    private static final int STALAGMITE_SEDIMENT_STONE_TRIES = 32;
    private static final int STALAGMITE_LAVASTONE_TRIES = 32;
    private static final int STALAGMITE_PACKED_ICE_TRIES = 32;

    private static final int TALL_STALAGMITE_STONE_TRIES = 64;
    private static final int TALL_STALAGMITE_ANDESITE_TRIES = 64;
    private static final int TALL_STALAGMITE_DIORITE_TRIES = 64;
    private static final int TALL_STALAGMITE_GRANITE_TRIES = 64;
    private static final int TALL_STALAGMITE_TUFF_TRIES = 64;
    private static final int TALL_STALAGMITE_DEEPSLATE_TRIES = 64;
    private static final int TALL_STALAGMITE_SEDIMENT_STONE_TRIES = 32;
    private static final int TALL_STALAGMITE_LAVASTONE_TRIES = 32;
    private static final int TALL_STALAGMITE_PACKED_ICE_TRIES = 32;

    private static final int STALACTITE_STONE_TRIES = 64;
    private static final int STALACTITE_ANDESITE_TRIES = 64;
    private static final int STALACTITE_DIORITE_TRIES = 64;
    private static final int STALACTITE_GRANITE_TRIES = 64;
    private static final int STALACTITE_TUFF_TRIES = 64;
    private static final int STALACTITE_DEEPSLATE_TRIES = 64;
    private static final int STALACTITE_SEDIMENT_STONE_TRIES = 32;
    private static final int STALACTITE_LAVASTONE_TRIES = 32;
    private static final int STALACTITE_PACKED_ICE_TRIES = 32;

    private static final int TALL_STALACTITE_STONE_TRIES = 64;
    private static final int TALL_STALACTITE_ANDESITE_TRIES = 64;
    private static final int TALL_STALACTITE_DIORITE_TRIES = 64;
    private static final int TALL_STALACTITE_GRANITE_TRIES = 64;
    private static final int TALL_STALACTITE_TUFF_TRIES = 64;
    private static final int TALL_STALACTITE_DEEPSLATE_TRIES = 64;
    private static final int TALL_STALACTITE_SEDIMENT_STONE_TRIES = 32;
    private static final int TALL_STALACTITE_LAVASTONE_TRIES = 32;
    private static final int TALL_STALACTITE_PACKED_ICE_TRIES = 32;

    private static final int STALAGMITE_COUNT = ModConfig.common().stalagmites().count();
    private static final int STALACTITE_COUNT = ModConfig.common().stalactites().count();

    // endregion

    // region Mushrooms

    // TODO: Look at the rarity placement modifier sometime
    private static final int SWEETSHROOM_TRIES = 96;
    private static final ImmutableList<Block> SWEETSHROOM_BLACKLIST = ImmutableList.of(Blocks.PACKED_ICE, ModBlocks.DIRTSTONE, ModBlocks.MARLSTONE);
    private static final int GOLDISHROOM_TRIES = 96;
    private static final ImmutableList<Block> GOLDISHROOM_BLACKLIST = ImmutableList.of(Blocks.PACKED_ICE, ModBlocks.DIRTSTONE, ModBlocks.MARLSTONE);
    private static final int SHINYSHROOM_TRIES = 48;
    private static final ImmutableList<Block> SHINYSHROOM_BLACKLIST = ImmutableList.of(Blocks.PACKED_ICE, ModBlocks.DIRTSTONE, ModBlocks.MARLSTONE);
    private static final int LUMISHROOM_TRIES = 48;
    private static final ImmutableList<Block> LUMISHROOM_BLACKLIST = ImmutableList.of(Blocks.PACKED_ICE, ModBlocks.DIRTSTONE, ModBlocks.MARLSTONE);
    private static final int FLUOSHROOM_TRIES = 96;
    private static final ImmutableList<Block> FLUOSHROOM_BLACKLIST = ImmutableList.of(Blocks.DIRT, ModBlocks.DIRTSTONE);
    private static final int ROCKSHROOM_TRIES = 32;
    private static final ImmutableList<Block> ROCKSHROOM_BLACKLIST = ImmutableList.of(Blocks.PACKED_ICE, ModBlocks.DIRTSTONE, ModBlocks.MARLSTONE);

    // endregion

    // region Moss

    private static final int DRY_MOSS_TRIES = 8;

    private static final int FIRE_MOSS_TRIES = 24;
    private static final ImmutableList<Block> FIRE_MOSS_WHITELIST = ImmutableList.of(ModBlocks.LAVASTONE);

    private static final int FROZEN_MOSS_TRIES = 32;
    private static final ImmutableList<Block> FROZEN_MOSS_WHITELIST = ImmutableList.of(Blocks.PACKED_ICE);

    private static final int HANGING_ROOTS_TRIES = 8;

    private static final int HUMID_MOSS_GROUND_TRIES = 8;

    private static final int HUMID_MOSS_CEILING_TRIES = 32;
    private static final ImmutableList<Block> HUMID_MOSS_CEILING_WHITELIST = ImmutableList.of(ModBlocks.DIRTSTONE, Blocks.DIRT);

    private static final int CAVE_VINE_TRIES = 8;

    // endregion

    // endregion

    private static class Configured {

        // region Ores

        // @formatter:off

        private static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_BROKEN_STONE = ore("ore_broken_stone", OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.BROKEN_STONE.defaultBlockState(), ModConfig.common().oreBrokenStone());
        private static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_BROKEN_DEEPSLATE = ore("ore_broken_deepslate", OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.BROKEN_DEEPSLATE.defaultBlockState(), ModConfig.common().oreBrokenDeepslate());
        private static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SEDIMENT_STONE = ore("ore_sediment_stone", OreFeatures.NATURAL_STONE, ModBlocks.SEDIMENT_STONE.defaultBlockState(), ModConfig.common().oreSedimentStone());
        private static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_LAVASTONE = ore("ore_lavastone", OreFeatures.NATURAL_STONE, ModBlocks.LAVASTONE.defaultBlockState(), ModConfig.common().oreLavastone());
        private static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DIRTSTONE = ore("ore_dirtstone", OreFeatures.NATURAL_STONE, ModBlocks.DIRTSTONE.defaultBlockState(), ModConfig.common().oreDirtstone());
        private static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_MARLSTONE = ore("ore_marlstone", OreFeatures.NATURAL_STONE, ModBlocks.MARLSTONE.defaultBlockState(), ModConfig.common().oreMarlstone());
        private static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PACKED_ICE = ore("ore_packed_ice", OreFeatures.NATURAL_STONE, Blocks.PACKED_ICE.defaultBlockState(), ModConfig.common().orePackedIce());

        // @formatter:on

        private static Holder<ConfiguredFeature<OreConfiguration, ?>> ore(String name, RuleTest test, BlockState state, ModConfig.OreFeatureConfig config) {
            return FeatureUtils.register(Constants.MOD_ID + ":" + name, Feature.ORE, new OreConfiguration(test, state, config.veinSize()));
        }

        // endregion

        // region Rocks

        // @formatter:off

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLINT_PEBBLE = floorRandomPatch("flint_pebble", ModBlocks.PEBBLE_FLINT.defaultBlockState(), FLINT_PEBBLE_TRIES, SPREAD_I, SPREAD_I);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLINT_PEBBLE_SURFACE = floorRandomPatch("flint_pebble_surface", ModBlocks.PEBBLE_FLINT.defaultBlockState(), FLINT_PEBBLE_TRIES, SPREAD_J, SPREAD_J);

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STONE_PEBBLE = floorRandomPatchWhitelist("stone_pebble", ModBlocks.STONE_PEBBLES.getPebbleState(), STONE_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_STONE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ANDESITE_PEBBLE = floorRandomPatchWhitelist("andesite_pebble", ModBlocks.ANDESITE_PEBBLES.getPebbleState(), ANDESITE_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_ANDESITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DIORITE_PEBBLE = floorRandomPatchWhitelist("diorite_pebble", ModBlocks.DIORITE_PEBBLES.getPebbleState(), DIORITE_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_DIORITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> GRANITE_PEBBLE = floorRandomPatchWhitelist("granite_pebble", ModBlocks.GRANITE_PEBBLES.getPebbleState(), GRANITE_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_GRANITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TUFF_PEBBLE = floorRandomPatchWhitelist("tuff_pebble", ModBlocks.TUFF_PEBBLES.getPebbleState(), TUFF_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_TUFF);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DEEPSLATE_PEBBLE = floorRandomPatchWhitelist("deepslate_pebble", ModBlocks.DEEPSLATE_PEBBLES.getPebbleState(), DEEPSLATE_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_DEEPSLATE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SEDIMENT_STONE_PEBBLE = floorRandomPatchWhitelist("sediment_stone_pebble", ModBlocks.SEDIMENT_STONE_PEBBLES.getPebbleState(), SEDIMENT_STONE_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_SEDIMENT_STONE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> LAVASTONE_PEBBLE = floorRandomPatchWhitelist("lavastone_pebble", ModBlocks.LAVASTONE_PEBBLES.getPebbleState(), LAVASTONE_PEBBLE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_LAVASTONE);

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_STONE_PILE = floorRandomPatchWhitelist("small_stone_pile", ModBlocks.STONE_PEBBLES.getSmallPileState(), SMALL_STONE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_STONE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_ANDESITE_PILE = floorRandomPatchWhitelist("small_andesite_pile", ModBlocks.ANDESITE_PEBBLES.getSmallPileState(), SMALL_ANDESITE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_ANDESITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_DIORITE_PILE = floorRandomPatchWhitelist("small_diorite_pile", ModBlocks.DIORITE_PEBBLES.getSmallPileState(), SMALL_DIORITE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_DIORITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_GRANITE_PILE = floorRandomPatchWhitelist("small_granite_pile", ModBlocks.GRANITE_PEBBLES.getSmallPileState(), SMALL_GRANITE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_GRANITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_TUFF_PILE = floorRandomPatchWhitelist("small_tuff_pile", ModBlocks.TUFF_PEBBLES.getSmallPileState(), SMALL_TUFF_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_TUFF);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_DEEPSLATE_PILE = floorRandomPatchWhitelist("small_deepslate_pile", ModBlocks.DEEPSLATE_PEBBLES.getSmallPileState(), SMALL_DEEPSLATE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_DEEPSLATE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_SEDIMENT_STONE_PILE = floorRandomPatchWhitelist("small_sediment_stone_pile", ModBlocks.SEDIMENT_STONE_PEBBLES.getSmallPileState(), SMALL_SEDIMENT_STONE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_SEDIMENT_STONE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SMALL_LAVASTONE_PILE = floorRandomPatchWhitelist("small_lavastone_pile", ModBlocks.LAVASTONE_PEBBLES.getSmallPileState(), SMALL_LAVASTONE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_LAVASTONE);

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_STONE_PILE = floorRandomPatchWhitelist("big_stone_pile", ModBlocks.STONE_PEBBLES.getBigPileState(), BIG_STONE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_STONE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_ANDESITE_PILE = floorRandomPatchWhitelist("big_andesite_pile", ModBlocks.ANDESITE_PEBBLES.getBigPileState(), BIG_ANDESITE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_ANDESITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_DIORITE_PILE = floorRandomPatchWhitelist("big_diorite_pile", ModBlocks.DIORITE_PEBBLES.getBigPileState(), BIG_DIORITE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_DIORITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_GRANITE_PILE = floorRandomPatchWhitelist("big_granite_pile", ModBlocks.GRANITE_PEBBLES.getBigPileState(), BIG_GRANITE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_GRANITE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_TUFF_PILE = floorRandomPatchWhitelist("big_tuff_pile", ModBlocks.TUFF_PEBBLES.getBigPileState(), BIG_TUFF_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_TUFF);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_DEEPSLATE_PILE = floorRandomPatchWhitelist("big_deepslate_pile", ModBlocks.DEEPSLATE_PEBBLES.getBigPileState(), BIG_DEEPSLATE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_DEEPSLATE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_SEDIMENT_STONE_PILE = floorRandomPatchWhitelist("big_sediment_stone_pile", ModBlocks.SEDIMENT_STONE_PEBBLES.getBigPileState(), BIG_SEDIMENT_STONE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_SEDIMENT_STONE);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BIG_LAVASTONE_PILE = floorRandomPatchWhitelist("big_lavastone_pile", ModBlocks.LAVASTONE_PEBBLES.getBigPileState(), BIG_LAVASTONE_PILE_TRIES, SPREAD_J, SPREAD_J, WHITELIST_LAVASTONE);

        // @formatter:on

        // endregion

        // region Spelothems

        // @formatter:off

        // region Stalagmites

        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_STONE = floorRandomPatchWhitelist("stalagmite_stone", ModBlocks.STONE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_ANDESITE = floorRandomPatchWhitelist("stalagmite_andesite", ModBlocks.ANDESITE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_ANDESITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_ANDESITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_DIORITE = floorRandomPatchWhitelist("stalagmite_diorite", ModBlocks.DIORITE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_DIORITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DIORITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_GRANITE = floorRandomPatchWhitelist("stalagmite_granite", ModBlocks.GRANITE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_GRANITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_GRANITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_TUFF = floorRandomPatchWhitelist("stalagmite_tuff", ModBlocks.TUFF_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_TUFF_TRIES, SPREAD_I, SPREAD_I, WHITELIST_TUFF);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_DEEPSLATE = floorRandomPatchWhitelist("stalagmite_deepslate", ModBlocks.DEEPSLATE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_DEEPSLATE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DEEPSLATE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_SEDIMENT_STONE = floorRandomPatchWhitelist("stalagmite_sediment_stone", ModBlocks.SEDIMENT_STONE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_SEDIMENT_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_SEDIMENT_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_LAVASTONE = floorRandomPatchWhitelist("stalagmite_lavastone", ModBlocks.LAVASTONE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_LAVASTONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_LAVASTONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALAGMITE_PACKED_ICE = floorRandomPatchWhitelist("stalagmite_packed_ice", ModBlocks.PACKED_ICE_SPELOTHEMS.stalagmite.defaultBlockState(), STALAGMITE_PACKED_ICE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_PACKED_ICE);

        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_STONE = floorRandomPatchWhitelist("tall_stalagmite_stone", ModFeatures.TALL_SPELOTHEM, ModBlocks.STONE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_ANDESITE = floorRandomPatchWhitelist("tall_stalagmite_andesite", ModFeatures.TALL_SPELOTHEM, ModBlocks.ANDESITE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_ANDESITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_ANDESITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_DIORITE = floorRandomPatchWhitelist("tall_stalagmite_diorite", ModFeatures.TALL_SPELOTHEM, ModBlocks.DIORITE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_DIORITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DIORITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_GRANITE = floorRandomPatchWhitelist("tall_stalagmite_granite", ModFeatures.TALL_SPELOTHEM, ModBlocks.GRANITE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_GRANITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_GRANITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_TUFF = floorRandomPatchWhitelist("tall_stalagmite_tuff", ModFeatures.TALL_SPELOTHEM, ModBlocks.TUFF_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_TUFF_TRIES, SPREAD_I, SPREAD_I, WHITELIST_TUFF);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_DEEPSLATE = floorRandomPatchWhitelist("tall_stalagmite_deepslate", ModFeatures.TALL_SPELOTHEM, ModBlocks.DEEPSLATE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_DEEPSLATE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DEEPSLATE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_SEDIMENT_STONE = floorRandomPatchWhitelist("tall_stalagmite_sediment_stone", ModFeatures.TALL_SPELOTHEM, ModBlocks.SEDIMENT_STONE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_SEDIMENT_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_SEDIMENT_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_LAVASTONE = floorRandomPatchWhitelist("tall_stalagmite_lavastone", ModFeatures.TALL_SPELOTHEM, ModBlocks.LAVASTONE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_LAVASTONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_LAVASTONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALAGMITE_PACKED_ICE = floorRandomPatchWhitelist("tall_stalagmite_packed_ice", ModFeatures.TALL_SPELOTHEM, ModBlocks.PACKED_ICE_SPELOTHEMS.tallStalagmite.defaultBlockState(), TALL_STALAGMITE_PACKED_ICE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_PACKED_ICE);

        // endregion

        // region Stalactites

        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_STONE = ceilingRandomPatchWhitelist("stalactite_stone", ModBlocks.STONE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_ANDESITE = ceilingRandomPatchWhitelist("stalactite_andesite", ModBlocks.ANDESITE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_ANDESITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_ANDESITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_DIORITE = ceilingRandomPatchWhitelist("stalactite_diorite", ModBlocks.DIORITE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_DIORITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DIORITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_GRANITE = ceilingRandomPatchWhitelist("stalactite_granite", ModBlocks.GRANITE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_GRANITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_GRANITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_TUFF = ceilingRandomPatchWhitelist("stalactite_tuff", ModBlocks.TUFF_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_TUFF_TRIES, SPREAD_I, SPREAD_I, WHITELIST_TUFF);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_DEEPSLATE = ceilingRandomPatchWhitelist("stalactite_deepslate", ModBlocks.DEEPSLATE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_DEEPSLATE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DEEPSLATE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_SEDIMENT_STONE = ceilingRandomPatchWhitelist("stalactite_sediment_stone", ModBlocks.SEDIMENT_STONE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_SEDIMENT_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_SEDIMENT_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_LAVASTONE = ceilingRandomPatchWhitelist("stalactite_lavastone", ModBlocks.LAVASTONE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_LAVASTONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_LAVASTONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> STALACTITE_PACKED_ICE = ceilingRandomPatchWhitelist("stalactite_packed_ice", ModBlocks.PACKED_ICE_SPELOTHEMS.stalactite.defaultBlockState(), STALACTITE_PACKED_ICE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_PACKED_ICE);

        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_STONE = ceilingRandomPatchWhitelist("tall_stalactite_stone", ModFeatures.TALL_SPELOTHEM, ModBlocks.STONE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_ANDESITE = ceilingRandomPatchWhitelist("tall_stalactite_andesite", ModFeatures.TALL_SPELOTHEM, ModBlocks.ANDESITE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_ANDESITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_ANDESITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_DIORITE = ceilingRandomPatchWhitelist("tall_stalactite_diorite", ModFeatures.TALL_SPELOTHEM, ModBlocks.DIORITE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_DIORITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DIORITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_GRANITE = ceilingRandomPatchWhitelist("tall_stalactite_granite", ModFeatures.TALL_SPELOTHEM, ModBlocks.GRANITE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_GRANITE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_GRANITE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_TUFF = ceilingRandomPatchWhitelist("tall_stalactite_tuff", ModFeatures.TALL_SPELOTHEM, ModBlocks.TUFF_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_TUFF_TRIES, SPREAD_I, SPREAD_I, WHITELIST_TUFF);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_DEEPSLATE = ceilingRandomPatchWhitelist("tall_stalactite_deepslate", ModFeatures.TALL_SPELOTHEM, ModBlocks.DEEPSLATE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_DEEPSLATE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_DEEPSLATE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_SEDIMENT_STONE = ceilingRandomPatchWhitelist("tall_stalactite_sediment_stone", ModFeatures.TALL_SPELOTHEM, ModBlocks.SEDIMENT_STONE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_SEDIMENT_STONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_SEDIMENT_STONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_LAVASTONE = ceilingRandomPatchWhitelist("tall_stalactite_lavastone", ModFeatures.TALL_SPELOTHEM, ModBlocks.LAVASTONE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_LAVASTONE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_LAVASTONE);
        private static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_STALACTITE_PACKED_ICE = ceilingRandomPatchWhitelist("tall_stalactite_packed_ice", ModFeatures.TALL_SPELOTHEM, ModBlocks.PACKED_ICE_SPELOTHEMS.tallStalactite.defaultBlockState(), TALL_STALACTITE_PACKED_ICE_TRIES, SPREAD_I, SPREAD_I, WHITELIST_PACKED_ICE);

        // endregion

        // @formatter:on

        // endregion

        // region Mushrooms

        // @formatter:off

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SWEETSHROOM = floorRandomPatchBlacklist("sweetshroom", ModBlocks.SWEETSHROOM.defaultBlockState(), SWEETSHROOM_TRIES, SPREAD_I, SPREAD_K, SWEETSHROOM_BLACKLIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> GOLDISHROOM = floorRandomPatchBlacklist("goldishroom", ModBlocks.GOLDISHROOM.defaultBlockState(), GOLDISHROOM_TRIES, SPREAD_I, SPREAD_K, GOLDISHROOM_BLACKLIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SHINYSHROOM = floorRandomPatchBlacklist("shinyshroom", ModBlocks.SHINYSHROOM.defaultBlockState(), SHINYSHROOM_TRIES, SPREAD_I, SPREAD_K, SHINYSHROOM_BLACKLIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> LUMISHROOM = floorRandomPatchBlacklist("lumishroom", ModBlocks.LUMISHROOM.defaultBlockState(), LUMISHROOM_TRIES, SPREAD_I, SPREAD_K, LUMISHROOM_BLACKLIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLUOSHROOM = floorRandomPatchBlacklist("fluoshroom", ModBlocks.FLUOSHROOM.defaultBlockState(), FLUOSHROOM_TRIES, SPREAD_I, SPREAD_K, FLUOSHROOM_BLACKLIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ROCKSHROOM = floorRandomPatchBlacklist("rockshroom", ModBlocks.ROCKSHROOM.defaultBlockState(), ROCKSHROOM_TRIES, SPREAD_I, SPREAD_K, ROCKSHROOM_BLACKLIST);

        // @formatter:on

        // endregion

        // region Moss

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DRY_MOSS = floorRandomPatch("dry_moss", ModBlocks.MOSS_DRY.defaultBlockState(), DRY_MOSS_TRIES, SPREAD_I, SPREAD_K);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FIRE_MOSS = floorRandomPatchWhitelist("fire_moss", ModBlocks.MOSS_FIRE.defaultBlockState(), FIRE_MOSS_TRIES, SPREAD_I, SPREAD_K, FIRE_MOSS_WHITELIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FROZEN_MOSS = floorRandomPatchWhitelist("frozen_moss", ModBlocks.MOSS_FROZEN.defaultBlockState(), FROZEN_MOSS_TRIES, SPREAD_I, SPREAD_K, FROZEN_MOSS_WHITELIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HANGING_ROOTS = ceilingRandomPatch("hanging_roots", ModBlocks.MOSS_HANGING_ROOTS.defaultBlockState(), HANGING_ROOTS_TRIES, SPREAD_I, SPREAD_K);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HUMID_MOSS_GROUND = floorRandomPatch("humid_moss_ground", ModBlocks.MOSS_HUMID_GROUND.defaultBlockState(), HUMID_MOSS_GROUND_TRIES, SPREAD_I, SPREAD_K);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HUMID_MOSS_CEILING = ceilingRandomPatchWhitelist("humid_moss_ceiling", ModBlocks.MOSS_HUMID_CEILING.defaultBlockState(), HUMID_MOSS_CEILING_TRIES, SPREAD_I, SPREAD_K, HUMID_MOSS_CEILING_WHITELIST);
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CAVE_VINE = ceilingRandomPatch("cave_vine", ModBlocks.CAVE_VINE_END.defaultBlockState(), CAVE_VINE_TRIES, SPREAD_I, SPREAD_K);

        // endregion

        // region Helpers

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> floorRandomPatchBlacklist(String name, BlockState state, int tries, int xzspread, int yspread, List<Block> blacklist) {
            return floorRandomPatchBlacklist(name, Feature.SIMPLE_BLOCK, state, tries, xzspread, yspread, blacklist);
        }
        
        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> floorRandomPatchBlacklist(String name, Feature<SimpleBlockConfiguration> feature, BlockState state, int tries, int xzspread, int yspread, List<Block> blacklist) {
            return randomPatch(name, new RandomPatchConfiguration(tries, xzspread, yspread,
                PlacementUtils.filtered(feature, new SimpleBlockConfiguration(BlockStateProvider.simple(state)),
                    BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.not(BlockPredicate.matchesBlocks(blacklist, new BlockPos(0, -1, 0)))))));
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> floorRandomPatchWhitelist(String name, BlockState state, int tries, int xzspread, int yspread, List<Block> whitelist) {
            return floorRandomPatchWhitelist(name, Feature.SIMPLE_BLOCK, state, tries, xzspread, yspread, whitelist);
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> floorRandomPatchWhitelist(String name, Feature<SimpleBlockConfiguration> feature, BlockState state, int tries, int xzspread, int yspread, List<Block> whitelist) {
            return randomPatch(name, new RandomPatchConfiguration(tries, xzspread, yspread,
                PlacementUtils.filtered(feature, new SimpleBlockConfiguration(BlockStateProvider.simple(state)),
                    BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.matchesBlocks(whitelist, new BlockPos(0, -1, 0))))));
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> floorRandomPatch(String name, BlockState state, int tries, int xzspread, int yspread) {
            return floorRandomPatch(name, Feature.SIMPLE_BLOCK, state, tries, xzspread, yspread);
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> floorRandomPatch(String name, Feature<SimpleBlockConfiguration> feature, BlockState state, int tries, int xzspread, int yspread) {
            return randomPatch(name, new RandomPatchConfiguration(tries, xzspread, yspread,
                PlacementUtils.inlinePlaced(feature, new SimpleBlockConfiguration(BlockStateProvider.simple(state)),
                    BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.solid(new BlockPos(0, -1, 0)))))));
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ceilingRandomPatchBlacklist(String name, BlockState state, int tries, int xzspread, int yspread, List<Block> blacklist) {
            return ceilingRandomPatchBlacklist(name, Feature.SIMPLE_BLOCK, state, tries, xzspread, yspread, blacklist);
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ceilingRandomPatchBlacklist(String name, Feature<SimpleBlockConfiguration> feature, BlockState state, int tries, int xzspread, int yspread, List<Block> blacklist) {
            return randomPatch(name, new RandomPatchConfiguration(tries, xzspread, yspread,
                PlacementUtils.filtered(feature, new SimpleBlockConfiguration(BlockStateProvider.simple(state)),
                    BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.not(BlockPredicate.matchesBlocks(blacklist, new BlockPos(0, 1, 0)))))));
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ceilingRandomPatchWhitelist(String name, BlockState state, int tries, int xzspread, int yspread, List<Block> whitelist) {
            return ceilingRandomPatchWhitelist(name, Feature.SIMPLE_BLOCK, state, tries, xzspread, yspread, whitelist);
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ceilingRandomPatchWhitelist(String name, Feature<SimpleBlockConfiguration> feature, BlockState state, int tries, int xzspread, int yspread, List<Block> whitelist) {
            return randomPatch(name, new RandomPatchConfiguration(tries, xzspread, yspread,
                PlacementUtils.filtered(feature, new SimpleBlockConfiguration(BlockStateProvider.simple(state)),
                    BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.matchesBlocks(whitelist, new BlockPos(0, 1, 0))))));
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ceilingRandomPatch(String name, BlockState state, int tries, int xzspread, int yspread) {
            return ceilingRandomPatch(name, Feature.SIMPLE_BLOCK, state, tries, xzspread, yspread);
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ceilingRandomPatch(String name, Feature<SimpleBlockConfiguration> feature, BlockState state, int tries, int xzspread, int yspread) {
            return randomPatch(name, new RandomPatchConfiguration(tries, xzspread, yspread,
                PlacementUtils.inlinePlaced(feature, new SimpleBlockConfiguration(BlockStateProvider.simple(state)),
                    BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.solid(new BlockPos(0, 1, 0)))))));
        }

        private static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> randomPatch(String name, RandomPatchConfiguration config) {
            return FeatureUtils.register(Constants.MOD_ID + ":" + name, Feature.RANDOM_PATCH, config);
        }

        // endregion

    }

    public static class Placements {

        // region Ores

        // @formatter:off

        public static final Holder<PlacedFeature> ORE_BROKEN_STONE = ore("ore_broken_stone", Configured.ORE_BROKEN_STONE, ModConfig.common().oreBrokenStone(), VerticalAnchor.absolute(0), VerticalAnchor.belowTop(16));
        public static final Holder<PlacedFeature> ORE_BROKEN_DEEPSLATE = ore("ore_broken_deepslate", Configured.ORE_BROKEN_DEEPSLATE, ModConfig.common().oreBrokenDeepslate(), VerticalAnchor.bottom(), VerticalAnchor.absolute(0));
        public static final Holder<PlacedFeature> ORE_SEDIMENT_STONE = ore("ore_sediment_stone", Configured.ORE_SEDIMENT_STONE, ModConfig.common().oreSedimentStone(), VerticalAnchor.absolute(16), VerticalAnchor.belowTop(16));
        public static final Holder<PlacedFeature> ORE_LAVASTONE = ore("ore_lavastone", Configured.ORE_LAVASTONE, ModConfig.common().oreLavastone(), VerticalAnchor.bottom(), VerticalAnchor.absolute(0));
        public static final Holder<PlacedFeature> ORE_DIRTSTONE = ore("ore_dirtstone", Configured.ORE_DIRTSTONE, ModConfig.common().oreDirtstone(), VerticalAnchor.absolute(16), VerticalAnchor.belowTop(16));
        public static final Holder<PlacedFeature> ORE_MARLSTONE = ore("ore_marlstone", Configured.ORE_MARLSTONE, ModConfig.common().oreMarlstone(), VerticalAnchor.absolute(16), VerticalAnchor.belowTop(16));
        public static final Holder<PlacedFeature> ORE_PACKED_ICE = ore("ore_packed_ice", Configured.ORE_PACKED_ICE, ModConfig.common().orePackedIce(), VerticalAnchor.absolute(0), VerticalAnchor.belowTop(16));

        // @formatter:on

        private static Holder<PlacedFeature> ore(String name, Holder<ConfiguredFeature<OreConfiguration, ?>> configured, ModConfig.OreFeatureConfig config,
            VerticalAnchor bottom, VerticalAnchor top) {
            return PlacementUtils.register(Constants.MOD_ID + ":" + name, configured, CountPlacement.of(config.count()),
                HeightRangePlacement.uniform(bottom, top), BiomeFilter.biome());
        }

        // endregion

        // region Rocks

        // @formatter:off

        public static final Holder<PlacedFeature> FLINT_PEBBLE = underground("flint_pebble", Configured.FLINT_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> FLINT_PEBBLE_SURFACE = surface("flint_pebble_surface", Configured.FLINT_PEBBLE_SURFACE, SURFACE_PEBBLE_COUNT);

        public static final Holder<PlacedFeature> STONE_PEBBLE = underground("stone_pebble", Configured.STONE_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> ANDESITE_PEBBLE = underground("andesite_pebble", Configured.ANDESITE_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> DIORITE_PEBBLE = underground("diorite_pebble", Configured.DIORITE_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> GRANITE_PEBBLE = underground("granite_pebble", Configured.GRANITE_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> TUFF_PEBBLE = underground("tuff_pebble", Configured.TUFF_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> DEEPSLATE_PEBBLE = underground("deepslate_pebble", Configured.DEEPSLATE_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> SEDIMENT_STONE_PEBBLE = underground("sediment_stone_pebble", Configured.SEDIMENT_STONE_PEBBLE, PEBBLE_COUNT);
        public static final Holder<PlacedFeature> LAVASTONE_PEBBLE = underground("lavastone_pebble", Configured.LAVASTONE_PEBBLE, PEBBLE_COUNT);

        public static final Holder<PlacedFeature> SMALL_STONE_PILE = underground("small_stone_pile", Configured.SMALL_STONE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> SMALL_ANDESITE_PILE = underground("small_andesite_pile", Configured.SMALL_ANDESITE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> SMALL_DIORITE_PILE = underground("small_diorite_pile", Configured.SMALL_DIORITE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> SMALL_GRANITE_PILE = underground("small_granite_pile", Configured.SMALL_GRANITE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> SMALL_TUFF_PILE = underground("small_tuff_pile", Configured.SMALL_TUFF_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> SMALL_DEEPSLATE_PILE = underground("small_deepslate_pile", Configured.SMALL_DEEPSLATE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> SMALL_SEDIMENT_STONE_PILE = underground("small_sediment_stone_pile", Configured.SMALL_SEDIMENT_STONE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> SMALL_LAVASTONE_PILE = underground("small_lavastone_pile", Configured.SMALL_LAVASTONE_PILE, ROCKPILE_COUNT);

        public static final Holder<PlacedFeature> BIG_STONE_PILE = underground("big_stone_pile", Configured.BIG_STONE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> BIG_ANDESITE_PILE = underground("big_andesite_pile", Configured.BIG_ANDESITE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> BIG_DIORITE_PILE = underground("big_diorite_pile", Configured.BIG_DIORITE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> BIG_GRANITE_PILE = underground("big_granite_pile", Configured.BIG_GRANITE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> BIG_TUFF_PILE = underground("big_tuff_pile", Configured.BIG_TUFF_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> BIG_DEEPSLATE_PILE = underground("big_deepslate_pile", Configured.BIG_DEEPSLATE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> BIG_SEDIMENT_STONE_PILE = underground("big_sediment_stone_pile", Configured.BIG_SEDIMENT_STONE_PILE, ROCKPILE_COUNT);
        public static final Holder<PlacedFeature> BIG_LAVASTONE_PILE = underground("big_lavastone_pile", Configured.BIG_LAVASTONE_PILE, ROCKPILE_COUNT);

        // @formatter:on

        // endregion

        // region Spelothems

        // region Stalagmites

        public static final Holder<PlacedFeature> STALAGMITE_STONE = underground("stalagmite_stone", Configured.STALAGMITE_STONE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_ANDESITE = underground("stalagmite_andesite", Configured.STALAGMITE_ANDESITE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_DIORITE = underground("stalagmite_diorite", Configured.STALAGMITE_DIORITE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_GRANITE = underground("stalagmite_granite", Configured.STALAGMITE_GRANITE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_TUFF = underground("stalagmite_tuff", Configured.STALAGMITE_TUFF, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_DEEPSLATE = underground("stalagmite_deepslate", Configured.STALAGMITE_DEEPSLATE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_SEDIMENT_STONE = underground("stalagmite_sediment_stone", Configured.STALAGMITE_SEDIMENT_STONE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_LAVASTONE = underground("stalagmite_lavastone", Configured.STALAGMITE_LAVASTONE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> STALAGMITE_PACKED_ICE = underground("stalagmite_packed_ice", Configured.STALAGMITE_PACKED_ICE, STALAGMITE_COUNT);

        public static final Holder<PlacedFeature> TALL_STALAGMITE_STONE = underground("tall_stalagmite_stone", Configured.TALL_STALAGMITE_STONE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_ANDESITE = underground("tall_stalagmite_andesite", Configured.TALL_STALAGMITE_ANDESITE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_DIORITE = underground("tall_stalagmite_diorite", Configured.TALL_STALAGMITE_DIORITE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_GRANITE = underground("tall_stalagmite_granite", Configured.TALL_STALAGMITE_GRANITE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_TUFF = underground("tall_stalagmite_tuff", Configured.TALL_STALAGMITE_TUFF, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_DEEPSLATE = underground("tall_stalagmite_deepslate", Configured.TALL_STALAGMITE_DEEPSLATE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_SEDIMENT_STONE = underground("tall_stalagmite_sediment_stone", Configured.TALL_STALAGMITE_SEDIMENT_STONE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_LAVASTONE = underground("tall_stalagmite_lavastone", Configured.TALL_STALAGMITE_LAVASTONE, STALAGMITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALAGMITE_PACKED_ICE = underground("tall_stalagmite_packed_ice", Configured.TALL_STALAGMITE_PACKED_ICE, STALAGMITE_COUNT);

        // endregion

        // region Stalactites

        public static final Holder<PlacedFeature> STALACTITE_STONE = underground("stalactite_stone", Configured.STALACTITE_STONE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_ANDESITE = underground("stalactite_andesite", Configured.STALACTITE_ANDESITE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_DIORITE = underground("stalactite_diorite", Configured.STALACTITE_DIORITE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_GRANITE = underground("stalactite_granite", Configured.STALACTITE_GRANITE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_TUFF = underground("stalactite_tuff", Configured.STALACTITE_TUFF, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_DEEPSLATE = underground("stalactite_deepslate", Configured.STALACTITE_DEEPSLATE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_SEDIMENT_STONE = underground("stalactite_sediment_stone", Configured.STALACTITE_SEDIMENT_STONE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_LAVASTONE = underground("stalactite_lavastone", Configured.STALACTITE_LAVASTONE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> STALACTITE_PACKED_ICE = underground("stalactite_packed_ice", Configured.STALACTITE_PACKED_ICE, STALACTITE_COUNT);

        public static final Holder<PlacedFeature> TALL_STALACTITE_STONE = underground("tall_stalactite_stone", Configured.TALL_STALACTITE_STONE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_ANDESITE = underground("tall_stalactite_andesite", Configured.TALL_STALACTITE_ANDESITE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_DIORITE = underground("tall_stalactite_diorite", Configured.TALL_STALACTITE_DIORITE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_GRANITE = underground("tall_stalactite_granite", Configured.TALL_STALACTITE_GRANITE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_TUFF = underground("tall_stalactite_tuff", Configured.TALL_STALACTITE_TUFF, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_DEEPSLATE = underground("tall_stalactite_deepslate", Configured.TALL_STALACTITE_DEEPSLATE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_SEDIMENT_STONE = underground("tall_stalactite_sediment_stone", Configured.TALL_STALACTITE_SEDIMENT_STONE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_LAVASTONE = underground("tall_stalactite_lavastone", Configured.TALL_STALACTITE_LAVASTONE, STALACTITE_COUNT);
        public static final Holder<PlacedFeature> TALL_STALACTITE_PACKED_ICE = underground("tall_stalactite_packed_ice", Configured.TALL_STALACTITE_PACKED_ICE, STALACTITE_COUNT);

        // endregion

        // region

        // endregion

        // region Mushrooms

        // @formatter:off

        public static final Holder<PlacedFeature> SWEETSHROOM = underground("sweetshroom", Configured.SWEETSHROOM, ModConfig.common().sweetshroomCount());
        public static final Holder<PlacedFeature> GOLDISHROOM = underground("goldishroom", Configured.GOLDISHROOM, ModConfig.common().goldishroomCount());
        public static final Holder<PlacedFeature> SHINYSHROOM = underground("shinyshroom", Configured.SHINYSHROOM, ModConfig.common().shinyshroomCount());
        public static final Holder<PlacedFeature> LUMISHROOM = underground("lumishroom", Configured.LUMISHROOM, ModConfig.common().lumishroomCount());
        public static final Holder<PlacedFeature> FLUOSHROOM = underground("fluoshroom", Configured.FLUOSHROOM, ModConfig.common().fluoshroomCount());
        public static final Holder<PlacedFeature> ROCKSHROOM = underground("rockshroom", Configured.ROCKSHROOM, ModConfig.common().rockshroomCount());

        // @formatter:on

        // endregion

        // region Moss

        public static final Holder<PlacedFeature> DRY_MOSS = underground("dry_moss", Configured.DRY_MOSS, ModConfig.common().dryMossCount());
        public static final Holder<PlacedFeature> FIRE_MOSS = underground("fire_moss", Configured.FIRE_MOSS, ModConfig.common().fireMossCount());
        public static final Holder<PlacedFeature> FROZEN_MOSS = underground("frozen_moss", Configured.FROZEN_MOSS, ModConfig.common().frozenMossCount());
        public static final Holder<PlacedFeature> HANGING_ROOTS = underground("hanging_roots", Configured.HANGING_ROOTS, ModConfig.common().hangingRootsCount());
        public static final Holder<PlacedFeature> HUMID_MOSS_GROUND = underground("humid_moss_ground", Configured.HUMID_MOSS_GROUND, ModConfig.common().humidMossCount());
        public static final Holder<PlacedFeature> HUMID_MOSS_CEILING = underground("humid_moss_ceiling", Configured.HUMID_MOSS_CEILING, ModConfig.common().humidMossCount());
        public static final Holder<PlacedFeature> CAVE_VINE = underground("cave_vine", Configured.CAVE_VINE, ModConfig.common().caveVineCount());

        // endregion

        private static Holder<PlacedFeature> surface(String name, Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> feature, int count) {
            return PlacementUtils.register(Constants.MOD_ID + ":" + name, feature, CountPlacement.of(count), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        }

        private static Holder<PlacedFeature> underground(String name, Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> feature, int count) {
            return PlacementUtils.register(Constants.MOD_ID + ":" + name, feature, CountPlacement.of(count), InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
                BiomeFilter.biome());
        }

        private static void init() {}

    }

    public static void init() {
        Placements.init();
    }


    // region Biome Modifications

    public static final Set<Biome.BiomeCategory> INVALID_BIOMES = ImmutableSet.of(Biome.BiomeCategory.THEEND, Biome.BiomeCategory.NETHER);
    public static final Set<Biome.BiomeCategory> IN_HUMID = ImmutableSet.of(Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.MUSHROOM,
        Biome.BiomeCategory.SWAMP);
    public static final Set<Biome.BiomeCategory> IN_DRY = ImmutableSet.of(Biome.BiomeCategory.DESERT, Biome.BiomeCategory.MESA, Biome.BiomeCategory.SAVANNA);
    public static final Set<Biome.BiomeCategory> IN_COLD = ImmutableSet.of(Biome.BiomeCategory.ICY, Biome.BiomeCategory.TAIGA);
    public static final Set<Biome.BiomeCategory> IN_WET = ImmutableSet.of(Biome.BiomeCategory.BEACH, Biome.BiomeCategory.RIVER);

    // TODO: Better way to deal with different biomes, but this is a start at generalising the worldgen crap

    public static void addOverworldFeatures(BiConsumer<GenerationStep.Decoration, Holder<PlacedFeature>> addFeature) {
        // Ore Types
        if (ModConfig.common().oreBrokenStone().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_ORES, ModWorldGen.Placements.ORE_BROKEN_STONE);
        }

        if (ModConfig.common().oreBrokenDeepslate().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_ORES, ModWorldGen.Placements.ORE_BROKEN_DEEPSLATE);
        }

        if (ModConfig.common().oreSedimentStone().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_ORES, ModWorldGen.Placements.ORE_SEDIMENT_STONE);
        }

        if (ModConfig.common().oreLavastone().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_ORES, ModWorldGen.Placements.ORE_LAVASTONE);
        }

        // Rocks
        if (ModConfig.common().flintPebble().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.FLINT_PEBBLE);
        }

        if (ModConfig.common().pebbles().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STONE_PEBBLE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.ANDESITE_PEBBLE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.DIORITE_PEBBLE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.GRANITE_PEBBLE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TUFF_PEBBLE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.DEEPSLATE_PEBBLE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SEDIMENT_STONE_PEBBLE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.LAVASTONE_PEBBLE);
        }

        if (ModConfig.common().rockPiles().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_STONE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_ANDESITE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_DIORITE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_GRANITE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_TUFF_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_DEEPSLATE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_SEDIMENT_STONE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.SMALL_LAVASTONE_PILE);

            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_STONE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_ANDESITE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_DIORITE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_GRANITE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_TUFF_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_DEEPSLATE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_SEDIMENT_STONE_PILE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.BIG_LAVASTONE_PILE);
        }

        if (ModConfig.common().stalagmites().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_ANDESITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_ANDESITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_DIORITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_DIORITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_GRANITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_GRANITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_TUFF);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_TUFF);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_DEEPSLATE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_DEEPSLATE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_SEDIMENT_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_SEDIMENT_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_LAVASTONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_LAVASTONE);
        }

        if (ModConfig.common().stalactites().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_ANDESITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_ANDESITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_DIORITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_DIORITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_GRANITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_GRANITE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_TUFF);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_TUFF);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_DEEPSLATE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_DEEPSLATE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_SEDIMENT_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_SEDIMENT_STONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_LAVASTONE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_LAVASTONE);
        }
        
        if (ModConfig.common().mushroomsEnabled()) {
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.SWEETSHROOM);
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.GOLDISHROOM);
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.SHINYSHROOM);
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.LUMISHROOM);
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.ROCKSHROOM);
        }
        
        if (ModConfig.common().mossEnabled()) {
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.DRY_MOSS);
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.FIRE_MOSS);
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.HANGING_ROOTS);
        }

        if (ModConfig.common().vinesEnabled()) {
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.CAVE_VINE);
        }
    }

    public static void addOverworldSurfaceBeachFeatures(BiConsumer<GenerationStep.Decoration, Holder<PlacedFeature>> addFeature) {
        if (ModConfig.common().surfacePebbles().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.FLINT_PEBBLE_SURFACE);
        }
    }
    
    public static void addOverworldColdFeatures(BiConsumer<GenerationStep.Decoration, Holder<PlacedFeature>> addFeature) {
        if (ModConfig.common().orePackedIce().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_ORES, ModWorldGen.Placements.ORE_PACKED_ICE);
        }

        if (ModConfig.common().stalagmites().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALAGMITE_PACKED_ICE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALAGMITE_PACKED_ICE);
        }

        if (ModConfig.common().stalactites().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.STALACTITE_PACKED_ICE);
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModWorldGen.Placements.TALL_STALACTITE_PACKED_ICE);
        }

        if (ModConfig.common().mossEnabled()) {
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.FROZEN_MOSS);
        }
    }
    
    public static void addOverworldHumidFeatures(BiConsumer<GenerationStep.Decoration, Holder<PlacedFeature>> addFeature) {
        if (ModConfig.common().oreDirtstone().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_ORES, ModWorldGen.Placements.ORE_DIRTSTONE);
        }

        if (ModConfig.common().mushroomsEnabled()) {
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.FLUOSHROOM);
        }

        if (ModConfig.common().mossEnabled()) {
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.HUMID_MOSS_GROUND);
            addFeature.accept(GenerationStep.Decoration.VEGETAL_DECORATION, Placements.HUMID_MOSS_CEILING);
        }
    }
    
    public static void addOverworldDryFeatures(BiConsumer<GenerationStep.Decoration, Holder<PlacedFeature>> addFeature) {
        if (ModConfig.common().oreMarlstone().enabled()) {
            addFeature.accept(GenerationStep.Decoration.UNDERGROUND_ORES, ModWorldGen.Placements.ORE_MARLSTONE);
        }
    }

    // endregion

}
