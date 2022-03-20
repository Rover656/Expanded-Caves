package dev.nerdthings.expandedcaves.common.blocks;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.moss.MossBlock;
import dev.nerdthings.expandedcaves.common.blocks.moss.MossType;
import dev.nerdthings.expandedcaves.common.blocks.mushroom.CustomMushroom;
import dev.nerdthings.expandedcaves.common.blocks.mushroom.CustomMushroomBlock;
import dev.nerdthings.expandedcaves.common.blocks.pots.PotBlock;
import dev.nerdthings.expandedcaves.common.blocks.pots.PotType;
import dev.nerdthings.expandedcaves.common.blocks.rock.DecorativeRockBlock;
import dev.nerdthings.expandedcaves.common.blocks.rock.RockpileBlock;
import dev.nerdthings.expandedcaves.common.blocks.spelothem.SpelothemBundle;
import dev.nerdthings.expandedcaves.common.blocks.util.CPressurePlateBlock;
import dev.nerdthings.expandedcaves.common.blocks.util.CStairBlock;
import dev.nerdthings.expandedcaves.common.blocks.util.CStoneButtonBlock;
import dev.nerdthings.expandedcaves.common.blocks.vine.CaveVineBlock;
import dev.nerdthings.expandedcaves.common.blocks.vine.CaveVineEndBlock;
import dev.nerdthings.expandedcaves.common.items.ModFoods;
import dev.nerdthings.expandedcaves.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class ModBlocks {

    // TODO: I want to store block/item IDs in a constants file someplace to make them easier to manage.

    // Registry helper
    private record BlockEntry(String name, Block block, Function<Block, BlockItem> item) {}
    private static final List<BlockEntry> blockList = new ArrayList<>(); // TODO: I'd like a way to free this after registration.

    // region Mushrooms

    public static final CustomMushroomBlock SWEETSHROOM = customMushroomBlock("sweetshroom", CustomMushroom.SWEETSHROOM, ModFoods.SWEETSHROOM);
    public static final CustomMushroomBlock GOLDISHROOM = customMushroomBlock("goldishroom", CustomMushroom.GOLDISHROOM, ModFoods.GOLDISHROOM);
    public static final CustomMushroomBlock SHINYSHROOM = customMushroomBlock("shinyshroom", CustomMushroom.SHINYSHROOM, ModFoods.SHINYSHROOM);
    public static final CustomMushroomBlock LUMISHROOM = customMushroomBlock("lumishroom", CustomMushroom.LUMISHROOM, ModFoods.LUMISHROOM, props -> props.lightLevel(state -> 9));
    public static final CustomMushroomBlock FLUOSHROOM = customMushroomBlock("fluoshroom", CustomMushroom.FLUOSHROOM, ModFoods.FLUOSHROOM, props -> props.lightLevel(state -> 8));
    public static final CustomMushroomBlock ROCKSHROOM = customMushroomBlock("rockshroom", CustomMushroom.ROCKSHROOM, ModFoods.ROCKSHROOM);

    private static CustomMushroomBlock customMushroomBlock(String name, CustomMushroom customMushroom, FoodProperties food) {
        return customMushroomBlock(name, customMushroom, food, UnaryOperator.identity());
    }

    private static CustomMushroomBlock customMushroomBlock(String name, CustomMushroom customMushroom, FoodProperties food, UnaryOperator<BlockBehaviour.Properties> props) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(Material.PLANT).strength(0.0f).randomTicks().noCollission().sound(SoundType.GRASS);
        properties = props.apply(properties);
        return register(name, new CustomMushroomBlock(customMushroom, properties),
            block -> new BlockItem(block, Services.PLATFORM.defaultItemBuilder().food(food)));
    }

    // endregion

    // region Moss

    public static final MossBlock MOSS_DRY = mossBlock("dry_moss", MossType.DRY);
    public static final MossBlock MOSS_FIRE = mossBlock("fire_moss", MossType.FIRE, p -> p.lightLevel(state -> 12));
    public static final MossBlock MOSS_FROZEN = mossBlock("frozen_moss", MossType.FROZEN);
    public static final MossBlock MOSS_HANGING_ROOTS = mossBlock("hanging_roots", MossType.HANGING_ROOTS);
    public static final MossBlock MOSS_HUMID_GROUND = mossBlock("humid_moss_ground", MossType.HUMID_GROUND);
    public static final MossBlock MOSS_HUMID_CEILING = mossBlock("humid_moss_ceiling", MossType.HUMID_CEILING);

    private static MossBlock mossBlock(String name, MossType type) {
        return mossBlock(name, type, UnaryOperator.identity());
    }

    private static MossBlock mossBlock(String name, MossType type, UnaryOperator<BlockBehaviour.Properties> props) {
        BlockBehaviour.Properties  properties = BlockBehaviour.Properties.of(Material.PLANT).instabreak().noCollission().sound(SoundType.GRASS);
        properties = props.apply(properties);
        return register(name, new MossBlock(type, properties));
    }

    // endregion

    // region Vines

    public static final CaveVineBlock CAVE_VINE = register("cave_vine", new CaveVineBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)), b -> null);
    public static final CaveVineEndBlock CAVE_VINE_END = register("cave_vine_end", new CaveVineEndBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));

    // endregion

    // region Stone Blocks

    public static final BreakingBlock BROKEN_STONE = register("broken_stone", new BreakingBlock(SoundEvents.STONE_BREAK, () -> true, BlockBehaviour.Properties.of(Material.STONE).strength(0.75f, 1.5f)));
    public static final BreakingBlock BROKEN_DEEPSLATE = register("broken_deepslate", new BreakingBlock(SoundEvents.DEEPSLATE_BREAK, () -> true, BlockBehaviour.Properties.of(Material.STONE).strength(0.75f, 1.5f).sound(SoundType.DEEPSLATE)));

    public static final Block SEDIMENT_STONE = basic("sediment_stone", 1.0f, 3.0f);
    public static final StairBlock SEDIMENT_STONE_STAIRS = stair("sediment_stone", 1.0f, 3.0f, ModBlocks.SEDIMENT_STONE);
    public static final SlabBlock SEDIMENT_STONE_SLAB = slab("sediment_stone", 1.0f, 3.0f);
    public static final WallBlock SEDIMENT_STONE_WALL = wall("sediment_stone", 1.0f, 3.0f);
    public static final PressurePlateBlock SEDIMENT_STONE_PRESSURE_PLATE = pressurePlate("sediment_stone", 1.0f, 3.0f);
    public static final StoneButtonBlock SEDIMENT_STONE_BUTTON = button("sediment_stone", 1.0f, 3.0f);

    public static final Block LAVASTONE = basic("lavastone", 2.0f, 8.0f);
    public static final StairBlock LAVASTONE_STAIRS = stair("lavastone", 2.0f, 8.0f, ModBlocks.LAVASTONE);
    public static final SlabBlock LAVASTONE_SLAB = slab("lavastone", 2.0f, 8.0f);
    public static final WallBlock LAVASTONE_WALL = wall("lavastone", 2.0f, 8.0f);
    public static final PressurePlateBlock LAVASTONE_PRESSURE_PLATE = pressurePlate("lavastone", 2.0f, 8.0f);
    public static final StoneButtonBlock LAVASTONE_BUTTON = button("lavastone", 2.0f, 8.0f);

    public static final Block POLISHED_LAVASTONE = basic("polished_lavastone", 2.0f, 8.0f);
    public static final StairBlock POLISHED_LAVASTONE_STAIRS = stair("polished_lavastone", 2.0f, 8.0f, ModBlocks.POLISHED_LAVASTONE);
    public static final SlabBlock POLISHED_LAVASTONE_SLAB = slab("polished_lavastone", 2.0f, 8.0f);
    public static final WallBlock POLISHED_LAVASTONE_WALL = wall("polished_lavastone", 2.0f, 8.0f);
    public static final PressurePlateBlock POLISHED_LAVASTONE_PRESSURE_PLATE = pressurePlate("polished_lavastone", 2.0f, 8.0f);
    public static final StoneButtonBlock POLISHED_LAVASTONE_BUTTON = button("polished_lavastone", 2.0f, 8.0f);

    public static final Block DIRTSTONE = basic("dirtstone", 1.2f, 3.0f);
    public static final StairBlock DIRTSTONE_STAIRS = stair("dirtstone", 1.2f, 3.0f, ModBlocks.DIRTSTONE);
    public static final SlabBlock DIRTSTONE_SLAB = slab("dirtstone", 1.2f, 3.0f);
    public static final WallBlock DIRTSTONE_WALL = wall("dirtstone", 1.2f, 3.0f);
    public static final PressurePlateBlock DIRTSTONE_PRESSURE_PLATE = pressurePlate("dirtstone", 1.2f, 3.0f);
    public static final StoneButtonBlock DIRTSTONE_BUTTON = button("dirtstone", 1.2f, 3.0f);

    public static final Block COBBLED_DIRTSTONE = basic("dirtstone_cobble", 1.2f, 3.0f);
    public static final StairBlock COBBLED_DIRTSTONE_STAIRS = stair("dirtstone_cobble", 1.2f, 3.0f, ModBlocks.COBBLED_DIRTSTONE);
    public static final SlabBlock COBBLED_DIRTSTONE_SLAB = slab("dirtstone_cobble", 1.2f, 3.0f);
    public static final WallBlock COBBLED_DIRTSTONE_WALL = wall("dirtstone_cobble", 1.2f, 3.0f);
    public static final PressurePlateBlock COBBLED_DIRTSTONE_PRESSURE_PLATE = pressurePlate("dirtstone_cobble", 1.2f, 3.0f);
    public static final StoneButtonBlock COBBLED_DIRTSTONE_BUTTON = button("dirtstone_cobble", 1.2f, 3.0f);

    public static final Block MARLSTONE = basic("marlstone", 1.2f, 3.0f);
    public static final StairBlock MARLSTONE_STAIRS = stair("marlstone", 1.2f, 3.0f, ModBlocks.MARLSTONE);
    public static final SlabBlock MARLSTONE_SLAB = slab("marlstone", 1.2f, 3.0f);
    public static final WallBlock MARLSTONE_WALL = wall("marlstone", 1.2f, 3.0f);
    public static final PressurePlateBlock MARLSTONE_PRESSURE_PLATE = pressurePlate("marlstone", 1.2f, 3.0f);
    public static final StoneButtonBlock MARLSTONE_BUTTON = button("marlstone", 1.2f, 3.0f);

    // TODO: Change bricks IDs

    public static final Block BRICKS_ICE = basic("bricks_ice", 1.2f, 3.0f);
    public static final StairBlock BRICKS_ICE_STAIRS = stair("bricks_ice", 1.2f, 3.0f, ModBlocks.BRICKS_ICE);
    public static final SlabBlock BRICKS_ICE_SLAB = slab("bricks_ice", 1.2f, 3.0f);
    public static final WallBlock BRICKS_ICE_WALL = wall("bricks_ice", 1.2f, 3.0f);
    public static final PressurePlateBlock BRICKS_ICE_PRESSURE_PLATE = pressurePlate("bricks_ice", 1.2f, 3.0f);
    public static final StoneButtonBlock BRICKS_ICE_BUTTON = button("bricks_ice", 1.2f, 3.0f);

    public static final Block BRICKS_SNOW = basic("bricks_snow", 1.2f, 3.0f);
    public static final StairBlock BRICKS_SNOW_STAIRS = stair("bricks_snow", 1.2f, 3.0f, ModBlocks.BRICKS_SNOW);
    public static final SlabBlock BRICKS_SNOW_SLAB = slab("bricks_snow", 1.2f, 3.0f);
    public static final WallBlock BRICKS_SNOW_WALL = wall("bricks_snow", 1.2f, 3.0f);
    public static final PressurePlateBlock BRICKS_SNOW_PRESSURE_PLATE = pressurePlate("bricks_snow", 1.2f, 3.0f);
    public static final StoneButtonBlock BRICKS_SNOW_BUTTON = button("bricks_snow", 1.2f, 3.0f);

    private static Block basic(String name, float destroyTime, float explosionResistance) {
        return register(name, new Block(BlockBehaviour.Properties.of(Material.STONE).strength(destroyTime, explosionResistance)));
    }

    private static StairBlock stair(String name, float destroyTime, float explosionResistance, Block parent) {
        return register(name + "_stairs", new CStairBlock(parent.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).strength(destroyTime, explosionResistance)));
    }

    private static SlabBlock slab(String name, float destroyTime, float explosionResistance) {
        return register(name + "_slab", new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(destroyTime, explosionResistance)));
    }

    private static WallBlock wall(String name, float destroyTime, float explosionResistance) {
        return register(name + "_wall", new WallBlock(BlockBehaviour.Properties.of(Material.STONE).strength(destroyTime, explosionResistance)));
    }

    private static StoneButtonBlock button(String name, float destroyTime, float explosionResistance) {
        return register(name + "_button", new CStoneButtonBlock(BlockBehaviour.Properties.of(Material.STONE).strength(destroyTime, explosionResistance)));
    }

    private static PressurePlateBlock pressurePlate(String name, float destroyTime, float explosionResistance) {
        return register(name + "_pressure_plate", new CPressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.of(Material.STONE).strength(destroyTime, explosionResistance)));
    }

    // endregion

    // region Pebbles

    public static final DecorativeRockBlock PEBBLE_FLINT = register("rock_flint", new DecorativeRockBlock(DecorativeRockBlock.FLINT_SHAPE, BlockBehaviour.Properties.of(Material.STONE).instabreak().dynamicShape()));

    public static final RockpileBlock STONE_PEBBLES = rockpile("stone_pebble");
    public static final RockpileBlock ANDESITE_PEBBLES = rockpile("andesite_pebble");
    public static final RockpileBlock DIORITE_PEBBLES = rockpile("diorite_pebble");
    public static final RockpileBlock GRANITE_PEBBLES = rockpile("granite_pebble");
    public static final RockpileBlock TUFF_PEBBLES = rockpile("tuff_pebble");
    public static final RockpileBlock DEEPSLATE_PEBBLES = rockpile("deepslate_pebble");
    public static final RockpileBlock SEDIMENT_STONE_PEBBLES = rockpile("sediment_stone_pebble");
    public static final RockpileBlock LAVASTONE_PEBBLES = rockpile("lavastone_pebble");
//    public static final RockpileBlock NETHERRACK_PEBBLES = rockpile("netherrack_pebble");
//    public static final RockpileBlock BLACKSTONE_PEBBLES = rockpile("blackstone_pebble");

    private static RockpileBlock rockpile(String name) {
        return register(name, new RockpileBlock(BlockBehaviour.Properties.of(Material.STONE).instabreak().dynamicShape()));
    }

    // endregion

    // region Spelothems

    // TODO: After the port is finished maybe we ditch the bundle idea?

    public static final SpelothemBundle STONE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "stone", Material.STONE, 0.45f, 0.75f);
    public static final SpelothemBundle ANDESITE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "andesite", Material.STONE, 0.45f, 0.75f);
    public static final SpelothemBundle DIORITE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "diorite", Material.STONE, 0.45f, 0.75f);
    public static final SpelothemBundle GRANITE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "granite", Material.STONE, 0.45f, 0.75f);
    public static final SpelothemBundle TUFF_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "tuff", Material.STONE, 0.45f, 0.75f);
    public static final SpelothemBundle DEEPSLATE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "deepslate", Material.STONE, 0.45f, 0.75f);
    public static final SpelothemBundle SEDIMENT_STONE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "sediment_stone", Material.STONE, 0.3f, 0.6f);
    public static final SpelothemBundle LAVASTONE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "lavastone", Material.STONE, 0.6f, 1.0f);
    public static final SpelothemBundle PACKED_ICE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "packed_ice", Material.STONE, 0.15f, 0.25f);

//    public static final SpelothemBundle NETHERRACK_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "packed_ice", Material.STONE, 0.15f, 0.25f);
//    public static final SpelothemBundle BLACKSTONE_SPELOTHEMS = new SpelothemBundle(ModBlocks::register, "packed_ice", Material.STONE, 0.15f, 0.25f);

    // endregion

    // region Pots

    public static final PotBlock TREASURE_POT = pot("treasure_pot", PotType.TREASURE_NORMAL);
    public static final PotBlock SMALL_TREASURE_POT = pot("small_treasure_pot", PotType.TREASURE_SHORT);
    public static final PotBlock TREASURE_AMPHORA = pot("treasure_amphora", PotType.TREASURE_LONG);
    public static final PotBlock SMALL_QUARTZ_TREASURE_POT = pot("small_quartz_treasure_pot", PotType.TREASURE_QUARTZ_SHORT);
    public static final PotBlock QUARTZ_TREASURE_AMPHORA = pot("quartz_treasure_amphora", PotType.TREASURE_QUARTZ_LONG);

    public static final PotBlock DECORATIVE_POT = pot("decorative_pot", PotType.DECORATIVE_NORMAL);
    public static final PotBlock SMALL_DECORATIVE_POT = pot("small_decorative_pot", PotType.DECORATIVE_SHORT);
    public static final PotBlock DECORATIVE_AMPHORA = pot("decorative_amphora", PotType.DECORATIVE_LONG);
    public static final PotBlock SMALL_QUARTZ_DECORATIVE_POT = pot("small_decorative_quartz_pot", PotType.DECORATIVE_QUARTZ_SHORT);
    public static final PotBlock QUARTZ_DECORATIVE_AMPHORA = pot("decorative_quartz_amphora", PotType.DECORATIVE_QUARTZ_LONG);

    private static PotBlock pot(String name, PotType type) {
        return register(name, new PotBlock(type, BlockBehaviour.Properties.of(Material.DECORATION).noOcclusion()), b -> new BlockItem(b, Services.PLATFORM.defaultItemBuilder().rarity(
            Rarity.UNCOMMON)));
    }

    // endregion

    // region Registry Helper

    private static <T extends Block> T register(String name, T block) {
        blockList.add(new BlockEntry(name, block, null));
        return block;
    }

    private static <T extends Block> T register(String name, T block, Function<Block, BlockItem> item) {
        blockList.add(new BlockEntry(name, block, item));
        return block;
    }

    public static void registerBlocks(BiConsumer<Block, ResourceLocation> registerBlock) {
        for (BlockEntry block : blockList) {
            registerBlock.accept(block.block, Constants.loc(block.name));
        }
    }

    public static void registerBlockItems(BiConsumer<Item, ResourceLocation> registerItem) {
        for (BlockEntry block : blockList) {
            if (block.item != null) {
                BlockItem item = block.item.apply(block.block);
                if (item != null) {
                    registerItem.accept(item, Constants.loc(block.name));
                }
            } else {
                registerItem.accept(new BlockItem(block.block, Services.PLATFORM.defaultItemBuilder()), Constants.loc(block.name));
            }
        }
    }

    // endregion
}
