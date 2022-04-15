package dev.nerdthings.expandedcaves.mixin;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.advancements.AdvancementProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Consumer;

@Mixin(AdvancementProvider.class)
public interface AdvancementProviderAccess {
    @Mutable
    @Final
    @Accessor
    void setTabs(List<Consumer<Consumer<Advancement>>> tabs);
}
