package dev.nerdthings.expandedcaves.common.blocks.rock;

import dev.nerdthings.expandedcaves.common.blocks.WaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DecorativeRockBlock extends WaterloggableBlock {
    public static final VoxelShape FLINT_SHAPE = Shapes.or(Block.box(5.5, 0, 5.5, 10.5, 2, 10.5));
    public static final VoxelShape PEBBLE_SHAPE = Shapes.or(Block.box(6, 0, 6, 10, 2, 10));
    public static final VoxelShape ROCKPILE_TWO_SHAPE = Shapes.or(Block.box(4.5, 0, 4.5, 11.5, 3, 11.5));
    public static final VoxelShape ROCKPILE_THREE_SHAPE = Shapes.or(Block.box(2.5, 0, 2.5, 13.5, 4, 13.5));

    private final VoxelShape shape;

    public DecorativeRockBlock(VoxelShape shape, Properties properties) {
        super(properties);
        this.shape = shape;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
        BlockPos facingPos) {
        return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 vector3d = state.getOffset(worldIn, pos);
        return shape.move(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
