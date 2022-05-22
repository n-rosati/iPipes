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
    private static final int PIPE_COLOUR = 0x4f4f4f;
    private static final int PIPE_GLASS_COLOUR = 0x696969;
    private static final int SUPPLIER_PIPE_COLOUR = 0xef2142;
    private static final int REQUESTER_PIPE_COLOUR = 0x3495eb;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(PIPE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(SUPPLIER_PIPE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(REQUESTER_PIPE_BLOCK, RenderLayer.getTranslucent());

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> tintIndex == 0 ? PIPE_COLOUR : PIPE_GLASS_COLOUR, PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> PIPE_COLOUR, PIPE_BLOCK_ITEM);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> SUPPLIER_PIPE_COLOUR, SUPPLIER_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> SUPPLIER_PIPE_COLOUR, SUPPLIER_PIPE_BLOCK_ITEM);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> REQUESTER_PIPE_COLOUR, REQUESTER_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> REQUESTER_PIPE_COLOUR, REQUESTER_PIPE_BLOCK_ITEM);
    }
}
