package dev.nerdthings.expandedcaves.common.blocks.moss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum MossType {
    DRY(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D), Direction.UP),
    FIRE(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D), Direction.UP) {
        @Override
        public void onEntityCollision(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
            if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BLAZE)
            {
                entityIn.makeStuckInBlock(state, new Vec3((double) 0.9F, 1.0D, (double) 0.9F));
                if (!worldIn.isClientSide && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ()))
                {
                    double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
                    double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
                    if (d0 >= (double) 0.003F || d1 >= (double) 0.003F)
                    {
                        entityIn.hurt(DamageSource.ON_FIRE, 1.0F);
                    }
                }

            }
        }
    },
    FROZEN(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D), Direction.UP) {
        @Override
        public void onEntityCollision(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
            if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.STRAY)
            {
                entityIn.makeStuckInBlock(state, new Vec3((double) 0.8F, 1.0D, (double) 0.8F));
            }
        }
    },
    HANGING_ROOTS(Block.box(1.0D, 6.0D, 1.0D, 15.0D, 16.0D, 15.0D), Direction.DOWN),
    HUMID_GROUND(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Direction.UP),
    HUMID_CEILING(Block.box(1.0D, 2.0D, 1.0D, 15.0D, 16.0D, 15.0D), Direction.DOWN);

    public VoxelShape shape;
    public Direction growDirection;
    public void onEntityCollision(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {}

    MossType(VoxelShape shape, Direction growDirection) {
        this.shape = shape;
        this.growDirection = growDirection;
    }
}
