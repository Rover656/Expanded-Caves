package dev.nerdthings.expandedcaves.common.items;

import dev.nerdthings.expandedcaves.common.ModTags;
import dev.nerdthings.expandedcaves.platform.Services;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ItemTiers implements Tier {
    // @formatter:off
    PEBBLED_STONE(1, 65, 4.0F, 1.0F, 5, () -> Ingredient.of(ModTags.Items.STONE_PEBBLES)),
    RUSTY(1, 125, 6.0F, 2.0F, 2, () -> Ingredient.of(Services.PLATFORM.ironIngotTag())),
    FLINT(1, 97, 4.0F, 1.0F, 5, () -> Ingredient.of(Items.FLINT))
    // @formatter:on
    ;

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ItemTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
    }

    public int getUses() {
        return this.maxUses;
    }

    public float getSpeed() {
        return this.efficiency;
    }

    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    public int getLevel() {
        return this.harvestLevel;
    }

    public int getEnchantmentValue() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}