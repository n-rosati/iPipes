package ca.hydranoid620.ipipes;

import ca.hydranoid620.ipipes.blocks.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

public class iPipes implements ModInitializer {
    public static final String MOD_ID = "ipipes";
    public static final String MOD_NAME = "iPipes";
    public static final Logger LOGGER = LogManager.getLogger(iPipes.MOD_ID, new MessageFactory() {
        private final String PREFIX = "[" + iPipes.MOD_NAME + "] ";
        @Override
        public Message newMessage(Object message) {
            return ParameterizedMessageFactory.INSTANCE.newMessage(PREFIX + "{}", message);
        }

        @Override
        public Message newMessage(String message) {
            return ParameterizedMessageFactory.INSTANCE.newMessage(PREFIX + message);
        }

        @Override
        public Message newMessage(String message, Object... params) {
            return ParameterizedMessageFactory.INSTANCE.newMessage(PREFIX + message, params);
        }
    });
    public static final ItemGroup IPIPES_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(iPipes.PIPE_BLOCK));

    public static final PipeBlock PIPE_BLOCK = new PipeBlock();
    public static final BlockItem PIPE_BLOCK_ITEM = new BlockItem(PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static final RequesterPipeBlock REQUESTER_PIPE_BLOCK = new RequesterPipeBlock();
    public static final BlockItem REQUESTER_PIPE_BLOCK_ITEM = new BlockItem(REQUESTER_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static final ActiveSupplierPipeBlock ACTIVE_SUPPLIER_PIPE_BLOCK = new ActiveSupplierPipeBlock();
    public static final BlockItem ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM = new BlockItem(ACTIVE_SUPPLIER_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static final PassiveSupplierPipeBlock PASSIVE_SUPPLIER_PIPE_BLOCK = new PassiveSupplierPipeBlock();
    public static final BlockItem PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM = new BlockItem(PASSIVE_SUPPLIER_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static final StoragePipeBlock STORAGE_PIPE_BLOCK = new StoragePipeBlock();
    public static final BlockItem STORAGE_PIPE_BLOCK_ITEM = new BlockItem(STORAGE_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));

    @Override
    public void onInitialize() {
        LOGGER.info("Init started");

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "pipe"), PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "pipe"), PIPE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "requester_pipe"), REQUESTER_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "requester_pipe"), REQUESTER_PIPE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "active_supplier_pipe"), ACTIVE_SUPPLIER_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "active_supplier_pipe"), ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "passive_supplier_pipe"), PASSIVE_SUPPLIER_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "passive_supplier_pipe"), PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM);

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "storage_pipe"), STORAGE_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "storage_pipe"), STORAGE_PIPE_BLOCK_ITEM);

        LOGGER.info("Init finished");
    }
}
