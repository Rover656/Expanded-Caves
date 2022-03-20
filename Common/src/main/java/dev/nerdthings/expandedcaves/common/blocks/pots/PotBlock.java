package dev.nerdthings.expandedcaves.common.blocks.pots;

import dev.nerdthings.expandedcaves.common.blocks.WaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

// TODO: CLEAN!!!

public class PotBlock extends WaterloggableBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;

    private final PotType type;

    public PotBlock(PotType type, Properties properties) {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(HORIZONTAL_FACING)) {
        case NORTH:
            return type.shapeNorth;
        case EAST:
            return type.shapeEast;
        case SOUTH:
            return type.shapeSouth;
        case WEST:
            return type.shapeWest;
        default:
            return null;
        }
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        LevelReader iworldreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for (Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate
                    .setValue(HORIZONTAL_FACING, direction1)
                    .setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
                if (blockstate.canSurvive(iworldreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
        BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        super.createBlockStateDefinition(builder);
    }
}
