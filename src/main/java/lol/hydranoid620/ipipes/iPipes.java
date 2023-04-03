package lol.hydranoid620.ipipes;

import lol.hydranoid620.ipipes.blocks.*;
import lol.hydranoid620.ipipes.blocks.entities.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
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
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "general"),
                                                                            () -> new ItemStack(iPipes.PIPE_BLOCK));


    public static final String PIPE_BLOCK_ID = "pipe";
    public static final PipeBlock PIPE_BLOCK = Registry.register(Registry.BLOCK,
                                                                 new Identifier(MOD_ID, PIPE_BLOCK_ID),
                                                                 new PipeBlock());
    public static final BlockItem PIPE_BLOCK_ITEM = Registry.register(Registry.ITEM,
                                                                      new Identifier(MOD_ID, PIPE_BLOCK_ID),
                                                                      new BlockItem(PIPE_BLOCK, new Item.Settings().group(ITEM_GROUP)));
    public static BlockEntityType<PipeBlockEntity> PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                                                                         new Identifier(MOD_ID, PIPE_BLOCK_ID + "_be"),
                                                                                         FabricBlockEntityTypeBuilder.create(PipeBlockEntity::new, PIPE_BLOCK).build(null));


    public static final String REQUESTER_PIPE_ID = "requester_pipe";
    public static final RequesterPipeBlock REQUESTER_PIPE_BLOCK = Registry.register(Registry.BLOCK,
                                                                                    new Identifier(MOD_ID, REQUESTER_PIPE_ID),
                                                                                    new RequesterPipeBlock());
    public static final BlockItem REQUESTER_PIPE_BLOCK_ITEM = Registry.register(Registry.ITEM,
                                                                                new Identifier(MOD_ID, REQUESTER_PIPE_ID),
                                                                                new BlockItem(REQUESTER_PIPE_BLOCK, new Item.Settings().group(ITEM_GROUP)));
    public static BlockEntityType<RequesterPipeBlockEntity> REQUESTER_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                                                                                            new Identifier(MOD_ID, REQUESTER_PIPE_ID + "_be"),
                                                                                                            FabricBlockEntityTypeBuilder.create(RequesterPipeBlockEntity::new, REQUESTER_PIPE_BLOCK).build(null));


    public static final String ACTIVE_SUPPLIER_PIPE_ID = "active_supplier_pipe";
    public static final ActiveSupplierPipeBlock ACTIVE_SUPPLIER_PIPE_BLOCK = Registry.register(Registry.BLOCK,
                                                                                               new Identifier(MOD_ID, ACTIVE_SUPPLIER_PIPE_ID),
                                                                                               new ActiveSupplierPipeBlock());
    public static final BlockItem ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM = Registry.register(Registry.ITEM,
                                                                                      new Identifier(MOD_ID, ACTIVE_SUPPLIER_PIPE_ID),
                                                                                      new BlockItem(ACTIVE_SUPPLIER_PIPE_BLOCK, new Item.Settings().group(ITEM_GROUP)));
    public static BlockEntityType<ActiveSupplierPipeBlockEntity> ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                                                                                                       new Identifier(MOD_ID, ACTIVE_SUPPLIER_PIPE_ID + "_be"),
                                                                                                                       FabricBlockEntityTypeBuilder.create(ActiveSupplierPipeBlockEntity::new, ACTIVE_SUPPLIER_PIPE_BLOCK).build(null));


    public static final String PASSIVE_SUPPLIER_ID = "passive_supplier_pipe";
    public static final PassiveSupplierPipeBlock PASSIVE_SUPPLIER_PIPE_BLOCK = Registry.register(Registry.BLOCK,
                                                                                                 new Identifier(MOD_ID, PASSIVE_SUPPLIER_ID),
                                                                                                 new PassiveSupplierPipeBlock());
    public static final BlockItem PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM = Registry.register(Registry.ITEM,
                                                                                       new Identifier(MOD_ID, PASSIVE_SUPPLIER_ID),
                                                                                       new BlockItem(PASSIVE_SUPPLIER_PIPE_BLOCK, new Item.Settings().group(ITEM_GROUP)));
    public static BlockEntityType<PassiveSupplierPipeBlockEntity> PASSIVE_SUPPLIER_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                                                                                                         new Identifier(MOD_ID, PASSIVE_SUPPLIER_ID + "block_entity"),
                                                                                                                         FabricBlockEntityTypeBuilder.create(PassiveSupplierPipeBlockEntity::new, PASSIVE_SUPPLIER_PIPE_BLOCK).build(null));


    public static final String STORAGE_PIPE_ID = "storage_pipe";
    public static final StoragePipeBlock STORAGE_PIPE_BLOCK = Registry.register(Registry.BLOCK,
                                                                                new Identifier(MOD_ID, STORAGE_PIPE_ID),
                                                                                new StoragePipeBlock());
    public static final BlockItem STORAGE_PIPE_BLOCK_ITEM = Registry.register(Registry.ITEM,
                                                                              new Identifier(MOD_ID, STORAGE_PIPE_ID),
                                                                              new BlockItem(STORAGE_PIPE_BLOCK, new Item.Settings().group(ITEM_GROUP)));
    public static BlockEntityType<StoragePipeBlockEntity> STORAGE_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                                                                                        new Identifier(MOD_ID, STORAGE_PIPE_ID + "_be"),
                                                                                                        FabricBlockEntityTypeBuilder.create(StoragePipeBlockEntity::new, STORAGE_PIPE_BLOCK).build(null));


    @Override
    public void onInitialize() {
        LOGGER.info("Init started");

        LOGGER.info("Init finished");
    }

    // When changing pipe types, remember to also update PathFinder
    public enum Types {
        PIPE,
        REQUESTER_PIPE,
        ACTIVE_SUPPLIER_PIPE,
        PASSIVE_PROVIDER_PIPE,
        STORAGE_PIPE
    }
}
