package dev.nerdthings.expandedcaves.common.blocks.spelothem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

public abstract class AbstractTallSpelothemBlock extends AbstractSpelothemBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public AbstractTallSpelothemBlock(Properties properties, boolean ground) {
        super(properties, ground);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, ground ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
        BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
        DoubleBlockHalf reference = ground ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER;
        Direction dir = ground ? Direction.UP : Direction.DOWN;
        if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == reference != (facing == dir)
            || facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == reference && facing == dir.getOpposite() && !stateIn.canSurvive(worldIn, currentPos) ?
                Blocks.AIR.defaultBlockState() :
                super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(HALF) != (ground ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER)) {
            return super.canSurvive(state, worldIn, pos);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.relative(ground ? Direction.DOWN : Direction.UP));
            if (state.getBlock() != this)
                return super.canSurvive(state, worldIn, pos); // Forge: This function is called during world gen
                                                                   // and placement, before this block is set, so if we
                                                                   // are not 'here' then assume it's the pre-check.
            return blockstate.is(this) && blockstate.getValue(HALF) == (ground ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER);
        }
    }

    // TODO: COMPARE WITH DoublePlantBlock
    public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(HALF, ground ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER), flags);
        worldIn.setBlock(pos.relative(ground ? Direction.UP : Direction.DOWN), this.defaultBlockState().setValue(HALF, ground ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER), flags);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     */
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide) {
            if (player.isCreative()) {
                removeBottomHalf(worldIn, pos, state, player);
            } else {
                dropResources(state, worldIn, pos, null, player, player.getMainHandItem());
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    protected void removeBottomHalf(Level world, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (doubleblockhalf == (ground ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER)) {
            BlockPos blockpos = pos.relative(ground ? Direction.DOWN : Direction.UP);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == (ground ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER)) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        return !level.isOutsideBuildHeight(ground ? pos.above() : pos.below()) &&
                level.getBlockState(pos.relative(ground ? Direction.UP : Direction.DOWN))
                        .canBeReplaced(context) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.relative(ground ? Direction.UP : Direction.DOWN), this.defaultBlockState().setValue(HALF, ground ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    // TODO: Apparently this was marked as unfinished??
}
