package dev.nerdthings.expandedcaves.client;

import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;

public class BlockRenderLayers {
    public static void setRenderLayers(BiConsumer<Block, RenderType> setRenderLayer) {
        setRenderLayer.accept(ModBlocks.SWEETSHROOM, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.GOLDISHROOM, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.SHINYSHROOM, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.LUMISHROOM, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.FLUOSHROOM, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.ROCKSHROOM, RenderType.cutout());

        setRenderLayer.accept(ModBlocks.MOSS_DRY, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.MOSS_FIRE, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.MOSS_FROZEN, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.MOSS_HANGING_ROOTS, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.MOSS_HUMID_GROUND, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.MOSS_HUMID_CEILING, RenderType.cutout());

        setRenderLayer.accept(ModBlocks.CAVE_VINE, RenderType.cutout());
        setRenderLayer.accept(ModBlocks.CAVE_VINE_END, RenderType.cutout());
    }
}
