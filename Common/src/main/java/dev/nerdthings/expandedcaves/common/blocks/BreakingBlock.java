package dev.nerdthings.expandedcaves.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;
import java.util.function.Supplier;

// TODO: Cracking texture or overlay.

public class BreakingBlock extends Block {

    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
    public static final IntegerProperty DAMAGE_PHASE = IntegerProperty.create("damage_phase", 0, 2);

    private final SoundEvent crackSoundEvent;
    private final Supplier<Boolean> enabled;

    public BreakingBlock(SoundEvent crackSoundEvent, Supplier<Boolean> enabled, Properties properties) {
        super(properties);
        this.crackSoundEvent = crackSoundEvent;
        this.enabled = enabled;
        this.registerDefaultState(this.getStateDefinition().any().setValue(TRIGGERED, Boolean.FALSE).setValue(DAMAGE_PHASE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TRIGGERED, DAMAGE_PHASE);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(TRIGGERED)) {
            // Get current phase
            int currentPhase = pState.getValue(DAMAGE_PHASE);

            // Trigger adjacent stones if this is phase 1 or 2
            if (currentPhase > 0) {
                for (Direction offset : Direction.values()) {
                    BlockPos neighbourPos = pPos.relative(offset);
                    BlockState neighbour = pLevel.getBlockState(neighbourPos);
                    if (neighbour.getBlock() instanceof BreakingBlock breakingBlock) {
                        breakingBlock.playTriggerEffects(pLevel, neighbourPos, neighbour, pRandom);
                        pLevel.setBlock(neighbourPos, neighbour.setValue(DAMAGE_PHASE, currentPhase - 1).setValue(TRIGGERED, false), Block.UPDATE_ALL);
                    }
                }
            }

            // Change phase ourselves
            switch (currentPhase) {
            case 0 -> pLevel.setBlock(pPos, pState.setValue(DAMAGE_PHASE, 1).setValue(TRIGGERED, false), Block.UPDATE_ALL);
            case 1 -> pLevel.setBlock(pPos, pState.setValue(DAMAGE_PHASE, 2).setValue(TRIGGERED, false), Block.UPDATE_ALL);
            case 2 -> pLevel.destroyBlock(pPos, false);
            }
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        // Only trigger for players
        if (enabled.get() && pEntity.getType() == EntityType.PLAYER) {
            // 33% chance to trigger the block.
            if (!pState.getValue(TRIGGERED) && pLevel.getRandom().nextFloat() > 0.66f) {
                // Play a cracking sound to scare the player
                playTriggerEffects(pLevel, pPos, pState, pLevel.getRandom());
                pLevel.setBlock(pPos, pState.setValue(TRIGGERED, true), Block.UPDATE_ALL);

                // Schedule the next phase transition
                if (pState.getValue(DAMAGE_PHASE) < 2) {
                    scheduleTick(pLevel, pPos, pLevel.getRandom());
                } else {
                    // Trigger immediate tick, this is to upset the neighbors
                    pLevel.scheduleTick(pPos, this, 0);
                }
            }
        }
    }

    private void playTriggerEffects(Level pLevel, BlockPos pPos, BlockState pState, Random pRandom) {
        // Play breaking noise
        pLevel.playSound(null, pPos, crackSoundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);

        // Spew particles
        for (int i = 0; i < 8; i++) {
            pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, pState), pPos.getX() + 0.5f, pPos.getY() + 1.5f, pPos.getZ() + 0.5f, pRandom.nextFloat(-0.5f, 0.5f), 1.5f + pRandom.nextFloat(), pRandom.nextFloat(-0.5f, 0.5f));
        }
    }

    private void scheduleTick(Level pLevel, BlockPos pPos, Random pRandom) {
        pLevel.scheduleTick(pPos, this, Math.round((0.2f + 1 * pRandom.nextFloat()) * 20));
    }
}
