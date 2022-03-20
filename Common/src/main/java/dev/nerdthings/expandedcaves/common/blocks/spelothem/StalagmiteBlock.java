package dev.nerdthings.expandedcaves.common.blocks.spelothem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StalagmiteBlock extends AbstractSpelothemBlock {
    private static final VoxelShape SHAPE = Shapes.or(Block.box(6, 0, 6, 10, 15, 10));

    public StalagmiteBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
