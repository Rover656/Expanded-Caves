package dev.nerdthings.expandedcaves.common.blocks.spelothem;

import dev.nerdthings.expandedcaves.common.blocks.WaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractSpelothemBlock extends WaterloggableBlock {
    public final boolean ground;

    public AbstractSpelothemBlock(Properties properties, boolean ground) {
        super(properties);
        this.ground = ground;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
        BlockPos facingPos) {
        return facing == (ground ? Direction.DOWN : Direction.UP) && !this.canSurvive(stateIn, worldIn, currentPos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction dir = ground ? Direction.UP : Direction.DOWN;
        return canSupportCenter(worldIn, pos.relative(dir.getOpposite()), dir);
    }

    // For stalactites.
    // TODO: Right now stalactites will just drip on occasion. Do we make it so that they only do this with liquid above (like Dripstone)
    protected static void spawnDripParticle(Level p_154072_, BlockPos p_154073_, BlockState p_154074_) {
        // TODO: Deal with the spawn location of the particle
        Vec3 vec3 = p_154074_.getOffset(p_154072_, p_154073_);
        double d0 = 0.0625D;
        double d1 = (double)p_154073_.getX() + 0.5D + vec3.x;
        double d2 = (double)((float)(p_154073_.getY() + 1) - 0.6875F) - 0.0625D;
        double d3 = (double)p_154073_.getZ() + 0.5D + vec3.z;
        ParticleOptions particleoptions = ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        p_154072_.addParticle(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
    }
}
