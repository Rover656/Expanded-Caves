package dev.nerdthings.expandedcaves.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DoubleBlockItem extends BlockItem {
    private boolean up;

    public DoubleBlockItem(boolean up, Block blockIn, Properties builder) {
        super(blockIn, builder);
        this.up = up;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        BlockPos upBlock = up ? context.getClickedPos().above() : context.getClickedPos().below();
        context.getLevel().setBlock(upBlock, Blocks.AIR.defaultBlockState(), 27);
        return super.placeBlock(context, state);
    }
}
