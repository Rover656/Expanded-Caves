package dev.nerdthings.expandedcaves.common.blocks.spelothem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class SpelothemBundle {
    public final StalagmiteBlock stalagmite;
    public final TallStalagmiteBlock tallStalagmite;
    public final StalactiteBlock stalactite;
    public final TallStalactiteBlock tallStalactite;

    public SpelothemBundle(BiFunction<String, Block, Block> registerFunc, String name, Material material, float smallStrength, float tallStrength) {
        this(registerFunc, name, material, smallStrength, tallStrength, UnaryOperator.identity());
    }

    public SpelothemBundle(BiFunction<String, Block, Block> registerFunc, String name, Material material, float smallStrength, float tallStrength, UnaryOperator<BlockBehaviour.Properties> properties) {
        stalagmite = new StalagmiteBlock(properties.apply(BlockBehaviour.Properties.of(material)).strength(smallStrength));
        stalactite = new StalactiteBlock(properties.apply(BlockBehaviour.Properties.of(material)).strength(smallStrength));
        tallStalagmite = new TallStalagmiteBlock(properties.apply(BlockBehaviour.Properties.of(material)).strength(tallStrength));
        tallStalactite = new TallStalactiteBlock(properties.apply(BlockBehaviour.Properties.of(material)).strength(tallStrength));

        // Register them all
        registerFunc.apply(name + "_stalagmite", stalagmite);
        registerFunc.apply(name + "_stalactite", stalactite);
        registerFunc.apply(name + "_tall_stalagmite", tallStalagmite);
        registerFunc.apply(name + "_tall_stalactite", tallStalactite);
    }

}
