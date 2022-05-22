package ca.hydranoid620.ipipes.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

import static ca.hydranoid620.ipipes.iPipes.*;

@Environment(EnvType.CLIENT)
public class iPipesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(PIPE_BLOCK, RenderLayer.getTranslucent());

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x4f4f4f, PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x4f4f4f, PIPE_BLOCK_ITEM);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, SUPPLIER_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x3495eb, SUPPLIER_PIPE_BLOCK_ITEM);
    }
}
