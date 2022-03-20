package dev.nerdthings.expandedcaves.common.blocks.mushroom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class CustomMushroomBlock extends MushroomBlock {

    private final CustomMushroom mushroom;

    public CustomMushroomBlock(CustomMushroom mushroom, Properties properties) {
        super(properties, null);
        this.mushroom = mushroom;
    }

    @Override
    public boolean growMushroom(ServerLevel p_54860_, BlockPos p_54861_, BlockState p_54862_, Random p_54863_) {
        // TODO: 1.18: TEMP OVERRIDE WHILE FEATURES ARE DISABLED.
        p_54860_.removeBlock(p_54861_, false);
        p_54860_.setBlock(p_54861_, p_54862_, 3);
        return false;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return mushroom.shape;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        // Hateful copy
        if (random.nextInt(mushroom.spreadSpeed) == 0) {
            int i = mushroom.spreadMax;
            int j = 4;

            for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))) {
                if (worldIn.getBlockState(blockpos).is(this)) {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            for(int k = 0; k < 4; ++k) {
                if (worldIn.isEmptyBlock(blockpos1) && state.canSurvive(worldIn, blockpos1)) {
                    pos = blockpos1;
                }

                blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }

            if (worldIn.isEmptyBlock(blockpos1) && state.canSurvive(worldIn, blockpos1)) {
                worldIn.setBlock(blockpos1, state, 2);
            }
        }
    }
}
