package dev.nerdthings.expandedcaves.forge.client;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.client.BlockRenderLayers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ExpandedCavesClient {
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        // Render layers
        BlockRenderLayers.setRenderLayers(ItemBlockRenderTypes::setRenderLayer);
    }
}
