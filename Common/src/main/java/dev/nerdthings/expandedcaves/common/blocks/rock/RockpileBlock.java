package dev.nerdthings.expandedcaves.common.blocks.rock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RockpileBlock extends Block {
    public static final VoxelShape SHAPE_ONE = Shapes.or(Block.box(6, 0, 6, 10, 2, 10));
    public static final VoxelShape SHAPE_TWO = Shapes.or(Block.box(4.5, 0, 4.5, 11.5, 3, 11.5));
    public static final VoxelShape SHAPE_THREE = Shapes.or(Block.box(2.5, 0, 2.5, 13.5, 4, 13.5));

    public static final IntegerProperty PEBBLES = IntegerProperty.create("pebbles", 1, 3);
    // TODO: Do we allow waterlogging? Probably?

    public RockpileBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(PEBBLES, 1));
    }

    public BlockState getPebbleState() {
        return this.defaultBlockState().setValue(PEBBLES, 1);
    }

    public BlockState getSmallPileState() {
        return this.defaultBlockState().setValue(PEBBLES, 2);
    }

    public BlockState getBigPileState() {
        return this.defaultBlockState().setValue(PEBBLES, 3);
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().getItem() == this.asItem() && blockState.getValue(PEBBLES) < 3 || super.canBeReplaced(blockState, context);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.cycle(PEBBLES);
        } else {
//            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
//            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(context);//.setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
//        if (p_60541_.getValue(WATERLOGGED)) {
//            p_60544_.scheduleTick(p_60545_, Fluids.WATER, Fluids.WATER.getTickDelay(p_60544_));
//        }

        return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        switch (blockState.getValue(PEBBLES)) {
            case 1:
            default:
                return SHAPE_ONE;
            case 2:
                return SHAPE_TWO;
            case 3:
                return SHAPE_THREE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PEBBLES);
        super.createBlockStateDefinition(builder);
    }
}
