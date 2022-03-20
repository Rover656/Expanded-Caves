package dev.nerdthings.expandedcaves.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PebbleItem extends BlockItem {
    public PebbleItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltips, TooltipFlag tooltipFlag) {
        // TODO: Lang.
//        tooltips.add(Lang.PLACEABLE_TOOLTIP.withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, tooltips, tooltipFlag);
    }
}
