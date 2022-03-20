package dev.nerdthings.expandedcaves.common.blocks.moss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MossBlock extends Block {
    private final MossType mossType;

    public MossBlock(MossType mossType, Properties properties) {
        super(properties);
        this.mossType = mossType;
    }

    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
        BlockPos facingPos) {
        return facing == mossType.growDirection.getOpposite() & !this.canSurvive(stateIn, worldIn, currentPos) ?
            Blocks.AIR.defaultBlockState() :
            super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.relative(mossType.growDirection.getOpposite()), mossType.growDirection);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        mossType.onEntityCollision(state, worldIn, pos, entityIn);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return mossType.shape;
    }
}
