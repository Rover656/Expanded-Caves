package dev.nerdthings.expandedcaves.common;

import dev.nerdthings.expandedcaves.Constants;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static void init() {
        Items.init();
    }

    public static class Items {
        private static void init() {}

        public static final TagKey<Item> STONE_PEBBLES = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("stone_pebbles"));
    }
}
