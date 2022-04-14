package dev.nerdthings.expandedcaves.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.ModTags;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.items.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class AdvancementProvider extends net.minecraft.data.advancements.AdvancementProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public AdvancementProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
        generator = dataGenerator;
    }

    @Override
    public String getName() {
        return "Common Advancement Provider";
    }

    // Gross copying was done in order to get this to work without mixins.
    @Override
    public void run(HashCache hashCache) {
        Path $$1 = this.generator.getOutputFolder();
        Set<ResourceLocation> $$2 = Sets.newHashSet();
        Consumer<Advancement> consumer = ($$3x) -> {
            if (!$$2.add($$3x.getId())) {
                throw new IllegalStateException("Duplicate advancement " + $$3x.getId());
            } else {
                Path $$4x = createPath($$1, $$3x);

                try {
                    DataProvider.save(GSON, hashCache, $$3x.deconstruct().serializeToJson(), $$4x);
                } catch (IOException var6) {
                    LOGGER.error("Couldn't save advancement {}", $$4x, var6);
                }

            }
        };

        // Add our advancements
        advancements(consumer);
    }

    private static Path createPath(Path $$0, Advancement $$1) {
        String var10001 = $$1.getId().getNamespace();
        return $$0.resolve("data/" + var10001 + "/advancements/" + $$1.getId().getPath() + ".json");
    }

    private static void advancements(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement()
            .display(rootDisplay(ModBlocks.STONE_PEBBLES, "advancements.caves.root.title", "advancements.caves.root.description", Constants.loc("textures/block/lavastone.png")))
            .addCriterion("pebble", onPickup(ModTags.Items.STONE_PEBBLES))
            .save(consumer, Constants.loc("root").toString());

        // Mushrooms
        Advancement sweetshroom = item(root, ModBlocks.SWEETSHROOM, "sweetshroom", consumer);
        Advancement lumishroom = item(sweetshroom, ModBlocks.LUMISHROOM, "lumishroom", consumer);
        Advancement goldishroom = item(sweetshroom, ModBlocks.GOLDISHROOM, "goldishroom", consumer);
        Advancement rockshroom = item(sweetshroom, ModBlocks.ROCKSHROOM, "rockshroom", consumer);
        Advancement fluoshroom = item(lumishroom, ModBlocks.FLUOSHROOM, "fluoshroom", consumer);
        Advancement shinyshroom = item(goldishroom, ModBlocks.SHINYSHROOM, "shinyshroom", consumer);

        // Stews
        item(shinyshroom, ModItems.STICKY_STEW, "sticky_stew", consumer);
        item(fluoshroom, ModItems.FLUORESCENT_STEW, "fluorescent_stew", consumer);
        item(rockshroom, ModItems.HARD_STEW, "hard_stew", consumer);

        // Kill skeleton or zombie
        Advancement killUndead = Advancement.Builder.advancement()
            .parent(root)
            .display(simple(Items.IRON_SWORD, "kill_a_skeleton_or_zombie", FrameType.TASK))
            .addCriterion("skeleton", onKill(EntityType.SKELETON))
            .addCriterion("zombie", onKill(EntityType.ZOMBIE))
            .save(consumer, Constants.loc("kill_a_skeleton_or_zombie").toString());

        Advancement killZombie = Advancement.Builder.advancement()
            .parent(killUndead)
            .display(simple(Items.ZOMBIE_HEAD, "kill_a_zombie", FrameType.TASK))
            .addCriterion("zombie", onKill(EntityType.ZOMBIE))
            .save(consumer, Constants.loc("kill_a_zombie").toString());

        Advancement killSkeleton = Advancement.Builder.advancement()
            .parent(killUndead)
            .display(simple(Items.ZOMBIE_HEAD, "kill_a_skeleton", FrameType.TASK))
            .addCriterion("skeleton", onKill(EntityType.SKELETON))
            .save(consumer, Constants.loc("kill_a_skeleton").toString());

        // Zombie items
        item(killZombie, ModItems.BUTCHER_KNIFE, "zombie_butcher", consumer);
        item(killZombie, ModItems.CHEF_KNIFE, "zombie_chef", consumer);
        item(killZombie, ModItems.GOURMET_SPOON, "zombie_gourmet", consumer);
        item(killZombie, ModItems.RUSTY_PICKAXE, "zombie_miner", consumer);

        // Skeleton items
        item(killSkeleton, ModItems.IRON_DAGGER, "skeleton_bandit", consumer);
        item(killSkeleton, ModItems.GOURMET_FORK, "skeleton_gourmet", consumer);
        item(killSkeleton, ModItems.WOODEN_CANE, "skeleton_rich", consumer);
        item(killSkeleton, ModItems.RUSTY_SWORD, "skeleton_warrior", consumer);
    }

    // region Helpers

    private static Advancement item(Advancement parent, ItemLike item, String name, Consumer<Advancement> consumer) {
        return Advancement.Builder.advancement()
            .parent(parent)
            .display(simple(item, name, FrameType.TASK))
            .addCriterion("pickup", onPickup(item))
            .save(consumer, Constants.loc(name).toString());
    }

    // endregion

    // Thanks botania
    protected static InventoryChangeTrigger.TriggerInstance onPickup(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    protected static InventoryChangeTrigger.TriggerInstance onPickup(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag).build());
    }

    protected static KilledTrigger.TriggerInstance onKill(EntityType<?> type) {
        return KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(type));
    }

    protected static DisplayInfo simple(ItemLike icon, String name, FrameType frameType) {
        // TODO: Tidy lang keys up
        String expandedName = "advancements.caves." + name;
        return new DisplayInfo(new ItemStack(icon.asItem()),
            new TranslatableComponent(expandedName + ".title"),
            new TranslatableComponent(expandedName + ".description"),
            null, frameType, true, true, false);
    }

    protected static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, ResourceLocation background) {
        return new DisplayInfo(new ItemStack(icon.asItem()),
            new TranslatableComponent(titleKey),
            new TranslatableComponent(descKey),
            background, FrameType.TASK, false, false, false);
    }
}
