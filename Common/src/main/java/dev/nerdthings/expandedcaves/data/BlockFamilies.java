package dev.nerdthings.expandedcaves.data;

import com.google.common.collect.Maps;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class BlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    // @formatter:off

    public static final BlockFamily SEDIMENT_STONE = familyBuilder(ModBlocks.SEDIMENT_STONE).stairs(ModBlocks.SEDIMENT_STONE_STAIRS).slab(ModBlocks.SEDIMENT_STONE_SLAB).wall(ModBlocks.SEDIMENT_STONE_WALL).pressurePlate(ModBlocks.SEDIMENT_STONE_PRESSURE_PLATE).button(ModBlocks.SEDIMENT_STONE_BUTTON).getFamily();
    public static final BlockFamily LAVASTONE = familyBuilder(ModBlocks.LAVASTONE).stairs(ModBlocks.LAVASTONE_STAIRS).slab(ModBlocks.LAVASTONE_SLAB).wall(ModBlocks.LAVASTONE_WALL).pressurePlate(ModBlocks.LAVASTONE_PRESSURE_PLATE).button(ModBlocks.LAVASTONE_BUTTON).getFamily();
    public static final BlockFamily POLISHED_LAVASTONE = familyBuilder(ModBlocks.POLISHED_LAVASTONE).stairs(ModBlocks.POLISHED_LAVASTONE_STAIRS).slab(ModBlocks.POLISHED_LAVASTONE_SLAB).wall(ModBlocks.POLISHED_LAVASTONE_WALL).pressurePlate(ModBlocks.POLISHED_LAVASTONE_PRESSURE_PLATE).button(ModBlocks.POLISHED_LAVASTONE_BUTTON).getFamily();
    public static final BlockFamily DIRTSTONE = familyBuilder(ModBlocks.DIRTSTONE).stairs(ModBlocks.DIRTSTONE_STAIRS).slab(ModBlocks.DIRTSTONE_SLAB).wall(ModBlocks.DIRTSTONE_WALL).pressurePlate(ModBlocks.DIRTSTONE_PRESSURE_PLATE).button(ModBlocks.DIRTSTONE_BUTTON).getFamily();
    public static final BlockFamily COBBLED_DIRTSTONE = familyBuilder(ModBlocks.COBBLED_DIRTSTONE).stairs(ModBlocks.COBBLED_DIRTSTONE_STAIRS).slab(ModBlocks.COBBLED_DIRTSTONE_SLAB).wall(ModBlocks.COBBLED_DIRTSTONE_WALL).pressurePlate(ModBlocks.COBBLED_DIRTSTONE_PRESSURE_PLATE).button(ModBlocks.COBBLED_DIRTSTONE_BUTTON).getFamily();
    public static final BlockFamily MARLSTONE = familyBuilder(ModBlocks.MARLSTONE).stairs(ModBlocks.MARLSTONE_STAIRS).slab(ModBlocks.MARLSTONE_SLAB).wall(ModBlocks.MARLSTONE_WALL).pressurePlate(ModBlocks.MARLSTONE_PRESSURE_PLATE).button(ModBlocks.MARLSTONE_BUTTON).getFamily();
    public static final BlockFamily BRICKS_ICE = familyBuilder(ModBlocks.BRICKS_ICE).stairs(ModBlocks.BRICKS_ICE_STAIRS).slab(ModBlocks.BRICKS_ICE_SLAB).wall(ModBlocks.BRICKS_ICE_WALL).pressurePlate(ModBlocks.BRICKS_ICE_PRESSURE_PLATE).button(ModBlocks.BRICKS_ICE_BUTTON).getFamily();
    public static final BlockFamily BRICKS_SNOW = familyBuilder(ModBlocks.BRICKS_SNOW).stairs(ModBlocks.BRICKS_SNOW_STAIRS).slab(ModBlocks.BRICKS_SNOW_SLAB).wall(ModBlocks.BRICKS_SNOW_WALL).pressurePlate(ModBlocks.BRICKS_SNOW_PRESSURE_PLATE).button(ModBlocks.BRICKS_SNOW_BUTTON).getFamily();

    // @formatter:on

    private static BlockFamily.Builder familyBuilder(Block p_175936_) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(p_175936_);
        BlockFamily blockfamily = MAP.put(p_175936_, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + Registry.BLOCK.getKey(p_175936_));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
