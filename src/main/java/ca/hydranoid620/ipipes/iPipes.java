package ca.hydranoid620.ipipes;

import ca.hydranoid620.ipipes.blocks.SupplierPipeBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ca.hydranoid620.ipipes.blocks.PipeBlock;

public class iPipes implements ModInitializer {
    public static final Logger LOGGER = LogManager.getFormatterLogger("iPipes");
    public static final String MOD_ID = "ipipes";
    public static final ItemGroup IPIPES_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(iPipes.PIPE_BLOCK));
    public static final PipeBlock PIPE_BLOCK = new PipeBlock();
    public static final BlockItem PIPE_BLOCK_ITEM = new BlockItem(PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static final SupplierPipeBlock SUPPLIER_PIPE_BLOCK = new SupplierPipeBlock();
    public static final BlockItem SUPPLIER_PIPE_BLOCK_ITEM = new BlockItem(SUPPLIER_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));

    @Override
    public void onInitialize() {
        LOGGER.info("Init started");

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "pipe"), PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "pipe"), PIPE_BLOCK_ITEM);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x4f4f4f, PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x4f4f4f, PIPE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "supplier_pipe"), SUPPLIER_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "supplier_pipe"), SUPPLIER_PIPE_BLOCK_ITEM);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, SUPPLIER_PIPE_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x3495eb, SUPPLIER_PIPE_BLOCK_ITEM);

        LOGGER.info("Init finished");
    }
}
