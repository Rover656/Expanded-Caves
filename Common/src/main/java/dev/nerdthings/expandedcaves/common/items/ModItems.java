package dev.nerdthings.expandedcaves.common.items;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.items.util.CPickaxeItem;
import dev.nerdthings.expandedcaves.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class ModItems {
    private record ItemEntry(String name, Item item) {}
    private static final List<ItemEntry> itemList = new ArrayList<>();

    public static final Item COOKED_SWEETSHROOM = register("cooked_sweetshroom", new Item(Services.PLATFORM.defaultItemBuilder().food(ModFoods.SWEETSHROOM_COOKED)));

    public static final Item STICKY_STEW = register("sticky_stew", new Item(Services.PLATFORM.defaultItemBuilder().food(ModFoods.STEW_STICKY)));
    public static final Item FLUORESCENT_STEW = register("fluorescent_stew", new Item(Services.PLATFORM.defaultItemBuilder().food(ModFoods.STEW_FLUORESCENT)));
    public static final Item HARD_STEW = register("hard_stew", new Item(Services.PLATFORM.defaultItemBuilder().food(ModFoods.STEW_HARD)));

    // TODO: healing items?

    public static final Item CLAY_LUMP = register("clay_lump");
    public static final Item BRICK_HALF = register("brick_half");
    public static final Item PLANT_FIBER = register("plant_fiber");

    public static final PickaxeItem PICKER_FLINT = pickaxe("picker_flint", ItemTiers.FLINT, 1, -2.2f);
    public static final PickaxeItem PICKER_STONE = pickaxe("picker_stone", ItemTiers.PEBBLED_STONE, 1, -2.2F);

    public static final SwordItem BUTCHER_KNIFE = sword("butcher_knife", Tiers.IRON, 4, -2.2F);
    public static final SwordItem CHEF_KNIFE = sword("chef_knife", Tiers.IRON, 3, -2.2F);
    public static final SwordItem GOURMET_FORK = sword("gourmet_fork", Tiers.IRON, 2, -2.2F);
    public static final SwordItem GOURMET_SPOON = sword("gourmet_spoon", Tiers.IRON, 1, -2.2F);
    public static final SwordItem IRON_DAGGER = sword("iron_dagger", Tiers.IRON, 2, -2.2F);
    public static final SwordItem RUSTY_SWORD = sword("rusty_sword", ItemTiers.RUSTY, 3, -2.4F);
    public static final SwordItem RUSTY_PICKAXE = sword("rusty_pickaxe", ItemTiers.RUSTY, 1, -2.8F);
    public static final SwordItem WOODEN_CANE = sword("wooden_cane", Tiers.WOOD, 3, -2.0F);

    // region Reg Helpers

    private static PickaxeItem pickaxe(String name, Tier tier, int attackDamage, float attackSpeed) {
        return register(name, new CPickaxeItem(tier, attackDamage, attackSpeed, Services.PLATFORM.defaultItemBuilder()));
    }

    private static SwordItem sword(String name, Tier tier, int attackDamage, float attackSpeed) {
        return register(name, new SwordItem(tier, attackDamage, attackDamage, Services.PLATFORM.defaultItemBuilder()));
    }

//    private static BlockItem block(Block block) {
//        return register(Registry.BLOCK.getKey(block).getPath(), new BlockItem(block, Services.PLATFORM.defaultItemBuilder()));
//    }

    private static Item register(String name) {
        return register(name, new Item(Services.PLATFORM.defaultItemBuilder()));
    }

    private static <T extends Item> T register(String name, T item) {
        itemList.add(new ItemEntry(name, item));
        return item;
    }

    public static void registerItems(BiConsumer<Item, ResourceLocation> registerItem) {
        for (ItemEntry item : itemList) {
            registerItem.accept(item.item, Constants.loc(item.name));
        }
    }

    // endregion
}
