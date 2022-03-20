package dev.nerdthings.expandedcaves.fabric.client;

import dev.nerdthings.expandedcaves.client.BlockRenderLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public class ExpandedCavesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Render layers
        BlockRenderLayers.setRenderLayers(BlockRenderLayerMap.INSTANCE::putBlock);
    }
}
