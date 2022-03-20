package dev.nerdthings.expandedcaves.common.blocks.mushroom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum CustomMushroom {
    // @formatter:off
    SWEETSHROOM(Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D), 8, 5),
    GOLDISHROOM(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D), 12, 3),
    SHINYSHROOM(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D), 15, 3),
    LUMISHROOM(Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D), 10, 4),
    FLUOSHROOM(Block.box(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D), 10, 5),
    ROCKSHROOM(Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D), 18, 2)
    // @formatter:on
    ;

    public VoxelShape shape;
    public int spreadSpeed;
    public int spreadMax;

    CustomMushroom(VoxelShape shape, int spreadSpeed, int spreadMax) {
        this.shape = shape;
        this.spreadSpeed = spreadSpeed;
        this.spreadMax = spreadMax;
    }
}
