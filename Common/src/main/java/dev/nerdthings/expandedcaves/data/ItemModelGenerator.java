package dev.nerdthings.expandedcaves.data;

import com.google.gson.JsonElement;
import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.blocks.spelothem.SpelothemBundle;
import dev.nerdthings.expandedcaves.common.items.ModItems;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ItemModelGenerator {
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> output;

    private static final ModelTemplate STALACTITE = new ModelTemplate(Optional.of(Constants.loc("item/stalactite")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate STALACTITE_TALL = new ModelTemplate(Optional.of(Constants.loc("item/stalactite_tall")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate STALAGMITE = new ModelTemplate(Optional.of(Constants.loc("item/stalagmite")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate STALAGMITE_TALL = new ModelTemplate(Optional.of(Constants.loc("item/stalagmite_tall")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);

    public ItemModelGenerator(BiConsumer<ResourceLocation, Supplier<JsonElement>> output) {
        this.output = output;
    }

    private void generateFlatItem(Item item, ModelTemplate template) {
        template.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), this.output);
    }

    private void generatedBlock(Block block, ModelTemplate template) {
        template.create(ModelLocationUtils.getModelLocation(block.asItem()), TextureMapping.layer0(TextureMapping.getBlockTexture(block)), this.output);
    }

    private void spelothems(SpelothemBundle bundle) {
        ResourceLocation typeTexture = TextureMapping.getBlockTexture(bundle.referenceBlock);
        STALACTITE.create(ModelLocationUtils.getModelLocation(bundle.stalactite.asItem()), TextureMapping.defaultTexture(typeTexture).put(TextureSlot.PARTICLE, typeTexture), this.output);
        STALACTITE_TALL.create(ModelLocationUtils.getModelLocation(bundle.tallStalactite.asItem()), TextureMapping.defaultTexture(typeTexture).put(TextureSlot.PARTICLE, typeTexture), this.output);
        STALAGMITE.create(ModelLocationUtils.getModelLocation(bundle.stalagmite.asItem()), TextureMapping.defaultTexture(typeTexture).put(TextureSlot.PARTICLE, typeTexture), this.output);
        STALAGMITE_TALL.create(ModelLocationUtils.getModelLocation(bundle.tallStalagmite.asItem()), TextureMapping.defaultTexture(typeTexture).put(TextureSlot.PARTICLE, typeTexture), this.output);
    }

    public void run() {
        // TODO: Is this tidy enough? Could always do weird class checks and iterate over the registry..

        // Food
        generateFlatItem(ModItems.COOKED_SWEETSHROOM, ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.STICKY_STEW, ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.FLUORESCENT_STEW, ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.HARD_STEW, ModelTemplates.FLAT_ITEM);

        // Misc items
        generateFlatItem(ModItems.CLAY_LUMP, ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.BRICK_HALF, ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.PLANT_FIBER, ModelTemplates.FLAT_ITEM);

        // Tools
        generateFlatItem(ModItems.PICKER_FLINT, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.PICKER_STONE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.BUTCHER_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.CHEF_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.GOURMET_FORK, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.GOURMET_SPOON, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.IRON_DAGGER, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.RUSTY_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.RUSTY_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(ModItems.WOODEN_CANE, ModelTemplates.FLAT_HANDHELD_ITEM);

        // Mushrooms
        generatedBlock(ModBlocks.SWEETSHROOM, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.GOLDISHROOM, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.SHINYSHROOM, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.LUMISHROOM, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.FLUOSHROOM, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.ROCKSHROOM, ModelTemplates.FLAT_ITEM);

        // Moss
        generatedBlock(ModBlocks.MOSS_DRY, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.MOSS_FIRE, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.MOSS_FROZEN, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.MOSS_HANGING_ROOTS, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.MOSS_HUMID_GROUND, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.MOSS_HUMID_CEILING, ModelTemplates.FLAT_ITEM);
        generatedBlock(ModBlocks.CAVE_VINE_END, ModelTemplates.FLAT_ITEM);

        // Pebbles
        generateFlatItem(ModBlocks.STONE_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModBlocks.ANDESITE_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModBlocks.DIORITE_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModBlocks.GRANITE_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModBlocks.TUFF_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModBlocks.DEEPSLATE_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModBlocks.SEDIMENT_STONE_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModBlocks.LAVASTONE_PEBBLES.asItem(), ModelTemplates.FLAT_ITEM);

        // Spelothems
        spelothems(ModBlocks.STONE_SPELOTHEMS);
        spelothems(ModBlocks.ANDESITE_SPELOTHEMS);
        spelothems(ModBlocks.DIORITE_SPELOTHEMS);
        spelothems(ModBlocks.GRANITE_SPELOTHEMS);
        spelothems(ModBlocks.TUFF_SPELOTHEMS);
        spelothems(ModBlocks.DEEPSLATE_SPELOTHEMS);
        spelothems(ModBlocks.SEDIMENT_STONE_SPELOTHEMS);
        spelothems(ModBlocks.LAVASTONE_SPELOTHEMS);
        spelothems(ModBlocks.PACKED_ICE_SPELOTHEMS);
    }

}
