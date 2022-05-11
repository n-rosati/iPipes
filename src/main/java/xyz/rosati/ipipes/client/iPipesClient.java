package xyz.rosati.ipipes.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import xyz.rosati.ipipes.iPipes;

@Environment(EnvType.CLIENT)
public class iPipesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(iPipes.PIPE_BLOCK, RenderLayer.getTranslucent());
    }
}
