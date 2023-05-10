package lol.hydranoid620.ipipes.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

import static lol.hydranoid620.ipipes.iPipes.*;

@Environment(EnvType.CLIENT)
public class iPipesClient implements ClientModInitializer {
    private static final int PIPE_FRAME_COLOUR = 0x333333;
    private static final int PIPE_GLASS_COLOUR = 0x808080;
    private static final int REQUESTER_PIPE_COLOUR = 0x8F0086;
    private static final int ACTIVE_SUPPLIER_PIPE_COLOUR = 0xEF2142;
    private static final int PASSIVE_SUPPLIER_PIPE_COLOUR = 0xFF7D00;
    private static final int STORAGE_PIPE_COLOUR = 0x006089;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(PIPE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(REQUESTER_PIPE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ACTIVE_SUPPLIER_PIPE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PASSIVE_SUPPLIER_PIPE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(STORAGE_PIPE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(NETWORK_CONTROLLER_BLOCK, RenderLayer.getTranslucent());

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> tintIndex == 0 ? PIPE_FRAME_COLOUR : PIPE_GLASS_COLOUR, PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> PIPE_FRAME_COLOUR, PIPE_BLOCK_ITEM);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> tintIndex == 0 ? REQUESTER_PIPE_COLOUR : PIPE_GLASS_COLOUR, REQUESTER_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> REQUESTER_PIPE_COLOUR, REQUESTER_PIPE_BLOCK_ITEM);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> tintIndex == 0 ? ACTIVE_SUPPLIER_PIPE_COLOUR : PIPE_GLASS_COLOUR, ACTIVE_SUPPLIER_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ACTIVE_SUPPLIER_PIPE_COLOUR, ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> tintIndex == 0 ? PASSIVE_SUPPLIER_PIPE_COLOUR : PIPE_GLASS_COLOUR, PASSIVE_SUPPLIER_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> PASSIVE_SUPPLIER_PIPE_COLOUR, PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> tintIndex == 0 ? STORAGE_PIPE_COLOUR : PIPE_GLASS_COLOUR, STORAGE_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> STORAGE_PIPE_COLOUR, STORAGE_PIPE_BLOCK_ITEM);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> tintIndex == 0 ? PIPE_FRAME_COLOUR : PIPE_GLASS_COLOUR, NETWORK_CONTROLLER_BLOCK);
    }
}
