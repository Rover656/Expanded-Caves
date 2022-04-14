package dev.nerdthings.expandedcaves.data;

import dev.nerdthings.expandedcaves.common.ModTags;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public class ItemTagProvider extends ItemTagsProvider {

    public ItemTagProvider(DataGenerator $$0, BlockTagsProvider $$1) {
        super($$0, $$1);
    }

    @Override
    protected void addTags() {
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.WALLS, ItemTags.WALLS);

        // Pebbles tags
        tag(ModTags.Items.STONE_PEBBLES).add(ModBlocks.STONE_PEBBLES.asItem(), ModBlocks.ANDESITE_PEBBLES.asItem(), ModBlocks.DIORITE_PEBBLES.asItem(),
            ModBlocks.GRANITE_PEBBLES.asItem(), ModBlocks.TUFF_PEBBLES.asItem(), ModBlocks.DEEPSLATE_PEBBLES.asItem(), ModBlocks.SEDIMENT_STONE.asItem(),
            ModBlocks.LAVASTONE_PEBBLES.asItem());
    }

    @Override
    public String getName() {
        return null;
    }
}
