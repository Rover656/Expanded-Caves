package dev.nerdthings.expandedcaves.common.blocks.vine;

import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class CaveVineEndBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public CaveVineEndBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(Random rand) {
        return NetherVines.getBlocksToGrowWhenBonemealed(rand);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.CAVE_VINE;
    }
}
