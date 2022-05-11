package xyz.rosati.ipipes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.rosati.ipipes.blocks.pipes.PipeBlock;

public class iPipes implements ModInitializer {
    public static final String MOD_ID = "ipipes";
    public static final PipeBlock PIPE_BLOCK = new PipeBlock();
    public static final ItemGroup IPIPES_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(PIPE_BLOCK));

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "pipe"), PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "pipe"), new BlockItem(PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP)));
    }
}
