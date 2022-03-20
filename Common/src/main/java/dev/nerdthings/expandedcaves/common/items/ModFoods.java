package dev.nerdthings.expandedcaves.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

@SuppressWarnings("deprecation")
public class ModFoods {
    public static final FoodProperties SWEETSHROOM = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties SWEETSHROOM_COOKED = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).build();
    public static final FoodProperties GOLDISHROOM = (new FoodProperties.Builder())
        .nutrition(2)
        .saturationMod(0.2F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0F)
        .build();
    public static final FoodProperties SHINYSHROOM = (new FoodProperties.Builder())
        .nutrition(2)
        .saturationMod(0.2F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0F)
        .build();
    public static final FoodProperties LUMISHROOM = (new FoodProperties.Builder())
        .nutrition(2)
        .saturationMod(0.2F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 0), 1.0F)
        .build();
    public static final FoodProperties FLUOSHROOM = (new FoodProperties.Builder())
        .nutrition(2)
        .saturationMod(0.2F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 1.0F)
        .build();
    public static final FoodProperties ROCKSHROOM = (new FoodProperties.Builder())
        .nutrition(2)
        .saturationMod(0.2F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0F)
        .build();
    public static final FoodProperties STEW_STICKY = (new FoodProperties.Builder())
        .nutrition(6)
        .saturationMod(0.6F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 1), 1.0F)
        .build();
    public static final FoodProperties STEW_FLUORESCENT = (new FoodProperties.Builder())
        .nutrition(6)
        .saturationMod(0.6F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0), 1.0F)
        .build();
    public static final FoodProperties STEW_HARD = (new FoodProperties.Builder())
        .nutrition(6)
        .saturationMod(0.6F)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 0), 1.0F)
        .build();
}
