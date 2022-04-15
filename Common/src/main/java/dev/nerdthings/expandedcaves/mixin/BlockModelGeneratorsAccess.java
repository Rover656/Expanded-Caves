package dev.nerdthings.expandedcaves.mixin;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockModelGenerators.class)
public interface BlockModelGeneratorsAccess {
    @Invoker("createSimpleBlock")
    public static MultiVariantGenerator createSimpleBlock(Block $$0, ResourceLocation $$1) {
        throw new AssertionError();
    }

    @Invoker("createRotatedVariants")
    public static Variant[] createRotatedVariants(ResourceLocation $$0) {
        throw new AssertionError();
    }

    @Invoker("createRotatedVariant")
    public static MultiVariantGenerator createRotatedVariant(Block $$0, ResourceLocation $$1) {
        throw new AssertionError();
    }

    @Invoker("createSlab")
    public static BlockStateGenerator createSlab(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
        throw new AssertionError();
    }

    @Invoker("createStairs")
    public static BlockStateGenerator createStairs(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
        throw new AssertionError();
    }

    @Invoker("createWall")
    public static BlockStateGenerator createWall(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
        throw new AssertionError();
    }

    @Invoker("createPressurePlate")
    public static BlockStateGenerator createPressurePlate(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
        throw new AssertionError();
    }

    @Invoker("createButton")
    public static BlockStateGenerator createButton(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
        throw new AssertionError();
    }
}
