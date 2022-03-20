package dev.nerdthings.expandedcaves.common.blocks.spelothem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class StalactiteBlock extends AbstractSpelothemBlock {
    private static final VoxelShape SHAPE = Shapes.or(Block.box(6, 1, 6, 10, 16, 10));

    public StalactiteBlock(Properties properties) {
        super(properties, false);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        float f = random.nextFloat();
        if (!(f > 0.12F)) {
            spawnDripParticle(level, blockPos, blockState);
        }
    }
}
