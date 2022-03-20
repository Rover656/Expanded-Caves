package dev.nerdthings.expandedcaves.common.blocks.spelothem;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class TallStalactiteBlock extends AbstractTallSpelothemBlock {
    private static final VoxelShape SHAPE_LOWER = Shapes.or(Block.box(6, 7, 6, 10, 16, 10));
    private static final VoxelShape SHAPE_UPPER = Shapes.or(Block.box(5, 0, 5, 11, 16, 11));

    public TallStalactiteBlock(Properties properties) {
        super(properties, false);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return SHAPE_LOWER;
        }
        return SHAPE_UPPER;
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(),
            pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        float f = random.nextFloat();
        if (!(f > 0.12F)) {
            spawnDripParticle(level, blockPos, blockState);
        }
    }
}
