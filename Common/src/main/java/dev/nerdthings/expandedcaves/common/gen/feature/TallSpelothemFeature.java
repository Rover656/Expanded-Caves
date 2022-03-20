package dev.nerdthings.expandedcaves.common.gen.feature;

import com.mojang.serialization.Codec;
import dev.nerdthings.expandedcaves.common.blocks.spelothem.AbstractTallSpelothemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class TallSpelothemFeature extends Feature<SimpleBlockConfiguration> {
    public TallSpelothemFeature(Codec<SimpleBlockConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleblockconfiguration = context.config();
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(context.random(), blockpos);
        if (blockstate.canSurvive(worldgenlevel, blockpos)) {
            if (blockstate.getBlock() instanceof AbstractTallSpelothemBlock spelothemBlock) {
                if (!worldgenlevel.isEmptyBlock(blockpos.offset(0, spelothemBlock.ground ? 1 : -1, 0))) {
                    return false;
                }

                spelothemBlock.placeAt(worldgenlevel, blockpos, 2);
                return true;
            }
        }
        return false;
    }
}
