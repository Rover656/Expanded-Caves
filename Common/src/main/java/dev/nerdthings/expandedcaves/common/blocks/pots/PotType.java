package dev.nerdthings.expandedcaves.common.blocks.pots;

import dev.nerdthings.expandedcaves.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum PotType {
    DECORATIVE_NORMAL(
        "decorative_pot",
        Shapes.or(
            Block.box(5.5, 6, 5.5, 10.5, 7, 10.5),
            Block.box(4.5, 1, 4.5, 11.5, 6, 11.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5),
            Block.box(5.5, 8, 5.5, 10.5, 9, 10.5),
            Block.box(6.5, 7, 6.5, 9.5, 8, 9.5)
        )
    ),
    DECORATIVE_SHORT(
        "small_decorative_pot",
        Shapes.or(
            Block.box(5.5, 1, 5.5, 10.5, 6, 10.5),
            Block.box(5.5, 7, 5.5, 10.5, 8, 10.5),
            Block.box(6.5, 6, 6.5, 9.5, 7, 9.5),
            Block.box(6.5, 0, 6.5, 9.5, 1, 9.5)
        )
    ),
    DECORATIVE_LONG(
        "decorative_amphora",
        Shapes.or(
            Block.box(5.5, 1, 5.5, 10.5, 9, 10.5),
            Block.box(5.5, 10, 5.5, 10.5, 11, 10.5),
            Block.box(6.5, 9, 6.5, 9.5, 10, 9.5),
            Block.box(6.5, 0, 6.5, 9.5, 1, 9.5)
        )
    ),
    DECORATIVE_QUARTZ_SHORT(
        "small_decorative_quartz_pot",
        Shapes.or(
            Block.box(5.5, 1, 5.5, 10.5, 6, 10.5),
            Block.box(5.5, 7, 5.5, 10.5, 8, 10.5),
            Block.box(6.5, 6, 6.5, 9.5, 7, 9.5),
            Block.box(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.box(3.5, 3, 7.5, 5.5, 6, 8.5),
            Block.box(10.5, 3, 7.5, 12.5, 6, 8.5)
        ),
        Shapes.or(
            Block.box(5.5, 1, 5.5, 10.5, 6, 10.5),
            Block.box(5.5, 7, 5.5, 10.5, 8, 10.5),
            Block.box(6.5, 6, 6.5, 9.5, 7, 9.5),
            Block.box(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.box(7.5, 3, 3.5, 8.5, 6, 5.5),
            Block.box(7.5, 3, 10.5, 8.5, 6, 12.5)
        )
    ),
    DECORATIVE_QUARTZ_LONG(
        "decorative_quartz_amphora",
        Shapes.or(
            Block.box(5.5, 1, 5.5, 10.5, 9, 10.5),
            Block.box(5.5, 10, 5.5, 10.5, 11, 10.5),
            Block.box(6.5, 9, 6.5, 9.5, 10, 9.5),
            Block.box(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.box(3.5, 5, 7.5, 5.5, 9, 8.5),
            Block.box(10.5, 5, 7.5, 12.5, 9, 8.5)
        ),
        Shapes.or(
            Block.box(5.5, 1, 5.5, 10.5, 9, 10.5),
            Block.box(5.5, 10, 5.5, 10.5, 11, 10.5),
            Block.box(6.5, 9, 6.5, 9.5, 10, 9.5),
            Block.box(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.box(7.5, 5, 3.5, 8.5, 9, 5.5),
            Block.box(7.5, 5, 10.5, 8.5, 9, 12.5)
        )
    ),
    TREASURE_NORMAL(
        "treasure_pot",
        Shapes.or(
            Block.box(4.5, 6, 7.5, 9.5, 7, 12.5),
            Block.box(3.5, 1, 6.5, 10.5, 6, 13.5),
            Block.box(4.5, 0, 7.5, 9.5, 1, 12.5),
            Block.box(4.5, 8, 7.5, 9.5, 9, 12.5),
            Block.box(5.5, 7, 8.5, 8.5, 8, 11.5)
        ),
        Shapes.or(
            Block.box(3.5, 6, 4.5, 8.5, 7, 9.5),
            Block.box(2.5, 1, 3.5, 9.5, 6, 10.5),
            Block.box(3.5, 0, 4.5, 8.5, 1, 9.5),
            Block.box(3.5, 8, 4.5, 8.5, 9, 9.5),
            Block.box(4.5, 7, 5.5, 7.5, 8, 8.5)
        ),
        Shapes.or(
            Block.box(6.5, 6, 3.5, 11.5, 7, 8.5),
            Block.box(5.5, 1, 2.5, 12.5, 6, 9.5),
            Block.box(6.5, 0, 3.5, 11.5, 1, 8.5),
            Block.box(6.5, 8, 3.5, 11.5, 9, 8.5),
            Block.box(7.5, 7, 4.5, 10.5, 8, 7.5)
        ),
        Shapes.or(
            Block.box(7.5, 6, 6.5, 12.5, 7, 11.5),
            Block.box(6.5, 1, 5.5, 13.5, 6, 12.5),
            Block.box(7.5, 0, 6.5, 12.5, 1, 11.5),
            Block.box(7.5, 8, 6.5, 12.5, 9, 11.5),
            Block.box(8.5, 7, 7.5, 11.5, 8, 10.5)
        )
    ),
    TREASURE_SHORT(
        "small_treasure_pot",
        Shapes.or(
            Block.box(4.5, 1, 7.5, 9.5, 6, 12.5),
            Block.box(4.5, 7, 7.5, 9.5, 8, 12.5),
            Block.box(5.5, 6, 8.5, 8.5, 7, 11.5),
            Block.box(5.5, 0, 8.5, 8.5, 1, 11.5)
        ),
        Shapes.or(
            Block.box(3.5, 1, 4.5, 8.5, 6, 9.5),
            Block.box(3.5, 7, 4.5, 8.5, 8, 9.5),
            Block.box(4.5, 6, 5.5, 7.5, 7, 8.5),
            Block.box(4.5, 0, 5.5, 7.5, 1, 8.5)
        ),
        Shapes.or(
            Block.box(6.5, 1, 3.5, 11.5, 6, 8.5),
            Block.box(6.5, 7, 3.5, 11.5, 8, 8.5),
            Block.box(7.5, 6, 4.5, 10.5, 7, 7.5),
            Block.box(7.5, 0, 4.5, 10.5, 1, 7.5)
        ),
        Shapes.or(
            Block.box(7.5, 1, 6.5, 12.5, 6, 11.5),
            Block.box(7.5, 7, 6.5, 12.5, 8, 11.5),
            Block.box(8.5, 6, 7.5, 11.5, 7, 10.5),
            Block.box(8.5, 0, 7.5, 11.5, 1, 10.5)
        )
    ),
    TREASURE_LONG(
        "treasure_amphora",
        Shapes.or(
            Block.box(4.5, 1, 7.5, 9.5, 9, 12.5),
            Block.box(4.5, 10, 7.5, 9.5, 11, 12.5),
            Block.box(5.5, 9, 8.5, 8.5, 10, 11.5),
            Block.box(5.5, 0, 8.5, 8.5, 1, 11.5)
        ),
        Shapes.or(
            Block.box(3.5, 1, 4.5, 8.5, 9, 9.5),
            Block.box(3.5, 10, 4.5, 8.5, 11, 9.5),
            Block.box(4.5, 9, 5.5, 7.5, 10, 8.5),
            Block.box(4.5, 0, 5.5, 7.5, 1, 8.5)
        ),
        Shapes.or(
            Block.box(6.5, 1, 3.5, 11.5, 9, 8.5),
            Block.box(6.5, 10, 3.5, 11.5, 11, 8.5),
            Block.box(7.5, 9, 4.5, 10.5, 10, 7.5),
            Block.box(7.5, 0, 4.5, 10.5, 1, 7.5)
        ),
        Shapes.or(
            Block.box(7.5, 1, 6.5, 12.5, 9, 11.5),
            Block.box(7.5, 10, 6.5, 12.5, 11, 11.5),
            Block.box(8.5, 9, 7.5, 11.5, 10, 10.5),
            Block.box(8.5, 0, 7.5, 11.5, 1, 10.5)
        )
    ),
    TREASURE_QUARTZ_SHORT(
        "small_quartz_treasure_pot",
        Shapes.or(
            Block.box(4.5, 1, 7.5, 9.5, 6, 12.5),
            Block.box(4.5, 7, 7.5, 9.5, 8, 12.5),
            Block.box(5.5, 6, 8.5, 8.5, 7, 11.5),
            Block.box(5.5, 0, 8.5, 8.5, 1, 11.5),
            Block.box(2.5, 3, 9.5, 4.5, 6, 10.5),
            Block.box(9.5, 3, 9.5, 11.5, 6, 10.5)
        ),
        Shapes.or(
            Block.box(3.5, 1, 4.5, 8.5, 6, 9.5),
            Block.box(3.5, 7, 4.5, 8.5, 8, 9.5),
            Block.box(4.5, 6, 5.5, 7.5, 7, 8.5),
            Block.box(4.5, 0, 5.5, 7.5, 1, 8.5),
            Block.box(5.5, 3, 2.5, 6.5, 6, 4.5),
            Block.box(5.5, 3, 9.5, 6.5, 6, 11.5)
        ),
        Shapes.or(
            Block.box(6.5, 1, 3.5, 11.5, 6, 8.5),
            Block.box(6.5, 7, 3.5, 11.5, 8, 8.5),
            Block.box(7.5, 6, 4.5, 10.5, 7, 7.5),
            Block.box(7.5, 0, 4.5, 10.5, 1, 7.5),
            Block.box(11.5, 3, 5.5, 13.5, 6, 6.5),
            Block.box(4.5, 3, 5.5, 6.5, 6, 6.5)
        ),
        Shapes.or(
            Block.box(7.5, 1, 6.5, 12.5, 6, 11.5),
            Block.box(7.5, 7, 6.5, 12.5, 8, 11.5),
            Block.box(8.5, 6, 7.5, 11.5, 7, 10.5),
            Block.box(8.5, 0, 7.5, 11.5, 1, 10.5),
            Block.box(9.5, 3, 11.5, 10.5, 6, 13.5),
            Block.box(9.5, 3, 4.5, 10.5, 6, 6.5)
        )
    ),
    TREASURE_QUARTZ_LONG(
        "quartz_treasure_amphora",
        Shapes.or(
            Block.box(4.5, 1, 7.5, 9.5, 9, 12.5),
            Block.box(4.5, 10, 7.5, 9.5, 11, 12.5),
            Block.box(5.5, 9, 8.5, 8.5, 10, 11.5),
            Block.box(5.5, 0, 8.5, 8.5, 1, 11.5),
            Block.box(2.5, 5, 9.5, 4.5, 9, 10.5),
            Block.box(9.5, 5, 9.5, 11.5, 9, 10.5)
        ),
        Shapes.or(
            Block.box(3.5, 1, 4.5, 8.5, 9, 9.5),
            Block.box(3.5, 10, 4.5, 8.5, 11, 9.5),
            Block.box(4.5, 9, 5.5, 7.5, 10, 8.5),
            Block.box(4.5, 0, 5.5, 7.5, 1, 8.5),
            Block.box(5.5, 5, 2.5, 6.5, 9, 4.5),
            Block.box(5.5, 5, 9.5, 6.5, 9, 11.5)
        ),
        Shapes.or(
            Block.box(6.5, 1, 3.5, 11.5, 9, 8.5),
            Block.box(6.5, 10, 3.5, 11.5, 11, 8.5),
            Block.box(7.5, 9, 4.5, 10.5, 10, 7.5),
            Block.box(7.5, 0, 4.5, 10.5, 1, 7.5),
            Block.box(11.5, 5, 5.5, 13.5, 9, 6.5),
            Block.box(4.5, 5, 5.5, 6.5, 9, 6.5)
        ),
        Shapes.or(
            Block.box(7.5, 1, 6.5, 12.5, 9, 11.5),
            Block.box(7.5, 10, 6.5, 12.5, 11, 11.5),
            Block.box(8.5, 9, 7.5, 11.5, 10, 10.5),
            Block.box(8.5, 0, 7.5, 11.5, 1, 10.5),
            Block.box(9.5, 5, 11.5, 10.5, 9, 13.5),
            Block.box(9.5, 5, 4.5, 10.5, 9, 6.5)
        )
    );

    public final ResourceLocation blockModel;
    public final ResourceLocation itemModel;
    public final VoxelShape shapeNorth;
    public final VoxelShape shapeEast;
    public final VoxelShape shapeSouth;
    public final VoxelShape shapeWest;

    PotType(String modelName, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
        blockModel = Constants.loc("block/" + modelName);
        itemModel = Constants.loc("item/" + modelName);
        shapeNorth = north;
        shapeEast = east;
        shapeSouth = south;
        shapeWest = west;
    }

    PotType(String modelName, VoxelShape shape) {
        blockModel = Constants.loc("block/" + modelName);
        itemModel = Constants.loc("item/" + modelName);
        shapeNorth = shape;
        shapeEast = shape;
        shapeSouth = shape;
        shapeWest = shape;
    }

    PotType(String modelName, VoxelShape ns, VoxelShape ew) {
        blockModel = Constants.loc("block/" + modelName);
        itemModel = Constants.loc("item/" + modelName);
        shapeNorth = ns;
        shapeEast = ew;
        shapeSouth = ns;
        shapeWest = ew;
    }
}
